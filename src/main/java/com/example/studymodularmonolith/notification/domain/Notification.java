package com.example.studymodularmonolith.notification.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {
    private final UUID id;
    private final UUID userId;
    private final String subject;
    private final String content;
    private final NotificationType type;
    private final LocalDateTime createdAt;
    private boolean read;

    public Notification(UUID userId, String subject, String content, NotificationType type) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.subject = subject;
        this.content = content;
        this.type = type;
        this.createdAt = LocalDateTime.now();
        this.read = false;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public NotificationType getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isRead() {
        return read;
    }

    public void markAsRead() {
        this.read = true;
    }

    public enum NotificationType {
        EMAIL, SMS, PUSH, SYSTEM
    }
}