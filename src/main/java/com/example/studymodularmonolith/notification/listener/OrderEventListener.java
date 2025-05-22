package com.example.studymodularmonolith.notification.listener;

import com.example.studymodularmonolith.notification.service.NotificationService;
import com.example.studymodularmonolith.order.event.OrderEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {
    private final NotificationService notificationService;

    public OrderEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @EventListener
    public void handleOrderCreatedEvent(OrderEvent event) {
        if (event.getType() == OrderEvent.EventType.CREATED) {
            notificationService.sendOrderConfirmation(event.getUserId(), event.getOrderId());
        } else if (event.getType() == OrderEvent.EventType.STATUS_CHANGED) {
            switch (event.getStatus()) {
                case PAID:
                    notificationService.createNotification(
                            event.getUserId(),
                            "Order Payment Confirmed",
                            "Your payment for order " + event.getOrderId() + " has been confirmed.",
                            com.example.studymodularmonolith.notification.domain.Notification.NotificationType.EMAIL
                    );
                    break;
                case SHIPPED:
                    notificationService.sendOrderShipped(event.getUserId(), event.getOrderId());
                    break;
                case DELIVERED:
                    notificationService.createNotification(
                            event.getUserId(),
                            "Order Delivered",
                            "Your order " + event.getOrderId() + " has been delivered.",
                            com.example.studymodularmonolith.notification.domain.Notification.NotificationType.EMAIL
                    );
                    break;
                case CANCELLED:
                    notificationService.createNotification(
                            event.getUserId(),
                            "Order Cancelled",
                            "Your order " + event.getOrderId() + " has been cancelled.",
                            com.example.studymodularmonolith.notification.domain.Notification.NotificationType.EMAIL
                    );
                    break;
            }
        }
    }
}