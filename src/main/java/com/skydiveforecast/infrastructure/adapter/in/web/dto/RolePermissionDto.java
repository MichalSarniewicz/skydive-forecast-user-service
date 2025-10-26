package com.skydiveforecast.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionDto {
    private Long id;
    private Long roleId;
    private String roleName;
    private Long permissionId;
    private String permissionCode;
    private String permissionDescription;
}