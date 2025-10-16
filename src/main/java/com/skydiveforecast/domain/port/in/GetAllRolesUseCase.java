package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolesDto;

public interface GetAllRolesUseCase {

    RolesDto getAllRoles();
}
