package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.domain.model.RoleEntity;
import com.skydiveforecast.domain.model.UserEntity;
import com.skydiveforecast.domain.model.UserRoleEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    @Mapping(target = "roles", expression = "java(extractRoles(entity.getRoles()))")
    UserDto toDto(UserEntity entity);

    List<UserDto> toDtoList(List<UserEntity> entities);

    RoleDto mapRoleToDto(RoleEntity role);

    // Helper method to extract RoleDto objects from UserRoleEntity objects
    default Set<RoleDto> extractRoles(Set<UserRoleEntity> userRoles) {
        if (userRoles == null) {
            return null;
        }
        return userRoles.stream()
                .map(UserRoleEntity::getRole)
                .map(this::mapRoleToDto)
                .collect(Collectors.toSet());
    }
}
