package com.nexus.orders.pricing;

public interface PricingStrategy {
    double calculatePrice(double baseTotal);
    String getStrategyName();
}
