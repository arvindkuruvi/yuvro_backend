package com.management.yuvro.service.impl;


import com.management.yuvro.dto.NotificationDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.jpa.repository.NotificationRepository;
import com.management.yuvro.mapper.NotificationMapper;
import com.management.yuvro.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    NotificationMapper notificationMapper;

    @Override
    public NotificationDTO saveNotification(NotificationDTO dto) {
        log.info("Saving notification: {}", dto);
        var notification = notificationMapper.mapNotificationDTOToNotification(dto);
        notification.setRead(false);
        notification.setCreatedTime(LocalDateTime.now());
        notification = notificationRepository.save(notification);
        log.info("Notification saved successfully: {}", notification);
        return notificationMapper.mapNotificationToNotificationDTO(notification);
    }

    @Override
    public PageDTO<NotificationDTO> getNotifications(Long userId, int page, int size) {
        log.info("Fetching notifications for userId: {}, page: {}, size: {}", userId, page, size);
        return notificationMapper.convertPageOfNotificationToPageOfNotificationDTO(notificationRepository.findAllByNotifierId(userId, PageRequest.of(page, size, Sort.by("notificationId").descending())));
    }

    @Override
    public CommonApiResponse readNotification(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
            log.info("Notification with ID {} marked as read", notificationId);
        });

        return new CommonApiResponse("Notification marked as read successfully", true);
    }

}
