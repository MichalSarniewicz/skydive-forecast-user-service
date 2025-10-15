package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.UpdateUserDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UpdateUserResponse;

public interface UpdateUserUseCase {

    UpdateUserResponse updateUser(Long userId, UpdateUserDto updateUserDto);
}
