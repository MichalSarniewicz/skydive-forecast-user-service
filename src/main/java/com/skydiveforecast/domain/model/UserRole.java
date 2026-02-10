package com.skydiveforecast.domain.model;

import lombok.Builder;

@Builder
public record UserRole(
        Long id,
        Long userId,
        Long roleId
) {
}
