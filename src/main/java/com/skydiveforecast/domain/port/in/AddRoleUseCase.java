package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.RoleDto;

public interface AddRoleUseCase {

    RoleDto addRole(String roleName);
}
