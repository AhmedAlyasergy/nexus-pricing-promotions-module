package com.nexus.pricing.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.nexus.pricing.models.Order;
import com.nexus.pricing.models.OrderItem;
import com.nexus.pricing.models.OrderType;
import com.nexus.pricing.manager.OrderController;

@RestController
@RequestMapping("/api/orders")
public class OrderManagementController {

    private final OrderController orderController = new OrderController();

    @PostMapping("/create")
    public Order createOrder(@RequestParam OrderType type, @RequestBody List<OrderItem> items) {
        return orderController.createOrder(type, items);
    }

    @PostMapping("/create/subscription")
    public Order createSubscriptionOrder(@RequestParam OrderType type, @RequestBody List<OrderItem> items, @RequestParam int months) {
        return orderController.createOrder(type, items, months);
    }

    @GetMapping("/{id}")
    public Order viewOrder(@PathVariable int id) {
        return orderController.viewOrder(id);
    }

    @GetMapping("/all")
    public List<Order> viewAllOrders() {
        return orderController.viewAllOrders();
    }

    @GetMapping("/history")
    public List<Order> viewOrderHistory() {
        return orderController.viewOrderHistory();
    }

    @PostMapping("/{id}/checkout")
    public double checkoutOrder(@PathVariable int id) {
        return orderController.checkoutOrder(id);
    }

    @PostMapping("/{id}/cancel")
    public void cancelOrder(@PathVariable int id) {
        orderController.cancelOrder(id);
    }
}