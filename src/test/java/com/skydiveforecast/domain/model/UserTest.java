package com.skydiveforecast.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void withId_ShouldReturnNewUserWithUpdatedId() {
        // Arrange
        User user = User.builder().id(1L).email("test@test.com").firstName("John").lastName("Doe").passwordHash("hash").isActive(true).build();

        // Act
        User result = user.withId(2L);

        // Assert
        assertEquals(2L, result.id());
        assertEquals("test@test.com", result.email());
    }

    @Test
    void withPasswordHash_ShouldReturnNewUserWithUpdatedPassword() {
        // Arrange
        User user = User.builder().id(1L).email("test@test.com").firstName("John").lastName("Doe").passwordHash("old").isActive(true).build();

        // Act
        User result = user.withPasswordHash("new");

        // Assert
        assertEquals("new", result.passwordHash());
        assertEquals(1L, result.id());
    }

    @Test
    void withIsActive_ShouldReturnNewUserWithUpdatedStatus() {
        // Arrange
        User user = User.builder().id(1L).email("test@test.com").firstName("John").lastName("Doe").passwordHash("hash").isActive(true).build();

        // Act
        User result = user.withIsActive(false);

        // Assert
        assertFalse(result.isActive());
        assertEquals(1L, result.id());
    }

    @Test
    void builder_ShouldCreateUserWithAllFields() {
        // Act
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123456789")
                .passwordHash("hash")
                .isActive(true)
                .build();

        // Assert
        assertEquals(1L, user.id());
        assertEquals("test@test.com", user.email());
        assertEquals("John", user.firstName());
        assertEquals("Doe", user.lastName());
        assertEquals("123456789", user.phoneNumber());
        assertEquals("hash", user.passwordHash());
        assertTrue(user.isActive());
    }
}
