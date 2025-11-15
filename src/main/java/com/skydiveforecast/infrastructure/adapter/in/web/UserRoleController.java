package com.skydiveforecast.infrastructure.adapter.in.web;

import com.skydiveforecast.domain.port.in.AssignRoleToUserUseCase;
import com.skydiveforecast.domain.port.in.GetAllUserRolesUseCase;
import com.skydiveforecast.domain.port.in.GetUserRolesUseCase;
import com.skydiveforecast.domain.port.in.RemoveRoleFromUserUseCase;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreateUserRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserRolesDto;
import com.skydiveforecast.domain.annotation.PermissionSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/user-roles")
@RequiredArgsConstructor
@Tag(name = "User Roles", description = "Endpoints for managing user-role assignments")
public class UserRoleController {

    private final GetAllUserRolesUseCase getAllUserRolesUseCase;
    private final GetUserRolesUseCase getUserRolesUseCase;
    private final AssignRoleToUserUseCase assignRoleToUserUseCase;
    private final RemoveRoleFromUserUseCase removeRoleFromUserUseCase;

    @GetMapping
    @PermissionSecurity(permission = "USER_ROLE_VIEW_ALL")
    @Operation(summary = "Get all user-role assignments",
            description = "Fetch a list of all user-role assignments in the system")
    public ResponseEntity<UserRolesDto> getAllUserRoles() {
        return ResponseEntity.ok(getAllUserRolesUseCase.getAllUserRoles());
    }

    @GetMapping("/user/{user-id}")
    @PermissionSecurity(permission = "USER_ROLE_VIEW")
    @Operation(summary = "Get roles for user",
            description = "Fetch all roles assigned to a specific user")
    public ResponseEntity<UserRolesDto> getUserRoles(@PathVariable("user-id") Long userId) {
        return ResponseEntity.ok(getUserRolesUseCase.getUserRoles(userId));
    }

    @PostMapping
    @PermissionSecurity(permission = "USER_ROLE_ASSIGN")
    @Operation(summary = "Assign role to user",
            description = "Assign a role to a specific user")
    public ResponseEntity<UserRoleDto> assignRoleToUser(@Valid @RequestBody CreateUserRoleDto createUserRoleDto) {
        return ResponseEntity.ok(assignRoleToUserUseCase.assignRoleToUser(createUserRoleDto));
    }

    @DeleteMapping("/user/{user-id}/role/{role-id}")
    @PermissionSecurity(permission = "USER_ROLE_REMOVE")
    @Operation(summary = "Remove specific role from user",
            description = "Remove a specific role from a user")
    public ResponseEntity<Void> removeRoleFromUser(@PathVariable("user-id") Long userId, @PathVariable("role-id") Long roleId) {
        removeRoleFromUserUseCase.removeRoleFromUser(userId, roleId);
        return ResponseEntity.noContent().build();
    }
}