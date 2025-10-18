package com.skydiveforecast.infrastructure.adapter.in.web;

import com.skydiveforecast.domain.port.in.*;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.AssignPermissionsToRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreateRolePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolePermissionsDto;
import com.skydiveforecast.infrastructure.security.PermissionSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin/role-permissions")
@RequiredArgsConstructor
@Tag(name = "Role Permissions", description = "Endpoints for managing role-permission relationships.")
public class AdminRolePermissionController {

    private final GetAllRolePermissionsUseCase getAllRolePermissionsUseCase;
    private final GetRolePermissionsByRoleIdUseCase getRolePermissionsByRoleIdUseCase;
    private final GetRolePermissionsByPermissionIdUseCase getRolePermissionsByPermissionIdUseCase;
    private final CreateRolePermissionUseCase createRolePermissionUseCase;
    private final AssignPermissionsToRoleUseCase assignPermissionsToRoleUseCase;
    private final DeleteRolePermissionUseCase deleteRolePermissionUseCase;
    private final GetPermissionCodesByRoleIdUseCase getPermissionCodesByRoleIdUseCase;
    private final DeleteAllRolePermissionsByRoleIdUseCase deleteAllRolePermissionsByRoleIdUseCase;
    private final DeleteAllRolePermissionsByPermissionIdUseCase deleteAllRolePermissionsByPermissionIdUseCase;

    @GetMapping
    @PermissionSecurity(permission = "ROLE_PERMISSION_VIEW")
    @Operation(summary = "Get All Role Permissions", description = "Retrieve a list of all role-permission relationships.")
    public ResponseEntity<RolePermissionsDto> getAllRolePermissions() {
        RolePermissionsDto rolePermissions = getAllRolePermissionsUseCase.getAllRolePermissions();
        return ResponseEntity.ok(rolePermissions);
    }

    @GetMapping("/role/{role-id}")
    @PermissionSecurity(permission = "ROLE_PERMISSION_VIEW")
    @Operation(summary = "Get Role Permissions by Role ID", description = "Retrieve all permissions assigned to a specific role.")
    public ResponseEntity<RolePermissionsDto> getRolePermissionsByRoleId(
            @Parameter(description = "ID of the role") @PathVariable("role-id") Long roleId) {
        RolePermissionsDto rolePermissions = getRolePermissionsByRoleIdUseCase.getRolePermissionsByRoleId(roleId);
        return ResponseEntity.ok(rolePermissions);
    }

    @GetMapping("/permission/{permission-id}")
    @PermissionSecurity(permission = "ROLE_PERMISSION_VIEW")
    @Operation(summary = "Get Role Permissions by Permission ID", description = "Retrieve all roles that have a specific permission.")
    public ResponseEntity<RolePermissionsDto> getRolePermissionsByPermissionId(
            @Parameter(description = "ID of the permission") @PathVariable("permission-id") Long permissionId) {
        RolePermissionsDto rolePermissions = getRolePermissionsByPermissionIdUseCase.getRolePermissionsByPermissionId(permissionId);
        return ResponseEntity.ok(rolePermissions);
    }

    @PostMapping
    @PermissionSecurity(permission = "ROLE_PERMISSION_CREATE")
    @Operation(summary = "Create Role Permission", description = "Create a new role-permission relationship.")
    public ResponseEntity<RolePermissionDto> createRolePermission(
            @Valid @RequestBody CreateRolePermissionDto createRolePermissionDto) {
        return ResponseEntity.ok(createRolePermissionUseCase.createRolePermission(createRolePermissionDto));
    }

    @PutMapping("/assign")
    @PermissionSecurity(permission = "ROLE_PERMISSION_ASSIGN")
    @Operation(
            summary = "Assign Permissions to Role",
            description = "Assign multiple permissions to a role. This will replace all existing permissions for the role with the new set of permissions.")
    public ResponseEntity<List<RolePermissionDto>> assignPermissionsToRole(
            @Valid @RequestBody AssignPermissionsToRoleDto assignPermissionsToRoleDto) {
        return ResponseEntity.ok(assignPermissionsToRoleUseCase.assignPermissionsToRole(assignPermissionsToRoleDto));
    }

    @DeleteMapping("/{id}")
    @PermissionSecurity(permission = "ROLE_PERMISSION_DELETE")
    @Operation(summary = "Delete Role Permission", description = "Delete a specific role-permission relationship.")
    public ResponseEntity<Void> deleteRolePermission(
            @Parameter(description = "ID of the role-permission relationship") @PathVariable Long id) {
        deleteRolePermissionUseCase.deleteRolePermission(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/role/{role-id}/permission-codes")
    @PermissionSecurity(permission = "ROLE_PERMISSION_VIEW")
    @Operation(summary = "Get Permission Codes by Role ID", description = "Retrieve all permission codes for a specific role.")
    public ResponseEntity<Set<String>> getPermissionCodesByRoleId(
            @Parameter(description = "ID of the role") @PathVariable("role-id") Long roleId) {
        Set<String> permissionCodes = getPermissionCodesByRoleIdUseCase.getPermissionCodesByRoleId(roleId);
        return ResponseEntity.ok(permissionCodes);
    }

    @DeleteMapping("/role/{role-id}")
    @PermissionSecurity(permission = "ROLE_PERMISSION_DELETE")
    @Operation(summary = "Delete All Role Permissions by Role ID", description = "Delete all permissions assigned to a specific role.")
    public ResponseEntity<Void> deleteAllRolePermissionsByRoleId(
            @Parameter(description = "ID of the role") @PathVariable("role-id") Long roleId) {
        deleteAllRolePermissionsByRoleIdUseCase.deleteAllRolePermissionsByRoleId(roleId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/permission/{permission-id}")
    @PermissionSecurity(permission = "ROLE_PERMISSION_DELETE")
    @Operation(summary = "Delete All Role Permissions by Permission ID", description = "Delete all role assignments for a specific permission.")
    public ResponseEntity<Void> deleteAllRolePermissionsByPermissionId(
            @Parameter(description = "ID of the permission") @PathVariable("permission-id") Long permissionId) {
        deleteAllRolePermissionsByPermissionIdUseCase.deleteAllRolePermissionsByPermissionId(permissionId);
        return ResponseEntity.noContent().build();
    }
}