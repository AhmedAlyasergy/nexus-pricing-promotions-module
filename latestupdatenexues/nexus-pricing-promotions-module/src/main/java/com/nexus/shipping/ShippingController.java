package com.nexus.shipping;

import com.google.cloud.firestore.Firestore;
import com.nexus.models.Supplier;
import com.nexus.services.FirebaseService;
import com.nexus.shipping.adapter.LegacySupplierSystem;
import com.nexus.shipping.adapter.SupplierShippingAdapter;
import com.nexus.shipping.bridge.DeliveryMethod;
import com.nexus.shipping.bridge.ShippableProduct;
import com.nexus.shipping.factory.DomesticShippingFactory;
import com.nexus.shipping.factory.InternationalShippingFactory;
import com.nexus.shipping.factory.ShippingFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class ShippingController {

    private static final Logger LOGGER = Logger.getLogger(ShippingController.class.getName());

    private static final Set<String> DOMESTIC_LOCATIONS = Set.of(
            "egypt", "cairo", "alexandria", "giza", "luxor", "aswan"
    );

    private final ShippingService shippingService = new ShippingService();

    @GetMapping("/shipping/methods")
    public ResponseEntity<List<DeliveryMethodResponse>> getShippingMethods(
            @RequestParam(required = false, defaultValue = "Egypt") String country,
            @RequestParam(required = false, defaultValue = "physical") String type) {

        ShippingFactory factory = resolveFactory(country, null);
        List<DeliveryMethodResponse> methods = new ArrayList<>();

        DeliveryMethod standard = factory.createStandardDelivery();
        DeliveryMethod express = factory.createExpressDelivery();

        String scope = isDomestic(country) ? "Domestic" : "International";

        if ("bulk".equalsIgnoreCase(type)) {
            methods.add(buildResponse(standard, scope + " Standard (Bulk)"));
        } else {
            methods.add(buildResponse(standard, scope + " Standard Delivery"));
            methods.add(buildResponse(express, scope + " Express Delivery"));
        }

        return ResponseEntity.ok(methods);
    }

    @PostMapping("/suppliers/sync-legacy")
    public ResponseEntity<?> syncLegacySupplier(@RequestBody LegacySupplierRequest request) {
        if (request.getSupplierCode() == null || request.getSupplierCode().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "supplierCode is required."));
        }
        if (request.getSupplierName() == null || request.getSupplierName().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "supplierName is required."));
        }

        LegacySupplierSystem legacySystem = new LegacySupplierSystem(request.getSupplierCode());
        SupplierShippingAdapter adapter = new SupplierShippingAdapter(legacySystem);

        String referenceItem = request.getReferenceItemCode() != null ? request.getReferenceItemCode() : "";
        String destination = request.getDestination() != null ? request.getDestination() : "";
        int quantity = request.getQuantity() > 0 ? request.getQuantity() : 1;

        boolean dispatched = adapter.confirmDispatch(referenceItem, destination, quantity);
        if (!dispatched) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of("error", "Legacy supplier system rejected the dispatch request."));
        }

        String trackingRef = adapter.getTrackingReference(referenceItem);

        Supplier supplier = new Supplier(
                request.getSupplierCode(),
                request.getSupplierName(),
                request.getContactPhone(),
                request.getEmailAddress(),
                request.getRegion()
        );

        try {
            Firestore db = FirebaseService.getInstance().getDb();
            db.collection("suppliers").document(supplier.getId()).set(supplier).get();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to save supplier to Firestore: " + request.getSupplierCode(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Supplier dispatched but failed to persist to Firestore."));
        }

        return ResponseEntity.ok(Map.of("status", "OK", "trackingReference", trackingRef));
    }

    @PostMapping("/shipping/dispatch")
    public ResponseEntity<?> dispatchShipment(@RequestBody DispatchRequest request) {
        if (request.getProductId() == null || request.getProductId().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "productId is required."));
        }
        if (request.getProductName() == null || request.getProductName().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "productName is required."));
        }
        if (request.getDestination() == null || request.getDestination().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "destination is required."));
        }

        ShippingFactory factory = resolveFactory(request.getCountry(), request.getOverseasSupplierId());
        ShippableProduct product;

        if ("bulk".equalsIgnoreCase(request.getType())) {
            int units = request.getUnitCount() > 0 ? request.getUnitCount() : 1;
            product = factory.createBulkProduct(request.getProductId(), request.getProductName(), units);
        } else {
            product = factory.createPhysicalProduct(request.getProductId(), request.getProductName(), request.isExpress());
        }

        try {
            ShipmentRecord record = shippingService.dispatch(product, request.getDestination());
            return ResponseEntity.ok(record);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Dispatch failed for product: " + request.getProductId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Dispatch failed. Please try again later."));
        }
    }

    private ShippingFactory resolveFactory(String country, String overseasSupplierId) {
        if (isDomestic(country)) {
            return new DomesticShippingFactory();
        }
        String supplierId = (overseasSupplierId != null && !overseasSupplierId.isBlank())
                ? overseasSupplierId : "INTL-DEFAULT";
        return new InternationalShippingFactory(supplierId);
    }

    private boolean isDomestic(String country) {
        return country != null && DOMESTIC_LOCATIONS.contains(country.trim().toLowerCase());
    }

    private DeliveryMethodResponse buildResponse(DeliveryMethod method, String description) {
        return new DeliveryMethodResponse(
                method.getClass().getSimpleName(),
                method.getShippingCost(),
                method.getEstimatedDays(),
                description
        );
    }
}
