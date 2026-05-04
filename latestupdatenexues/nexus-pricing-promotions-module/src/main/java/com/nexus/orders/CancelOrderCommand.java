package com.nexus.orders;

public class CancelOrderCommand {
    private final Order order;

    public CancelOrderCommand(Order order) {
        this.order = order;
    }

    public void execute() {
        order.cancel();
    }
}
