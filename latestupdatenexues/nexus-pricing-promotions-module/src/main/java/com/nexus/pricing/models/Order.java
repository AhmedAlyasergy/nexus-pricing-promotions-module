package com.nexus.pricing.models;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import com.nexus.models.Product;
import com.nexus.pricing.strategies.PricingStrategy;
import com.nexus.pricing.singleton.GlobalConfigManager;

public class Order {
    private int orderId;
    private List<OrderItem> items;
    private OrderStatus status;
    private OrderType type;
    private PricingStrategy pricingStrategy;

    // Backward compatibility constructor
    public Order(int orderId, Product product, int quantity) {
        this.orderId = orderId;
        this.items = new ArrayList<>();
        this.items.add(new OrderItem(product, quantity));
        this.status = OrderStatus.NEW;
        this.type = OrderType.NORMAL; // default
        this.pricingStrategy = null; // Will be set later
    }

    public Order(int orderId, List<OrderItem> items) {
        this.orderId = orderId;
        this.items = new ArrayList<>(items);
        this.status = OrderStatus.NEW;
        this.type = OrderType.NORMAL; // default
        this.pricingStrategy = null;
    }

    public Order(int orderId, List<OrderItem> items, OrderType type) {
        this.orderId = orderId;
        this.items = new ArrayList<>(items);
        this.status = OrderStatus.NEW;
        this.type = type;
        this.pricingStrategy = null;
    }

    public int getOrderId() {
        return orderId;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderType getType() {
        return type;
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

    public double getTotalBasePrice() {
        return getTotalAmount();
    }

    public double getTotalAmount() {
        return items.stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
    }

    public double calculateTotal() {
        double base = getTotalAmount();
        // Apply type-specific discounts
        if (type == OrderType.BULK) {
            int totalQuantity = items.stream().mapToInt(OrderItem::getQuantity).sum();
            if (totalQuantity >= 10) {
                base *= (1 - 0.10); // 10% discount
            }
        } else if (type == OrderType.SUBSCRIPTION) {
            base *= (1 - 0.05); // 5% discount
        }
        // Then apply pricing strategy
        double discountedPrice;
        if (pricingStrategy != null) {
            discountedPrice = pricingStrategy.applyDiscount(base);
        } else {
            discountedPrice = base;
        }
        double tax = discountedPrice * GlobalConfigManager.getInstance().getTaxRate();
        return discountedPrice + tax;
    }

    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
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

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", type=" + type +
                ", status=" + status +
                ", items=" + items +
                ", totalAmount=" + calculateTotal() +
                '}';
    }
}