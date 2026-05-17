package com.nexus.orders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nexus.orders.factory.BulkOrderCreator;
import com.nexus.orders.factory.NormalOrderCreator;
import com.nexus.orders.factory.OrderCreator;
import com.nexus.orders.factory.SubscriptionOrderCreator;

public class OrderController {

    private final Map<Integer, Order> activeOrders = new HashMap<>();
    private final List<Order> orderHistory = new ArrayList<>();

    private int nextOrderId = 1;

    public Order createOrder(OrderType type, List<OrderItem> items) {
        return createOrder(type, items, 1);
    }

    public Order createOrder(
            OrderType type,
            List<OrderItem> items,
            int subscriptionMonths
    ) {

        int orderId = nextOrderId++;

        OrderCreator creator;

        switch (type) {

            case NORMAL:
                creator = new NormalOrderCreator();
                break;

            case BULK:
                creator = new BulkOrderCreator();
                break;

            case SUBSCRIPTION:
                creator = new SubscriptionOrderCreator(subscriptionMonths);
                break;

            default:
                throw new IllegalArgumentException("Invalid order type");
        }

        Order order = creator.createOrder(orderId, items);

        activeOrders.put(orderId, order);

        return order;
    }

    public Order viewOrder(int orderId) {
        return activeOrders.get(orderId);
    }

    public List<Order> viewAllOrders() {
        return Collections.unmodifiableList(
                new ArrayList<>(activeOrders.values())
        );
    }

    public List<Order> viewOrderHistory() {
        return Collections.unmodifiableList(orderHistory);
    }

    public double checkoutOrder(int orderId) {

        Order order = activeOrders.get(orderId);

        if (order == null) {
            throw new IllegalArgumentException(
                    "Order " + orderId + " does not exist."
            );
        }

        double total = order.checkout();

        activeOrders.remove(orderId);

        orderHistory.add(order);

        return total;
    }

    public void cancelOrder(int orderId) {

        Order order = activeOrders.get(orderId);

        if (order == null) {
            throw new IllegalArgumentException(
                    "Order " + orderId +
                    " does not exist or already processed."
            );
        }

        CancelOrderCommand cancelCommand =
                new CancelOrderCommand(order);

        cancelCommand.execute();

        activeOrders.remove(orderId);

        orderHistory.add(order);
    }
}