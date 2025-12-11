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

    public Order placeOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateStatus(Long id, String status) {
        Order order = getOrder(id);
        if (order == null) return null;

        order.setStatus(status);
        return orderRepository.save(order);
    }

    public String deleteOrder(Long id) {
        orderRepository.deleteById(id);
        return "Order Deleted Successfully";
    }
}
