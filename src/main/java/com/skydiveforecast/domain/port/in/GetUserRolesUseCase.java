package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserRolesDto;

public interface GetUserRolesUseCase {

    UserRolesDto getUserRoles(Long userId);
}
