package com.nexus.orders;

import java.util.Collections;
import java.util.List;
import com.nexus.orders.pricing.PricingStrategy;

public abstract class Order {
    private final int orderId;
    private final List<OrderItem> items;
    private OrderStatus status;
    protected PricingStrategy pricingStrategy;

    protected Order(int orderId, List<OrderItem> items) {
        this.orderId = orderId;
        this.items = Collections.unmodifiableList(items);
        this.status = OrderStatus.NEW;
    }

    public int getOrderId() {
        return orderId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public boolean isCancellable() {
        return status == OrderStatus.NEW;
    }

    public boolean isCheckoutable() {
        return status == OrderStatus.NEW;
    }

    public double getTotalAmount() {
        return items.stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
    }

    public double calculateTotal() {
        if (pricingStrategy == null) {
            return getTotalAmount();
        }
        return pricingStrategy.calculatePrice(getTotalAmount());
    }

    public double checkout() {
        if (!isCheckoutable()) {
            throw new IllegalStateException("Order " + orderId + " cannot be checked out in status " + status);
        }
        setStatus(OrderStatus.CHECKED_OUT);
        return calculateTotal();
    }

    public void cancel() {
        if (!isCancellable()) {
            throw new IllegalStateException("Order " + orderId + " cannot be cancelled in status " + status);
        }
        setStatus(OrderStatus.CANCELLED);
    }

    public abstract OrderType getType();

    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", type=" + getType() +
                ", status=" + status +
                ", items=" + items +
                ", totalAmount=" + calculateTotal() +
                '}';
    }
}
