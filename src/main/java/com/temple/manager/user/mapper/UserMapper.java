package com.temple.manager.user.mapper;

import com.temple.manager.user.dto.UserDTO;
import com.temple.manager.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDTO entityToDTO(User user);
}
