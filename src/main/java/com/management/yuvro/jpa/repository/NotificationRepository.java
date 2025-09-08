package com.management.yuvro.jpa.repository;

import com.management.yuvro.jpa.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findAllByNotifierId(Long notifierId, PageRequest pageRequest);
}