package com.nexus.shipping.bridge;

public class ExpressDelivery implements DeliveryMethod {

    @Override
    public String deliver(String productName, String destination) {
        return "Express delivery of [" + productName + "] to [" + destination + "] in " + getEstimatedDays() + " day(s).";
    }

    @Override
    public double getShippingCost() { return 45.00; }

    @Override
    public int getEstimatedDays() { return 2; }
}
