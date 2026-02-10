package com.skydiveforecast.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleTest {

    @Test
    void builder_ShouldCreateUserRoleWithAllFields() {
        // Act
        UserRole userRole = UserRole.builder()
                .id(1L)
                .userId(10L)
                .roleId(20L)
                .build();

        // Assert
        assertEquals(1L, userRole.id());
        assertEquals(10L, userRole.userId());
        assertEquals(20L, userRole.roleId());
    }

    @Test
    void record_ShouldBeImmutable() {
        // Arrange
        UserRole userRole1 = UserRole.builder().id(1L).userId(10L).roleId(20L).build();
        UserRole userRole2 = UserRole.builder().id(1L).userId(10L).roleId(20L).build();

        // Assert
        assertEquals(userRole1, userRole2);
        assertEquals(userRole1.hashCode(), userRole2.hashCode());
    }
}
