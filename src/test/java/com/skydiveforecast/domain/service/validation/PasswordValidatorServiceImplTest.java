package com.skydiveforecast.domain.service.validation;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorServiceImplTest {

    private final PasswordValidatorServiceImpl passwordValidatorService = new PasswordValidatorServiceImpl();

    @Test
    void shouldReturnErrorWhenPasswordIsNull() {
        // Arrange
        String password = null;

        // Act
        Map<String, String> errors = passwordValidatorService.validate(password);

        // Assert
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.containsKey("password"));
        assertEquals("Password is required", errors.get("password"));
    }

    @Test
    void shouldReturnErrorWhenPasswordIsBlank() {
        // Arrange
        String password = " ";

        // Act
        Map<String, String> errors = passwordValidatorService.validate(password);

        // Assert
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertTrue(errors.containsKey("password"));
        assertEquals("Password is required", errors.get("password"));
    }

    @Test
    void shouldReturnErrorWhenPasswordIsTooShort() {
        // Arrange
        String password = "Short1!";

        // Act
        Map<String, String> errors = passwordValidatorService.validate(password);

        // Assert
        assertNotNull(errors);
        assertTrue(errors.containsKey("password"));
        assertEquals("Password must be at least 8 characters long", errors.get("password"));
    }

    @Test
    void shouldReturnErrorWhenPasswordMissingUppercaseLetter() {
        // Arrange
        String password = "password12!";

        // Act
        Map<String, String> errors = passwordValidatorService.validate(password);

        // Assert
        assertNotNull(errors);
        assertTrue(errors.containsKey("password"));
        assertEquals("Password must contain at least one uppercase letter", errors.get("password"));
    }

    @Test
    void shouldReturnErrorWhenPasswordMissingLowercaseLetter() {
        // Arrange
        String password = "PASSWORD12!";

        // Act
        Map<String, String> errors = passwordValidatorService.validate(password);

        // Assert
        assertNotNull(errors);
        assertTrue(errors.containsKey("password"));
        assertEquals("Password must contain at least one lowercase letter", errors.get("password"));
    }

    @Test
    void shouldReturnErrorWhenPasswordMissingDigit() {
        // Arrange
        String password = "Password!!";

        // Act
        Map<String, String> errors = passwordValidatorService.validate(password);

        // Assert
        assertNotNull(errors);
        assertTrue(errors.containsKey("password"));
        assertEquals("Password must contain at least one digit", errors.get("password"));
    }

    @Test
    void shouldReturnErrorWhenPasswordMissingSpecialCharacter() {
        // Arrange
        String password = "Password123";

        // Act
        Map<String, String> errors = passwordValidatorService.validate(password);

        // Assert
        assertNotNull(errors);
        assertTrue(errors.containsKey("password"));
        assertEquals("Password must contain at least one special character: !@#$%^&*()_+-=[]{}:;'\"|,.<>/?", errors.get("password"));
    }

    @Test
    void shouldReturnEmptyErrorsWhenPasswordIsValid() {
        // Arrange
        String password = "Password123!";

        // Act
        Map<String, String> errors = passwordValidatorService.validate(password);

        // Assert
        assertNotNull(errors);
        assertTrue(errors.isEmpty());
    }
}