package com.management.yuvro.controller;

import com.management.yuvro.dto.NotificationDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @PostMapping("/save-notification")
    public ResponseEntity<NotificationDTO> saveNotification(@RequestBody NotificationDTO notificationDTO) {
        return ResponseEntity.ok(notificationService.saveNotification(notificationDTO));
    }

    @GetMapping("/get-user-notifications")
    public ResponseEntity<PageDTO<NotificationDTO>> getNotifications(
            @RequestParam(name = "userId") Long userId, @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size) {
        return ResponseEntity.ok(notificationService.getNotifications(userId, page, size));
    }

    @PutMapping("/read-notification")
    public ResponseEntity<CommonApiResponse> readNotification(
            @RequestParam(name = "notificationId") Long notificationId) {
        return ResponseEntity.ok(notificationService.readNotification(notificationId));
    }

}