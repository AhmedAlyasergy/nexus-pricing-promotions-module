package com.nexus.pricing.strategies;

public class BulkPricing implements PricingStrategy {
    private static final double BULK_DISCOUNT_RATE = 0.10;
    private static final int BULK_QUANTITY_THRESHOLD = 10;

    @Override
    public double applyDiscount(double price) {
        // For simplicity, assume bulk if price > some amount, but since we don't have quantity here,
        // this is a placeholder. In real implementation, strategy would need order context.
        // For now, return price (no discount)
        return price;
    }
}