package com.nexus.shipping.bridge;

public class StandardDelivery implements DeliveryMethod {

    @Override
    public String deliver(String productName, String destination) {
        return "Standard delivery of [" + productName + "] to [" + destination + "] in " + getEstimatedDays() + " days.";
    }

    @Override
    public double getShippingCost() { return 15.00; }

    @Override
    public int getEstimatedDays() { return 7; }
}
