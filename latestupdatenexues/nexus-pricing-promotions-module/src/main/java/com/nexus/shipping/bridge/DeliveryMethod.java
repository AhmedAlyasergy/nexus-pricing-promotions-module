package com.nexus.shipping.bridge;

public interface DeliveryMethod {
    String deliver(String productName, String destination);
    double getShippingCost();
    int getEstimatedDays();
}
