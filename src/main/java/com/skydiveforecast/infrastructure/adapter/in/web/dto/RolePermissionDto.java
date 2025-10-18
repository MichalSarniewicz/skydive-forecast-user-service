package com.skydiveforecast.infrastructure.adapter.in.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RolePermissionDto {
    private Long id;
    private Long roleId;
    private String roleName;
    private Long permissionId;
    private String permissionCode;
    private String permissionDescription;
}