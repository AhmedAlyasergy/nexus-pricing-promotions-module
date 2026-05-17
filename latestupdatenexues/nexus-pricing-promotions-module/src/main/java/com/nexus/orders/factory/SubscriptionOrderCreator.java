package com.nexus.orders.factory;

import java.util.List;

import com.nexus.orders.Order;
import com.nexus.orders.OrderItem;
import com.nexus.orders.SubscriptionOrder;

public class SubscriptionOrderCreator extends OrderCreator {

    private final int subscriptionMonths;

    public SubscriptionOrderCreator(int subscriptionMonths) {
        this.subscriptionMonths = subscriptionMonths;
    }

    @Override
    public Order createOrder(int orderId, List<OrderItem> items) {
        return new SubscriptionOrder(orderId, items, subscriptionMonths);
    }
}   