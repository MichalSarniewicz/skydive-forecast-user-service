package com.skydiveforecast.domain.port.in;

public interface RemoveRoleFromUserUseCase {

    void removeRoleFromUser(Long userId, Long roleId);
}
