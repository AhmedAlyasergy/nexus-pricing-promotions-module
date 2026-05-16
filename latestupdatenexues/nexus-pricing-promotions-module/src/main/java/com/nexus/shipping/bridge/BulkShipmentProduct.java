package com.nexus.shipping.bridge;

import com.nexus.shipping.ShipmentRecord;

import java.util.UUID;

public class BulkShipmentProduct extends ShippableProduct {

    private final int unitCount;

    public BulkShipmentProduct(String productId, String productName, DeliveryMethod deliveryMethod, int unitCount) {
        super(productId, productName, deliveryMethod);
        this.unitCount = unitCount;
    }

    @Override
    public ShipmentRecord ship(String destination) {
        String label = productName + " (x" + unitCount + ")";
        String message = deliveryMethod.deliver(label, destination);
        System.out.println("[BulkShipmentProduct] " + message);
        String supplierRef = (deliveryMethod instanceof DropShipDelivery d) ? d.getSupplierId() : null;
        double totalCost = deliveryMethod.getShippingCost() * Math.ceil((double) unitCount / 50.0);
        return new ShipmentRecord(
                UUID.randomUUID().toString(), productId, productName,
                destination, deliveryMethod.getClass().getSimpleName(),
                totalCost, deliveryMethod.getEstimatedDays(), supplierRef);
    }

    public int getUnitCount() { return unitCount; }
}
