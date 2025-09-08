package com.management.yuvro.mapper;

import com.management.yuvro.dto.NotificationDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.jpa.entity.Notification;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import static com.management.yuvro.constants.Constants.RETRIEVE_SUCCESS;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface NotificationMapper {

    @Mapping(target = "content", source = "content")
    @Mapping(target = "page", source = "page.pageable.pageNumber")
    @Mapping(target = "size", source = "page.pageable.pageSize")
    @Mapping(target = "totalElements", source = "page.totalElements")
    @Mapping(target = "totalPages", source = "page.totalPages")
    @Mapping(target = "message", constant = RETRIEVE_SUCCESS)
    @Mapping(target = "success", constant = "true")
    PageDTO<NotificationDTO> convertPageOfNotificationToPageOfNotificationDTO(Page<Notification> page);

    Notification mapNotificationDTOToNotification(NotificationDTO notificationDTO);

    NotificationDTO mapNotificationToNotificationDTO(Notification notification);
}
