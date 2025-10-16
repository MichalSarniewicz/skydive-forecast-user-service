package com.skydiveforecast.infrastructure.adapter.in.web;

import com.skydiveforecast.domain.port.in.AddRoleUseCase;
import com.skydiveforecast.domain.port.in.DeleteRoleUseCase;
import com.skydiveforecast.domain.port.in.GetAllRolesUseCase;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolesDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "Endpoints for managing roles")
public class RoleController {

    private final GetAllRolesUseCase getAllRolesUseCase;
    private final AddRoleUseCase addRoleUseCase;
    private final DeleteRoleUseCase deleteRoleUseCase;

    @GetMapping
    @Operation(summary = "Get all roles", description = "Fetch a list of all roles available in the system")
    public ResponseEntity<RolesDto> getAllRoles() {
        RolesDto roles = getAllRolesUseCase.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @PostMapping
    @Operation(summary = "Add a new role", description = "Create a new role in the system")
    public ResponseEntity<RoleDto> addRole(
            @Parameter(description = "Name of the new role", required = true)
            @RequestParam("role-name") String roleName) {
        RoleDto roleDto = addRoleUseCase.addRole(roleName);
        return ResponseEntity.ok(roleDto);
    }

    @DeleteMapping("/{role-id}")
    @Operation(summary = "Delete a role", 
               description = "Delete a role by ID. The ADMIN role cannot be deleted.")
    public ResponseEntity<String> deleteRole(
            @Parameter(description = "ID of the role to delete", required = true)
            @PathVariable("role-id") Long roleId) {
        deleteRoleUseCase.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }
}