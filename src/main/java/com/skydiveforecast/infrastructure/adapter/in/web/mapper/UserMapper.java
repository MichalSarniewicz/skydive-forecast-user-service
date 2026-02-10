package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.domain.model.User;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    @Mapping(source = "isActive", target = "active")
    UserDto toDto(User domain);

    List<UserDto> toDtoList(List<User> domains);
}
