package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.AssignPermissionsToRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolePermissionDto;

import java.util.List;

public interface AssignPermissionsToRoleUseCase {

    List<RolePermissionDto> assignPermissionsToRole(AssignPermissionsToRoleDto assignPermissionsToRoleDto);
}
