package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreateUserDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserDto;

public interface CreateUserUseCase {

    UserDto createUser(CreateUserDto createUserDto);
}
