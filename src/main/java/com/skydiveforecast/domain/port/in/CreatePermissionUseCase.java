package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreatePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionDto;

public interface CreatePermissionUseCase {

    PermissionDto createPermission(CreatePermissionDto createPermissionDto);
}
