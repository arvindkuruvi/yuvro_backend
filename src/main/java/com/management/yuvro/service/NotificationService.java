package com.management.yuvro.service;

import com.management.yuvro.dto.NotificationDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.response.CommonApiResponse;

public interface NotificationService {
    NotificationDTO saveNotification(NotificationDTO notificationDTO);

    PageDTO<NotificationDTO> getNotifications(Long userId, int page, int size);

    CommonApiResponse readNotification(Long notificationId);
}
