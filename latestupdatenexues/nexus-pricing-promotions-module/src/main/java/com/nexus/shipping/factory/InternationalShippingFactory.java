package com.nexus.shipping.factory;

import com.nexus.shipping.bridge.BulkShipmentProduct;
import com.nexus.shipping.bridge.DeliveryMethod;
import com.nexus.shipping.bridge.DropShipDelivery;
import com.nexus.shipping.bridge.InternationalExpressDelivery;
import com.nexus.shipping.bridge.InternationalStandardDelivery;
import com.nexus.shipping.bridge.PhysicalProduct;
import com.nexus.shipping.bridge.ShippableProduct;

public class InternationalShippingFactory implements ShippingFactory {

    private final String overseasSupplierId;

    public InternationalShippingFactory(String overseasSupplierId) {
        this.overseasSupplierId = overseasSupplierId;
    }

    @Override
    public DeliveryMethod createStandardDelivery() {
        return new InternationalStandardDelivery();
    }

    @Override
    public DeliveryMethod createExpressDelivery() {
        return new InternationalExpressDelivery();
    }

    @Override
    public ShippableProduct createPhysicalProduct(String productId, String productName, boolean express) {
        DeliveryMethod method = express ? createExpressDelivery() : createStandardDelivery();
        return new PhysicalProduct(productId, productName, method);
    }

    @Override
    public ShippableProduct createBulkProduct(String productId, String productName, int unitCount) {
        return new BulkShipmentProduct(productId, productName, new DropShipDelivery(overseasSupplierId), unitCount);
    }
}
