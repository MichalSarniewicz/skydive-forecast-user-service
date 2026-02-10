package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.domain.model.User;

public interface FindUserByIdUseCase {

    User findUserById(Long userId);
}
