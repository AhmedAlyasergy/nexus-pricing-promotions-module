package com.nexus.orders;

import java.util.List;
import com.nexus.orders.pricing.SubscriptionPricingStrategy;

public class SubscriptionOrder extends Order {
    private final int subscriptionMonths;

    public SubscriptionOrder(int orderId, List<OrderItem> items, int subscriptionMonths) {
        super(orderId, items);
        this.subscriptionMonths = Math.max(subscriptionMonths, 1);
        this.pricingStrategy = new SubscriptionPricingStrategy();
    }

    public int getSubscriptionMonths() {
        return subscriptionMonths;
    }

    @Override
    public OrderType getType() {
        return OrderType.SUBSCRIPTION;
    }

    @Override
    public String toString() {
        return super.toString().replace("}", ", subscriptionMonths=" + subscriptionMonths + '}');
    }
}
