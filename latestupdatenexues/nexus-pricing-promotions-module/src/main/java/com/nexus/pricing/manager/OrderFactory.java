package com.nexus.pricing.manager;

import java.util.List;
import com.nexus.pricing.models.Order;
import com.nexus.pricing.models.OrderItem;
import com.nexus.pricing.models.OrderType;

public class OrderFactory {

    private OrderFactory() {
        // factory class should not be instantiated
    }

    public static Order createNormalOrder(int orderId, List<OrderItem> items) {
        Order order = new Order(orderId, items, OrderType.NORMAL);
        order.setPricingStrategy(new com.nexus.pricing.strategies.NormalPricing());
        return order;
    }

    public static Order createBulkOrder(int orderId, List<OrderItem> items) {
        Order order = new Order(orderId, items, OrderType.BULK);
        order.setPricingStrategy(new com.nexus.pricing.strategies.BulkPricing());
        return order;
    }

    public static Order createSubscriptionOrder(int orderId, List<OrderItem> items, int subscriptionMonths) {
        Order order = new Order(orderId, items, OrderType.SUBSCRIPTION);
        order.setPricingStrategy(new com.nexus.pricing.strategies.SubscriptionPricing());
        return order;
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