package com.skydiveforecast.domain.port.in;

import java.util.Set;

public interface GetPermissionCodesByRoleIdUseCase {

    Set<String> getPermissionCodesByRoleId(Long roleId);
}
