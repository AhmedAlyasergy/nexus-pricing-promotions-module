package com.nexus.shipping;

public class DispatchRequest {

    private String productId;
    private String productName;
    private String destination;
    private String country;
    private String type;
    private boolean express;
    private int unitCount;
    private String overseasSupplierId;

    public DispatchRequest() {}

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isExpress() { return express; }
    public void setExpress(boolean express) { this.express = express; }

    public int getUnitCount() { return unitCount; }
    public void setUnitCount(int unitCount) { this.unitCount = unitCount; }

    public String getOverseasSupplierId() { return overseasSupplierId; }
    public void setOverseasSupplierId(String overseasSupplierId) { this.overseasSupplierId = overseasSupplierId; }
}
