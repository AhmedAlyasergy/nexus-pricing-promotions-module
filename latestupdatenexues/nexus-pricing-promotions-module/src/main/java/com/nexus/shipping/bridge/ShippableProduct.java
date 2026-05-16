package com.nexus.shipping.bridge;

import com.nexus.shipping.ShipmentRecord;

public abstract class ShippableProduct {

    protected final String productId;
    protected final String productName;
    protected DeliveryMethod deliveryMethod;

    protected ShippableProduct(String productId, String productName, DeliveryMethod deliveryMethod) {
        this.productId = productId;
        this.productName = productName;
        this.deliveryMethod = deliveryMethod;
    }

    public abstract ShipmentRecord ship(String destination);

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public DeliveryMethod getDeliveryMethod() { return deliveryMethod; }
}
