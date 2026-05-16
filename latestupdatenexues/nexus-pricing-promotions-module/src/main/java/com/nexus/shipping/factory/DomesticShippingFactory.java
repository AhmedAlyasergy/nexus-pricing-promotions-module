package com.nexus.shipping.factory;

import com.nexus.shipping.bridge.BulkShipmentProduct;
import com.nexus.shipping.bridge.DeliveryMethod;
import com.nexus.shipping.bridge.ExpressDelivery;
import com.nexus.shipping.bridge.PhysicalProduct;
import com.nexus.shipping.bridge.ShippableProduct;
import com.nexus.shipping.bridge.StandardDelivery;

public class DomesticShippingFactory implements ShippingFactory {

    @Override
    public DeliveryMethod createStandardDelivery() {
        return new StandardDelivery();
    }

    @Override
    public DeliveryMethod createExpressDelivery() {
        return new ExpressDelivery();
    }

    @Override
    public ShippableProduct createPhysicalProduct(String productId, String productName, boolean express) {
        DeliveryMethod method = express ? createExpressDelivery() : createStandardDelivery();
        return new PhysicalProduct(productId, productName, method);
    }

    @Override
    public ShippableProduct createBulkProduct(String productId, String productName, int unitCount) {
        return new BulkShipmentProduct(productId, productName, createStandardDelivery(), unitCount);
    }
}
