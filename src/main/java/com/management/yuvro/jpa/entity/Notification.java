package com.management.yuvro.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    private String notificationType;
    private String notificationMessage;
    private Long createdById;
    private String createdByName;
    private Long notifierId;
    private LocalDateTime createdTime;
    private Long candidateTaskId;
    private boolean read;
}