package com.skydiveforecast.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class AssignPermissionsToRoleDto {
    @NotNull(message = "Role ID is required")
    private Long roleId;
    
    @NotNull(message = "Permission IDs are required")
    private Set<Long> permissionIds;
}