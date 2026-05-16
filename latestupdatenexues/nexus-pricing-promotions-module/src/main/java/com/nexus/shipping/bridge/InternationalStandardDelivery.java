package com.nexus.shipping.bridge;

public class InternationalStandardDelivery implements DeliveryMethod {

    @Override
    public String deliver(String productName, String destination) {
        return "International standard delivery of [" + productName + "] to [" + destination + "] in " + getEstimatedDays() + " days.";
    }

    @Override
    public double getShippingCost() { return 55.00; }

    @Override
    public int getEstimatedDays() { return 21; }
}
