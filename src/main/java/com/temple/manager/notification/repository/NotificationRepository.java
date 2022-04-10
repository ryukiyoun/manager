package com.temple.manager.notification.repository;

import com.temple.manager.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByCreateByAndConfirmAndResponseMessageNotNull(String username, boolean confirm);
}
