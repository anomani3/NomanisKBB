package com.nomaniskabab.notification_service.service;
import com.nomaniskabab.notification_service.dto.NotificationRequest;
import com.nomaniskabab.notification_service.entity.Notification;
import com.nomaniskabab.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;

    public Notification sendNotification(NotificationRequest request) {
        Notification notification = Notification.builder()
                .userId(request.getUserId())
                .message(request.getMessage())
                .type(request.getType())
                .timestamp(LocalDateTime.now())
                .build();

        return repository.save(notification);
    }

    public List<Notification> getUserNotifications(Long userId) {
        return repository.findByUserId(userId);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

