package com.temple.manager.notification.mapper;

import com.temple.manager.notification.dto.NotificationDTO;
import com.temple.manager.notification.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {
    NotificationDTO entityToDTO(Notification entity);

    List<NotificationDTO> entityListToDTOList(List<Notification> entityList);
}
