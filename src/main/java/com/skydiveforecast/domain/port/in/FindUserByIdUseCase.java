package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.persistance.entity.UserEntity;

public interface FindUserByIdUseCase {

    UserEntity findUserById(Long userId);
}
