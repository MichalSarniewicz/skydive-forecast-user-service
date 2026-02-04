package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.infrastructure.persistence.entity.UserEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UpdateUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UpdateUserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateEntityFromDto(UpdateUserDto dto, @MappingTarget UserEntity entity);
}
