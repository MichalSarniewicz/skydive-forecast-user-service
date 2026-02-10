package com.skydiveforecast.infrastructure.persistence.mapper;

import com.skydiveforecast.domain.model.RolePermission;
import com.skydiveforecast.infrastructure.persistence.entity.RolePermissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolePermissionEntityMapper {

    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "permission.id", target = "permissionId")
    RolePermission toDomain(RolePermissionEntity entity);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "permission", ignore = true)
    RolePermissionEntity toEntity(RolePermission domain);

    List<RolePermission> toDomainList(List<RolePermissionEntity> entities);
}
