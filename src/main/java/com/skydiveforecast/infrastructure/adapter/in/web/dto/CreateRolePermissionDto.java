package com.skydiveforecast.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRolePermissionDto {
    @NotNull(message = "Role ID is required")
    private Long roleId;
    
    @NotNull(message = "Permission ID is required")
    private Long permissionId;
}