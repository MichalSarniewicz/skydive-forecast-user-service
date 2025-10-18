package com.skydiveforecast.infrastructure.adapter.in.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RolePermissionsDto {
    private List<RolePermissionDto> rolePermissions;
}