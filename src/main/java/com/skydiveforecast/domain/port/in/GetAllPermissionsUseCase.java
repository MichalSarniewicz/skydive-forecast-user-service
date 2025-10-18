package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionsDto;

public interface GetAllPermissionsUseCase {

    PermissionsDto getAllPermissions();
}
