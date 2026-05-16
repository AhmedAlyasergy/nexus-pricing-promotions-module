package com.nexus.shipping;

import java.time.Instant;

public class ShipmentRecord {

    private String shipmentId;
    private String productId;
    private String productName;
    private String destination;
    private String deliveryType;
    private double shippingCost;
    private int estimatedDays;
    private String status;
    private String timestamp;
    private String supplierRef;

    public ShipmentRecord() {}

    public ShipmentRecord(String shipmentId, String productId, String productName,
                          String destination, String deliveryType, double shippingCost,
                          int estimatedDays, String supplierRef) {
        this.shipmentId = shipmentId;
        this.productId = productId;
        this.productName = productName;
        this.destination = destination;
        this.deliveryType = deliveryType;
        this.shippingCost = shippingCost;
        this.estimatedDays = estimatedDays;
        this.status = "DISPATCHED";
        this.timestamp = Instant.now().toString();
        this.supplierRef = supplierRef;
    }

    public String getShipmentId() { return shipmentId; }
    public void setShipmentId(String shipmentId) { this.shipmentId = shipmentId; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getDeliveryType() { return deliveryType; }
    public void setDeliveryType(String deliveryType) { this.deliveryType = deliveryType; }

    public double getShippingCost() { return shippingCost; }
    public void setShippingCost(double shippingCost) { this.shippingCost = shippingCost; }

    public int getEstimatedDays() { return estimatedDays; }
    public void setEstimatedDays(int estimatedDays) { this.estimatedDays = estimatedDays; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getSupplierRef() { return supplierRef; }
    public void setSupplierRef(String supplierRef) { this.supplierRef = supplierRef; }
}
