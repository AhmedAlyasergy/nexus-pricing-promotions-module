package com.nexus.shipping.adapter;

public class LegacySupplierSystem {

    private final String supplierCode;

    public LegacySupplierSystem(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String sendShipmentRequest(String itemCode, String shipTo, int qty) {
        if (itemCode == null || itemCode.isBlank() || qty <= 0) {
            return "REJECTED";
        }
        System.out.println("[LegacySupplierSystem:" + supplierCode + "] Request sent — item=" + itemCode + ", to=" + shipTo + ", qty=" + qty);
        return "ACCEPTED:" + supplierCode + "-" + itemCode + "-" + System.currentTimeMillis();
    }

    public String fetchTrackingCode(String itemCode) {
        if (itemCode == null || itemCode.isBlank()) {
            return supplierCode + "-TRK-UNKNOWN";
        }
        return supplierCode + "-TRK-" + itemCode.toUpperCase();
    }
}
