package com.skydiveforecast.domain.port.in;

public interface ChangePasswordUseCase {

    void changePassword(Long userId, String oldPassword, String newPassword);
}
