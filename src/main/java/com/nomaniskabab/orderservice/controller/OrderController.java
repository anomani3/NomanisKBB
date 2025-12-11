package com.nomaniskabab.orderservice.controller;
import com.nomaniskabab.orderservice.entity.Order;
import com.nomaniskabab.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public Order placeOrder(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
    }

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/update/{id}")
    public Order updateOrder(
            @PathVariable Long id,
            @RequestParam String status) {
        return orderService.updateStatus(id, status);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }
}

