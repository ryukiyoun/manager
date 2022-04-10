package com.temple.manager.notification.controller;

import com.temple.manager.notification.dto.NotificationDTO;
import com.temple.manager.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/slack/notification")
    public ResponseEntity<Boolean> slackNotification(@RequestBody String message) {
        notificationService.sendFunctionRequestMessage(message);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/slack/callback")
    public ResponseEntity<Boolean> slackCallBack(@RequestParam String payload) {
        notificationService.sendResponseMessage(payload);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/notification/confirm/{id}")
    public ResponseEntity<NotificationDTO> notificationConfirm(@PathVariable long id){
        return ResponseEntity.ok(notificationService.setNotificationConfirm(id));
    }

    @GetMapping("/notification/recent")
    public ResponseEntity<List<NotificationDTO>> getRecentNotification(@AuthenticationPrincipal UserDetails user){
        return ResponseEntity.ok(notificationService.recentNotification(user));
    }
}
