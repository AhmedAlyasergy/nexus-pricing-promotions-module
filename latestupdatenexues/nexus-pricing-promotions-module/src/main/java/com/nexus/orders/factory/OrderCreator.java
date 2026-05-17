package com.nexus.orders.factory;

import java.util.List;

import com.nexus.orders.Order;
import com.nexus.orders.OrderItem;

public abstract class OrderCreator {

    public abstract Order createOrder(
            int orderId,
            List<OrderItem> items
    );
}