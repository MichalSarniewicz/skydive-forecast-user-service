package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.domain.model.Permission;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreatePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    PermissionDto toDto(Permission domain);

    List<PermissionDto> toDtoList(List<Permission> domains);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Permission toDomain(CreatePermissionDto dto);
}
