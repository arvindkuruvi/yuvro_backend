package com.management.yuvro.mapper;

import com.management.yuvro.dto.request.RegisterRequest;
import com.management.yuvro.dto.response.AuthResponse;
import com.management.yuvro.jpa.entity.YuvroUser;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    YuvroUser mapRegisterRequestToYuvroUser(RegisterRequest request);

    AuthResponse mapUserWithAuthResponse(YuvroUser user, @MappingTarget AuthResponse response);
}

