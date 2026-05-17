package com.nexus.orders.factory;

import java.util.List;

import com.nexus.orders.NormalOrder;
import com.nexus.orders.Order;
import com.nexus.orders.OrderItem;

public class NormalOrderCreator extends OrderCreator {

    @Override
    public Order createOrder(int orderId, List<OrderItem> items) {
        return new NormalOrder(orderId, items);
    }
}