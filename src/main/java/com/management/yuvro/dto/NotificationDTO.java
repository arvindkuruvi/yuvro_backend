package com.management.yuvro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private Long notificationId;
    private String notificationType;
    private String notificationMessage;
    private Long createdById;
    private String createdByName;
    private Long notifierId;
    private String createdTime;
    private Long targetResourceId;
    private boolean read;
}
