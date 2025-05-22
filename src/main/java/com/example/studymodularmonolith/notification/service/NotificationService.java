package com.example.studymodularmonolith.notification.service;

import com.example.studymodularmonolith.notification.domain.Notification;
import com.example.studymodularmonolith.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification createNotification(UUID userId, String subject, String content, Notification.NotificationType type) {
        Notification notification = new Notification(userId, subject, content, type);
        return notificationRepository.save(notification);
    }

    public Optional<Notification> getNotificationById(UUID id) {
        return notificationRepository.findById(id);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationsByUserId(UUID userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getUnreadNotificationsByUserId(UUID userId) {
        return notificationRepository.findByUserIdAndRead(userId, false);
    }

    public List<Notification> getReadNotificationsByUserId(UUID userId) {
        return notificationRepository.findByUserIdAndRead(userId, true);
    }

    public List<Notification> getNotificationsByType(Notification.NotificationType type) {
        return notificationRepository.findByType(type);
    }

    public Notification markNotificationAsRead(UUID id) {
        return notificationRepository.findById(id)
                .map(notification -> {
                    notification.markAsRead();
                    return notificationRepository.save(notification);
                })
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
    }

    public void deleteNotification(UUID id) {
        notificationRepository.deleteById(id);
    }

    // Example of a method that would be called by other modules
    public void sendOrderConfirmation(UUID userId, UUID orderId) {
        String subject = "Order Confirmation";
        String content = "Your order " + orderId + " has been confirmed.";
        createNotification(userId, subject, content, Notification.NotificationType.EMAIL);
    }

    public void sendOrderShipped(UUID userId, UUID orderId) {
        String subject = "Order Shipped";
        String content = "Your order " + orderId + " has been shipped.";
        createNotification(userId, subject, content, Notification.NotificationType.EMAIL);
    }

    public void sendWelcomeMessage(UUID userId, String username) {
        String subject = "Welcome to our platform";
        String content = "Hello " + username + ", welcome to our platform!";
        createNotification(userId, subject, content, Notification.NotificationType.SYSTEM);
    }
}