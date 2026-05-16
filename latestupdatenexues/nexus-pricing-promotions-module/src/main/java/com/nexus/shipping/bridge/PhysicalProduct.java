package com.nexus.shipping.bridge;

import com.nexus.shipping.ShipmentRecord;

import java.util.UUID;

public class PhysicalProduct extends ShippableProduct {

    public PhysicalProduct(String productId, String productName, DeliveryMethod deliveryMethod) {
        super(productId, productName, deliveryMethod);
    }

    @Override
    public ShipmentRecord ship(String destination) {
        String message = deliveryMethod.deliver(productName, destination);
        System.out.println("[PhysicalProduct] " + message);
        String supplierRef = (deliveryMethod instanceof DropShipDelivery d) ? d.getSupplierId() : null;
        return new ShipmentRecord(
                UUID.randomUUID().toString(), productId, productName,
                destination, deliveryMethod.getClass().getSimpleName(),
                deliveryMethod.getShippingCost(), deliveryMethod.getEstimatedDays(), supplierRef);
    }
}
