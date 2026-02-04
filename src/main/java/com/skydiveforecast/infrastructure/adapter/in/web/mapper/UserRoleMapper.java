package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.infrastructure.persistence.entity.UserRoleEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserRoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    @Mapping(source = "role.id", target = "id")
    @Mapping(source = "role.name", target = "name")
    UserRoleDto toDto(UserRoleEntity entity);

    List<UserRoleDto> toDtoList(List<UserRoleEntity> entities);
}
