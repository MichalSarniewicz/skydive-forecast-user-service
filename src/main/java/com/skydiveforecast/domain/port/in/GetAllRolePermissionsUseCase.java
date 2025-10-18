package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolePermissionsDto;

public interface GetAllRolePermissionsUseCase {

    RolePermissionsDto getAllRolePermissions();
}
