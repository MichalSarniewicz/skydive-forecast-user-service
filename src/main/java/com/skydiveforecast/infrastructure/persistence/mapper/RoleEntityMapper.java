package com.skydiveforecast.infrastructure.persistence.mapper;

import com.skydiveforecast.domain.model.Role;
import com.skydiveforecast.infrastructure.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleEntityMapper {

    Role toDomain(RoleEntity entity);

    @Mapping(target = "userRoles", ignore = true)
    @Mapping(target = "rolePermissions", ignore = true)
    RoleEntity toEntity(Role domain);

    List<Role> toDomainList(List<RoleEntity> entities);
}
