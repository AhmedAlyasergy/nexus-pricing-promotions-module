package com.nexus.shipping.bridge;

public class InternationalExpressDelivery implements DeliveryMethod {

    @Override
    public String deliver(String productName, String destination) {
        return "International express delivery of [" + productName + "] to [" + destination + "] in " + getEstimatedDays() + " day(s).";
    }

    @Override
    public double getShippingCost() { return 120.00; }

    @Override
    public int getEstimatedDays() { return 5; }
}
