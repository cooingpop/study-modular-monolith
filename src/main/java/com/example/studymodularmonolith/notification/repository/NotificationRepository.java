package com.example.studymodularmonolith.notification.repository;

import com.example.studymodularmonolith.notification.domain.Notification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class NotificationRepository {
    private final ConcurrentHashMap<UUID, Notification> notifications = new ConcurrentHashMap<>();

    public Notification save(Notification notification) {
        notifications.put(notification.getId(), notification);
        return notification;
    }

    public Optional<Notification> findById(UUID id) {
        return Optional.ofNullable(notifications.get(id));
    }

    public List<Notification> findAll() {
        return new ArrayList<>(notifications.values());
    }

    public List<Notification> findByUserId(UUID userId) {
        return notifications.values().stream()
                .filter(notification -> notification.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Notification> findByUserIdAndRead(UUID userId, boolean read) {
        return notifications.values().stream()
                .filter(notification -> notification.getUserId().equals(userId) && notification.isRead() == read)
                .collect(Collectors.toList());
    }

    public List<Notification> findByType(Notification.NotificationType type) {
        return notifications.values().stream()
                .filter(notification -> notification.getType() == type)
                .collect(Collectors.toList());
    }

    public void deleteById(UUID id) {
        notifications.remove(id);
    }
}