package com.nomaniskabab.orderservice.service;

import com.nomaniskabab.orderservice.entity.Order;
import com.nomaniskabab.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    // ---------------- PLACE ORDER ----------------
    public Order placeOrder(Order order) {
        order.setStatus("CREATED"); // default status
        return orderRepository.save(order);
    }

    // ---------------- GET ORDER BY ID ----------------
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    // ---------------- GET USER ORDERS ----------------
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // ---------------- GET ALL ORDERS ----------------
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // ---------------- UPDATE STATUS ----------------
    public Order updateStatus(Long id, String status) {
        Order order = getOrder(id);
        if (order == null) {
            return null;
        }

        order.setStatus(status); // ✅ THIS NOW WORKS
        return orderRepository.save(order);
    }

    // ---------------- DELETE ORDER ----------------
    public String deleteOrder(Long id) {
        orderRepository.deleteById(id);
        return "Order Deleted Successfully";
    }
}
