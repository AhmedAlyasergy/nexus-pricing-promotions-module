package com.nexus.orders;

import java.util.List;
import com.nexus.orders.pricing.NormalPricingStrategy;

public class NormalOrder extends Order {
    public NormalOrder(int orderId, List<OrderItem> items) {
        super(orderId, items);
        this.pricingStrategy = new NormalPricingStrategy();
    }

    @Override
    public OrderType getType() {
        return OrderType.NORMAL;
    }
}