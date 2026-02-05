package com.skydiveforecast.infrastructure.persistence.mapper;

import com.skydiveforecast.domain.model.Permission;
import com.skydiveforecast.infrastructure.persistence.entity.PermissionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionEntityMapper {

    Permission toDomain(PermissionEntity entity);

    PermissionEntity toEntity(Permission domain);

    List<Permission> toDomainList(List<PermissionEntity> entities);
}
