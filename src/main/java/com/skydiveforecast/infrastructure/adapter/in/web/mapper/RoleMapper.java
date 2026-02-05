package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.domain.model.Role;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RoleDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toDto(Role domain);

    List<RoleDto> toDtoList(List<Role> domains);
}
