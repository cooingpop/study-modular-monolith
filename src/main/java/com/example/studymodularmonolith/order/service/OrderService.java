package com.example.studymodularmonolith.order.service;

import com.example.studymodularmonolith.order.domain.Order;
import com.example.studymodularmonolith.order.event.OrderEvent;
import com.example.studymodularmonolith.order.repository.OrderRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;

    public OrderService(OrderRepository orderRepository, ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    public Order createOrder(UUID userId) {
        Order order = new Order(userId);
        order = orderRepository.save(order);
        eventPublisher.publishEvent(OrderEvent.orderCreated(order));
        return order;
    }

    public Optional<Order> getOrderById(UUID id) {
        return orderRepository.findById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUserId(UUID userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public Order addItemToOrder(UUID orderId, UUID productId, int quantity, BigDecimal price) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.addItem(productId, quantity, price);
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    public Order removeItemFromOrder(UUID orderId, UUID productId) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.removeItem(productId);
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    public Order updateOrderStatus(UUID orderId, Order.OrderStatus status) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus(status);
                    order = orderRepository.save(order);
                    eventPublisher.publishEvent(OrderEvent.statusChanged(order));
                    return order;
                })
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }
}
