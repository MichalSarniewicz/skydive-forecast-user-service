package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UpdatePermissionDto;

public interface UpdatePermissionUseCase {

    PermissionDto updatePermission(Long id, UpdatePermissionDto updatePermissionDto);
}
