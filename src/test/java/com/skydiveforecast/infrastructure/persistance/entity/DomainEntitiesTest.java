package com.skydiveforecast.infrastructure.persistance.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomainEntitiesTest {

    @Test
    @DisplayName("Should create RoleEntity with builder")
    void roleEntity_Builder() {
        // Arrange & Act
        RoleEntity role = RoleEntity.builder()
                .id(1L)
                .name("ADMIN")
                .build();

        // Assert
        assertNotNull(role);
        assertEquals(1L, role.getId());
        assertEquals("ADMIN", role.getName());
    }

    @Test
    @DisplayName("Should set RoleEntity onCreate")
    void roleEntity_OnCreate() {
        // Arrange
        RoleEntity role = new RoleEntity();

        // Act
        role.onCreate();

        // Assert
        assertNotNull(role.getCreatedAt());
    }

    @Test
    @DisplayName("Should set RoleEntity onUpdate")
    void roleEntity_OnUpdate() {
        // Arrange
        RoleEntity role = new RoleEntity();

        // Act
        role.onUpdate();

        // Assert
        assertNotNull(role.getUpdatedAt());
    }

    @Test
    @DisplayName("Should create PermissionEntity with builder")
    void permissionEntity_Builder() {
        // Arrange & Act
        PermissionEntity permission = PermissionEntity.builder()
                .id(1L)
                .code("USER_VIEW")
                .description("View users")
                .build();

        // Assert
        assertNotNull(permission);
        assertEquals(1L, permission.getId());
        assertEquals("USER_VIEW", permission.getCode());
        assertEquals("View users", permission.getDescription());
    }

    @Test
    @DisplayName("Should set PermissionEntity onCreate")
    void permissionEntity_OnCreate() {
        // Arrange
        PermissionEntity permission = new PermissionEntity();

        // Act
        permission.onCreate();

        // Assert
        assertNotNull(permission.getCreatedAt());
    }

    @Test
    @DisplayName("Should set PermissionEntity onUpdate")
    void permissionEntity_OnUpdate() {
        // Arrange
        PermissionEntity permission = new PermissionEntity();

        // Act
        permission.onUpdate();

        // Assert
        assertNotNull(permission.getUpdatedAt());
    }

    @Test
    @DisplayName("Should create RolePermissionEntity with builder")
    void rolePermissionEntity_Builder() {
        // Arrange
        RoleEntity role = new RoleEntity();
        role.setId(1L);
        PermissionEntity permission = new PermissionEntity();
        permission.setId(2L);

        // Act
        RolePermissionEntity rolePermission = RolePermissionEntity.builder()
                .id(1L)
                .role(role)
                .permission(permission)
                .build();

        // Assert
        assertNotNull(rolePermission);
        assertEquals(1L, rolePermission.getId());
        assertEquals(1L, rolePermission.getRole().getId());
        assertEquals(2L, rolePermission.getPermission().getId());
    }

    @Test
    @DisplayName("Should set RolePermissionEntity onCreate")
    void rolePermissionEntity_OnCreate() {
        // Arrange
        RolePermissionEntity rolePermission = new RolePermissionEntity();

        // Act
        rolePermission.onCreate();

        // Assert
        assertNotNull(rolePermission.getCreatedAt());
    }

    @Test
    @DisplayName("Should set RolePermissionEntity onUpdate")
    void rolePermissionEntity_OnUpdate() {
        // Arrange
        RolePermissionEntity rolePermission = new RolePermissionEntity();

        // Act
        rolePermission.onUpdate();

        // Assert
        assertNotNull(rolePermission.getUpdatedAt());
    }
}
