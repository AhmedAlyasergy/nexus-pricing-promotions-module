package com.nexus.shipping.bridge;

public class DropShipDelivery implements DeliveryMethod {

    private final String supplierId;

    public DropShipDelivery(String supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String deliver(String productName, String destination) {
        return "Drop-ship of [" + productName + "] via supplier [" + supplierId + "] to [" + destination + "] in " + getEstimatedDays() + " days.";
    }

    @Override
    public double getShippingCost() { return 0.00; }

    @Override
    public int getEstimatedDays() { return 5; }

    public String getSupplierId() { return supplierId; }
}
