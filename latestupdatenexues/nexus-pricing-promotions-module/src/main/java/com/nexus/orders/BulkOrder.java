package com.nexus.orders;

import java.util.List;
import com.nexus.orders.pricing.BulkPricingStrategy;

public class BulkOrder extends Order {
    public BulkOrder(int orderId, List<OrderItem> items) {
        super(orderId, items);
        this.pricingStrategy = new BulkPricingStrategy(items);
    }

    @Override
    public OrderType getType() {
        return OrderType.BULK;
    }
}