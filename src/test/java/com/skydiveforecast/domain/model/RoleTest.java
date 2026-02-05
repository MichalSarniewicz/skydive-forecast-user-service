package com.skydiveforecast.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    @DisplayName("Should create new role with different id when withId is called")
    void withId_WhenCalled_ReturnsNewRoleWithUpdatedId() {
        // Arrange
        Role original = Role.builder()
                .id(1L)
                .name("ADMIN")
                .build();

        // Act
        Role result = original.withId(2L);

        // Assert
        assertEquals(2L, result.id());
        assertEquals(original.name(), result.name());
    }

    @Test
    @DisplayName("Should create new role with different name when withName is called")
    void withName_WhenCalled_ReturnsNewRoleWithUpdatedName() {
        // Arrange
        Role original = Role.builder()
                .id(1L)
                .name("USER")
                .build();

        // Act
        Role result = original.withName("ADMIN");

        // Assert
        assertEquals(original.id(), result.id());
        assertEquals("ADMIN", result.name());
    }

    @Test
    @DisplayName("Should create new role with different updatedAt when withUpdatedAt is called")
    void withUpdatedAt_WhenCalled_ReturnsNewRoleWithUpdatedTimestamp() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();
        Role original = Role.builder()
                .id(1L)
                .name("ADMIN")
                .build();

        // Act
        Role result = original.withUpdatedAt(now);

        // Assert
        assertEquals(now, result.updatedAt());
    }

    @Test
    @DisplayName("Should build role with all fields")
    void builder_WhenAllFieldsProvided_CreatesRoleSuccessfully() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();

        // Act
        Role role = Role.builder()
                .id(1L)
                .name("ADMIN")
                .createdAt(now)
                .updatedAt(now)
                .build();

        // Assert
        assertEquals(1L, role.id());
        assertEquals("ADMIN", role.name());
        assertEquals(now, role.createdAt());
        assertEquals(now, role.updatedAt());
    }
}
