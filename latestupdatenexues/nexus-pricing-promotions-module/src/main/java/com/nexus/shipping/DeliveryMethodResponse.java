package com.nexus.shipping;

public class DeliveryMethodResponse {

    private String methodName;
    private double shippingCost;
    private int estimatedDays;
    private String description;

    public DeliveryMethodResponse(String methodName, double shippingCost, int estimatedDays, String description) {
        this.methodName = methodName;
        this.shippingCost = shippingCost;
        this.estimatedDays = estimatedDays;
        this.description = description;
    }

    public String getMethodName() { return methodName; }
    public void setMethodName(String methodName) { this.methodName = methodName; }

    public double getShippingCost() { return shippingCost; }
    public void setShippingCost(double shippingCost) { this.shippingCost = shippingCost; }

    public int getEstimatedDays() { return estimatedDays; }
    public void setEstimatedDays(int estimatedDays) { this.estimatedDays = estimatedDays; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
