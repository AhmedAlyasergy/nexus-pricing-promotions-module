package com.nexus.shipping.bridge;

import com.nexus.shipping.ShipmentRecord;

import java.util.UUID;

public class SubscriptionShipmentProduct extends ShippableProduct {

    private final int cycleNumber;

    public SubscriptionShipmentProduct(String productId, String productName, DeliveryMethod deliveryMethod, int cycleNumber) {
        super(productId, productName, deliveryMethod);
        this.cycleNumber = cycleNumber;
    }

    @Override
    public ShipmentRecord ship(String destination) {
        String label = productName + " [Cycle #" + cycleNumber + "]";
        String message = deliveryMethod.deliver(label, destination);
        System.out.println("[SubscriptionShipmentProduct] " + message);
        String supplierRef = (deliveryMethod instanceof DropShipDelivery d) ? d.getSupplierId() : null;
        return new ShipmentRecord(
                UUID.randomUUID().toString(), productId, productName,
                destination, deliveryMethod.getClass().getSimpleName(),
                deliveryMethod.getShippingCost(), deliveryMethod.getEstimatedDays(), supplierRef);
    }

    public int getCycleNumber() { return cycleNumber; }
}
