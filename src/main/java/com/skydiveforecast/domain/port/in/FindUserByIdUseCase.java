package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.domain.model.UserEntity;

public interface FindUserByIdUseCase {

    UserEntity findUserById(Long userId);
}
