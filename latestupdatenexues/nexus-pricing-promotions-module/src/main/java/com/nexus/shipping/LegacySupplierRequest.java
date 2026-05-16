package com.nexus.shipping;

public class LegacySupplierRequest {

    private String supplierCode;
    private String supplierName;
    private String contactPhone;
    private String emailAddress;
    private String region;
    private String referenceItemCode;
    private String destination;
    private int quantity;

    public LegacySupplierRequest() {}

    public String getSupplierCode() { return supplierCode; }
    public void setSupplierCode(String supplierCode) { this.supplierCode = supplierCode; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getReferenceItemCode() { return referenceItemCode; }
    public void setReferenceItemCode(String referenceItemCode) { this.referenceItemCode = referenceItemCode; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
