package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.infrastructure.persistance.entity.RoleEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toDto(RoleEntity entity);

    @Mapping(target = "userRoles", ignore = true)
    @Mapping(target = "rolePermissions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    RoleEntity toEntity(RoleDto dto);

    List<RoleDto> toDtoList(List<RoleEntity> entities);
}
