package com.skydiveforecast.domain.model;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RolePermissionTest {

    @Test
    void builder_ShouldCreateRolePermissionWithAllFields() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();

        // Act
        RolePermission rolePermission = RolePermission.builder()
                .id(1L)
                .roleId(10L)
                .permissionId(20L)
                .createdAt(now)
                .updatedAt(now)
                .build();

        // Assert
        assertEquals(1L, rolePermission.id());
        assertEquals(10L, rolePermission.roleId());
        assertEquals(20L, rolePermission.permissionId());
        assertEquals(now, rolePermission.createdAt());
        assertEquals(now, rolePermission.updatedAt());
    }

    @Test
    void record_ShouldBeImmutable() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();
        RolePermission rp1 = RolePermission.builder().id(1L).roleId(10L).permissionId(20L).createdAt(now).updatedAt(now).build();
        RolePermission rp2 = RolePermission.builder().id(1L).roleId(10L).permissionId(20L).createdAt(now).updatedAt(now).build();

        // Assert
        assertEquals(rp1, rp2);
        assertEquals(rp1.hashCode(), rp2.hashCode());
    }
}
