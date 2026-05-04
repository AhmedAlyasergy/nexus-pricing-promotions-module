package com.nexus.orders;

import java.util.List;

public class OrderFactory {

    private OrderFactory() {
        // factory class should not be instantiated
    }

    public static Order createNormalOrder(int orderId, List<OrderItem> items) {
        return new NormalOrder(orderId, items);
    }

    public static Order createBulkOrder(int orderId, List<OrderItem> items) {
        return new BulkOrder(orderId, items);
    }

    public static Order createSubscriptionOrder(int orderId, List<OrderItem> items, int subscriptionMonths) {
        return new SubscriptionOrder(orderId, items, subscriptionMonths);
    }

    public static Order createOrder(OrderType type, int orderId, List<OrderItem> items) {
        return createOrder(type, orderId, items, 1);
    }

    public static Order createOrder(OrderType type, int orderId, List<OrderItem> items, int subscriptionMonths) {
        return switch (type) {
            case NORMAL -> createNormalOrder(orderId, items);
            case BULK -> createBulkOrder(orderId, items);
            case SUBSCRIPTION -> createSubscriptionOrder(orderId, items, subscriptionMonths);
        };
    }
}
