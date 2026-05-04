package com.nexus.pricing.manager;

import com.nexus.pricing.models.Order;

public class CancelOrderCommand {
    private final Order order;

    public CancelOrderCommand(Order order) {
        this.order = order;
    }

    public void execute() {
        order.cancel();
    }
}