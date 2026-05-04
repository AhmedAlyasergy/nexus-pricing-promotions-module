package com.nexus.pricing.strategies;

public class SubscriptionPricing implements PricingStrategy {
    private static final double SUBSCRIPTION_DISCOUNT_RATE = 0.05;

    @Override
    public double applyDiscount(double price) {
        return price * (1 - SUBSCRIPTION_DISCOUNT_RATE);
    }
}