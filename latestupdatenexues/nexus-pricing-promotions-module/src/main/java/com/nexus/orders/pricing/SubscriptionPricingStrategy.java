package com.nexus.orders.pricing;

public class SubscriptionPricingStrategy implements PricingStrategy {
    private static final double SUBSCRIPTION_DISCOUNT_RATE = 0.05;

    @Override
    public double calculatePrice(double baseTotal) {
        return baseTotal * (1 - SUBSCRIPTION_DISCOUNT_RATE);
    }

    @Override
    public String getStrategyName() {
        return "Subscription";
    }
}
