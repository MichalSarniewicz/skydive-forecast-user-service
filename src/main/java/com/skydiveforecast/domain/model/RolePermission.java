package com.skydiveforecast.domain.model;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record RolePermission(
        Long id,
        Long roleId,
        Long permissionId,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
