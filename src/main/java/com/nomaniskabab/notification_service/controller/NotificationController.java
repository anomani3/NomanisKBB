package com.nomaniskabab.notification_service.controller;
import com.nomaniskabab.notification_service.dto.NotificationRequest;
import com.nomaniskabab.notification_service.entity.Notification;
import com.nomaniskabab.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping("/send")
    public Notification send(@RequestBody NotificationRequest request) {
        return service.sendNotification(request);
    }

    @GetMapping("/{userId}")
    public List<Notification> getAll(@PathVariable Long userId) {
        return service.getUserNotifications(userId);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "Notification deleted successfully";
    }
}

