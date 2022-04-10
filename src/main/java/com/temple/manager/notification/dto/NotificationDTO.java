package com.temple.manager.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    long notificationId;

    String requestMessage;

    String responseTitle;

    String responseMessage;

    boolean confirm;

    LocalDateTime createDate;
}
