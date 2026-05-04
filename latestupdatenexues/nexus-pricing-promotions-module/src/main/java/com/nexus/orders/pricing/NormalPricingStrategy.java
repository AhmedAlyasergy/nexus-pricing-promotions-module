package com.nexus.orders.pricing;

public class NormalPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(double baseTotal) {
        return baseTotal;
    }

    @Override
    public String getStrategyName() {
        return "Normal";
    }
}
