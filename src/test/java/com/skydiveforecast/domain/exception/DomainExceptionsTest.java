package com.skydiveforecast.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class DomainExceptionsTest {

    @Test
    @DisplayName("Should create InvalidCredentialsException with message")
    void invalidCredentialsException() {
        // Arrange
        String message = "Invalid username or password";

        // Act
        InvalidCredentialsException exception = new InvalidCredentialsException(message);

        // Assert
        assertEquals(message, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    @DisplayName("Should create InvalidTokenException with message")
    void invalidTokenException() {
        // Arrange
        String message = "Token has expired";

        // Act
        InvalidTokenException exception = new InvalidTokenException(message);

        // Assert
        assertEquals(message, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    @DisplayName("Should create ValidationException with message and field errors")
    void validationException() {
        // Arrange
        String message = "Validation failed";
        Map<String, String> fieldErrors = Map.of("password", "Password must be at least 8 characters");

        // Act
        ValidationException exception = new ValidationException(message, fieldErrors);

        // Assert
        assertEquals(message, exception.getMessage());
        assertEquals(fieldErrors, exception.getFieldErrors());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    @DisplayName("Should create ValidationException with default message")
    void validationExceptionWithDefaultMessage() {
        // Arrange
        Map<String, String> fieldErrors = Map.of("email", "Email is required");

        // Act
        ValidationException exception = new ValidationException(fieldErrors);

        // Assert
        assertEquals("Validation failed", exception.getMessage());
        assertEquals(fieldErrors, exception.getFieldErrors());
        assertInstanceOf(RuntimeException.class, exception);
    }
}
