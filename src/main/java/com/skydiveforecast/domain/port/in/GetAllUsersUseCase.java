package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.UsersDto;

public interface GetAllUsersUseCase {

    UsersDto getAllUsers();
}
