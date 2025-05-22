package com.example.studymodularmonolith.order.event;

import com.example.studymodularmonolith.order.domain.Order;

import java.util.UUID;

public class OrderEvent {
    private final UUID orderId;
    private final UUID userId;
    private final Order.OrderStatus status;
    private final EventType type;

    public OrderEvent(UUID orderId, UUID userId, Order.OrderStatus status, EventType type) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.type = type;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getUserId() {
        return userId;
    }

    public Order.OrderStatus getStatus() {
        return status;
    }

    public EventType getType() {
        return type;
    }

    public enum EventType {
        CREATED, STATUS_CHANGED
    }

    public static OrderEvent orderCreated(Order order) {
        return new OrderEvent(order.getId(), order.getUserId(), order.getStatus(), EventType.CREATED);
    }

    public static OrderEvent statusChanged(Order order) {
        return new OrderEvent(order.getId(), order.getUserId(), order.getStatus(), EventType.STATUS_CHANGED);
    }
}