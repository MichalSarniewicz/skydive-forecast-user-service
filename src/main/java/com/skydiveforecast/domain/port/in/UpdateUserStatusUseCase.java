package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserStatusUpdateDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserStatusUpdateResponse;

public interface UpdateUserStatusUseCase {

    UserStatusUpdateResponse updateUserStatus(Long userId, UserStatusUpdateDto statusUpdateDto);
}
