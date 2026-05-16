package com.nexus.shipping.adapter;

public class SupplierShippingAdapter implements SupplierShippingPort {

    private final LegacySupplierSystem legacySystem;

    public SupplierShippingAdapter(LegacySupplierSystem legacySystem) {
        this.legacySystem = legacySystem;
    }

    @Override
    public boolean confirmDispatch(String productId, String destination, int quantity) {
        String response = legacySystem.sendShipmentRequest(productId, destination, quantity);
        return response != null && response.startsWith("ACCEPTED");
    }

    @Override
    public String getTrackingReference(String productId) {
        return legacySystem.fetchTrackingCode(productId);
    }
}
