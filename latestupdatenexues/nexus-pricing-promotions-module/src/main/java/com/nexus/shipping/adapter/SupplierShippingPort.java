package com.nexus.shipping.adapter;

public interface SupplierShippingPort {
    boolean confirmDispatch(String productId, String destination, int quantity);
    String getTrackingReference(String productId);
}
