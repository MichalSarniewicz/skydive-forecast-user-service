package com.skydiveforecast.infrastructure.persistence.mapper;

import com.skydiveforecast.domain.model.UserRole;
import com.skydiveforecast.infrastructure.persistence.entity.UserRoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRoleEntityMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "role.id", target = "roleId")
    UserRole toDomain(UserRoleEntity entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "role", ignore = true)
    UserRoleEntity toEntity(UserRole domain);

    List<UserRole> toDomainList(List<UserRoleEntity> entities);
}
