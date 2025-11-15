package com.skydiveforecast.infrastructure.adapter.in.web;

import com.skydiveforecast.domain.annotation.PermissionSecurity;
import com.skydiveforecast.domain.port.in.CreatePermissionUseCase;
import com.skydiveforecast.domain.port.in.DeletePermissionUseCase;
import com.skydiveforecast.domain.port.in.GetAllPermissionsUseCase;
import com.skydiveforecast.domain.port.in.UpdatePermissionUseCase;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreatePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionsDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UpdatePermissionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/permissions")
@RequiredArgsConstructor
@Tag(name = "Permissions", description = "Endpoints for managing permissions")
public class PermissionController {

    private final GetAllPermissionsUseCase getAllPermissionsUseCase;
    private final CreatePermissionUseCase createPermissionUseCase;
    private final UpdatePermissionUseCase updatePermissionUseCase;
    private final DeletePermissionUseCase deletePermissionUseCase;

    @GetMapping
    @PermissionSecurity(permission = "PERMISSION_VIEW")
    @Operation(summary = "Get All Permissions", description = "Retrieve a list of all permissions.")
    public ResponseEntity<PermissionsDto> getAllPermissions() {
        return ResponseEntity.ok(getAllPermissionsUseCase.getAllPermissions());
    }

    @PostMapping
    @PermissionSecurity(permission = "PERMISSION_CREATE")
    @Operation(summary = "Create Permission", description = "Create a new permission.")
    public ResponseEntity<PermissionDto> createPermission(@Valid @RequestBody CreatePermissionDto createPermissionDto) {
        return new ResponseEntity<>(createPermissionUseCase.createPermission(createPermissionDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PermissionSecurity(permission = "PERMISSION_UPDATE")
    @Operation(summary = "Update Permission", description = "Update an existing permission.")
    public ResponseEntity<PermissionDto> updatePermission(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePermissionDto updatePermissionDto) {
        return ResponseEntity.ok(updatePermissionUseCase.updatePermission(id, updatePermissionDto));
    }

    @DeleteMapping("/{id}")
    @PermissionSecurity(permission = "PERMISSION_DELETE")
    @Operation(summary = "Delete Permission", description = "Delete a permission.")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        deletePermissionUseCase.deletePermission(id);
        return ResponseEntity.noContent().build();
    }
}
