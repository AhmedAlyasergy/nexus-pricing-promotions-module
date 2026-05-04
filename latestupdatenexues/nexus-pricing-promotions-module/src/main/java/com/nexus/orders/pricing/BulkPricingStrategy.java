package com.nexus.orders.pricing;

import java.util.List;
import com.nexus.orders.OrderItem;

public class BulkPricingStrategy implements PricingStrategy {
    private static final double BULK_DISCOUNT_RATE = 0.10;
    private static final int BULK_QUANTITY_THRESHOLD = 10;
    private final List<OrderItem> items;

    public BulkPricingStrategy(List<OrderItem> items) {
        this.items = items;
    }

    @Override
    public double calculatePrice(double baseTotal) {
        int totalQuantity = items.stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();

        if (totalQuantity >= BULK_QUANTITY_THRESHOLD) {
            return baseTotal * (1 - BULK_DISCOUNT_RATE);
        }
        return baseTotal;
    }

    @Override
    public String getStrategyName() {
        return "Bulk";
    }
}
