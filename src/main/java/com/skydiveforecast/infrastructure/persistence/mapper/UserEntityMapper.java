package com.skydiveforecast.infrastructure.persistence.mapper;

import com.skydiveforecast.domain.model.User;
import com.skydiveforecast.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    @Mapping(source = "active", target = "isActive")
    User toDomain(UserEntity entity);

    @Mapping(target = "roles", ignore = true)
    @Mapping(source = "isActive", target = "active")
    UserEntity toEntity(User domain);

    List<User> toDomainList(List<UserEntity> entities);
}
