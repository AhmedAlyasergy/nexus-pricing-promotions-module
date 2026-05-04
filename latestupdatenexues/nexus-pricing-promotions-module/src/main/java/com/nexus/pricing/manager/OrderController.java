package com.nexus.pricing.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.nexus.pricing.models.Order;
import com.nexus.pricing.models.OrderItem;
import com.nexus.pricing.models.OrderType;

public class OrderController {
    private final Map<Integer, Order> activeOrders = new HashMap<>();
    private final List<Order> orderHistory = new ArrayList<>();
    private int nextOrderId = 1;

    public Order createOrder(OrderType type, List<OrderItem> items) {
        return createOrder(type, items, 1);
    }

    public Order createOrder(OrderType type, List<OrderItem> items, int subscriptionMonths) {
        int orderId = nextOrderId++;
        Order order = OrderFactory.createOrder(type, orderId, items, subscriptionMonths);
        activeOrders.put(orderId, order);
        return order;
    }

    public Order viewOrder(int orderId) {
        return activeOrders.get(orderId);
    }

    public List<Order> viewAllOrders() {
        return Collections.unmodifiableList(new ArrayList<>(activeOrders.values()));
    }

    public List<Order> viewOrderHistory() {
        return Collections.unmodifiableList(orderHistory);
    }

    public double checkoutOrder(int orderId) {
        Order order = activeOrders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order " + orderId + " does not exist.");
        }

        double total = order.checkout();
        activeOrders.remove(orderId);
        orderHistory.add(order);
        return total;
    }

    public void cancelOrder(int orderId) {
        Order order = activeOrders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order " + orderId + " does not exist or has already been processed.");
        }

        CancelOrderCommand cancelCommand = new CancelOrderCommand(order);
        cancelCommand.execute();
        activeOrders.remove(orderId);
        orderHistory.add(order);
    }
}