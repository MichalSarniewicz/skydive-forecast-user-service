package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreateUserRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserRoleDto;

public interface AssignRoleToUserUseCase {

    UserRoleDto assignRoleToUser(CreateUserRoleDto createUserRoleDto);
}
