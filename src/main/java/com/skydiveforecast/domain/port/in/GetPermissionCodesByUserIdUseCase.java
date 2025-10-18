package com.skydiveforecast.domain.port.in;

import java.util.Set;

public interface GetPermissionCodesByUserIdUseCase {
    Set<String> getPermissionCodesByUserId(Long userId);
}
