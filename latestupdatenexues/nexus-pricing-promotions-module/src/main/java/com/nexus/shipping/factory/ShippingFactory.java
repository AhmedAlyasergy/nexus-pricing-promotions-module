package com.nexus.shipping.factory;

import com.nexus.shipping.bridge.DeliveryMethod;
import com.nexus.shipping.bridge.ShippableProduct;

public interface ShippingFactory {
    DeliveryMethod createStandardDelivery();
    DeliveryMethod createExpressDelivery();
    ShippableProduct createPhysicalProduct(String productId, String productName, boolean express);
    ShippableProduct createBulkProduct(String productId, String productName, int unitCount);
}
