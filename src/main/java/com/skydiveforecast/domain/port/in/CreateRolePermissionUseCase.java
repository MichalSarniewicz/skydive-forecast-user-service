package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreateRolePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolePermissionDto;

public interface CreateRolePermissionUseCase {

    RolePermissionDto createRolePermission(CreateRolePermissionDto createRolePermissionDto);
}
