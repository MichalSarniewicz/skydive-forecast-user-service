package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.domain.model.*;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(UserEntity entity);

    List<UserDto> toDtoList(List<UserEntity> entities);
}
