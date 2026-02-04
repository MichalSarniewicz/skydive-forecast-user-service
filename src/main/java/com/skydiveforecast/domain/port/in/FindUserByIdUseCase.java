package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.persistence.entity.UserEntity;

public interface FindUserByIdUseCase {

    UserEntity findUserById(Long userId);
}
