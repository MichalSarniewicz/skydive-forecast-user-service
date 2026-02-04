package com.skydiveforecast.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PermissionTest {

    @Test
    @DisplayName("Should create new permission with different id when withId is called")
    void withId_WhenCalled_ReturnsNewPermissionWithUpdatedId() {
        // Arrange
        Permission original = Permission.builder()
                .id(1L)
                .code("USER_READ")
                .description("Read users")
                .build();

        // Act
        Permission result = original.withId(2L);

        // Assert
        assertEquals(2L, result.id());
        assertEquals(original.code(), result.code());
        assertEquals(original.description(), result.description());
    }

    @Test
    @DisplayName("Should create new permission with different description when withDescription is called")
    void withDescription_WhenCalled_ReturnsNewPermissionWithUpdatedDescription() {
        // Arrange
        Permission original = Permission.builder()
                .id(1L)
                .code("USER_READ")
                .description("Old description")
                .build();

        // Act
        Permission result = original.withDescription("New description");

        // Assert
        assertEquals(original.id(), result.id());
        assertEquals(original.code(), result.code());
        assertEquals("New description", result.description());
    }

    @Test
    @DisplayName("Should create new permission with different updatedAt when withUpdatedAt is called")
    void withUpdatedAt_WhenCalled_ReturnsNewPermissionWithUpdatedTimestamp() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();
        Permission original = Permission.builder()
                .id(1L)
                .code("USER_READ")
                .build();

        // Act
        Permission result = original.withUpdatedAt(now);

        // Assert
        assertEquals(now, result.updatedAt());
    }

    @Test
    @DisplayName("Should build permission with all fields")
    void builder_WhenAllFieldsProvided_CreatesPermissionSuccessfully() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();

        // Act
        Permission permission = Permission.builder()
                .id(1L)
                .code("USER_READ")
                .description("Read users")
                .createdAt(now)
                .updatedAt(now)
                .build();

        // Assert
        assertEquals(1L, permission.id());
        assertEquals("USER_READ", permission.code());
        assertEquals("Read users", permission.description());
        assertEquals(now, permission.createdAt());
        assertEquals(now, permission.updatedAt());
    }
}
