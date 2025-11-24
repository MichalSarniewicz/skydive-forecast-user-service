package com.skydiveforecast.domain.service.validation;

import com.skydiveforecast.domain.port.out.UserRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserValidatorImplTest {

    @Mock
    private UserRepositoryPort userRepository;

    @InjectMocks
    private UserValidatorImpl validator;

    @Test
    @DisplayName("Should validate email successfully")
    void validateEmail_Success() {
        // Arrange
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);

        // Act
        Map<String, String> errors = validator.validateEmail("test@example.com", true);

        // Assert
        assertTrue(errors.isEmpty());
        verify(userRepository).existsByEmail("test@example.com");
    }

    @Test
    @DisplayName("Should return error when email is null")
    void validateEmail_Null() {
        // Arrange & Act
        Map<String, String> errors = validator.validateEmail(null, false);

        // Assert
        assertEquals(1, errors.size());
        assertEquals("Email is required", errors.get("email"));
    }

    @Test
    @DisplayName("Should return error when email is blank")
    void validateEmail_Blank() {
        // Arrange & Act
        Map<String, String> errors = validator.validateEmail("  ", false);

        // Assert
        assertEquals(1, errors.size());
        assertEquals("Email is required", errors.get("email"));
    }

    @Test
    @DisplayName("Should return error when email already exists")
    void validateEmail_AlreadyExists() {
        // Arrange
        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        // Act
        Map<String, String> errors = validator.validateEmail("existing@example.com", true);

        // Assert
        assertEquals(1, errors.size());
        assertEquals("Email already in use", errors.get("email"));
        verify(userRepository).existsByEmail("existing@example.com");
    }

    @Test
    @DisplayName("Should not check existence when flag is false")
    void validateEmail_NoExistenceCheck() {
        // Arrange & Act
        Map<String, String> errors = validator.validateEmail("test@example.com", false);

        // Assert
        assertTrue(errors.isEmpty());
        verify(userRepository, never()).existsByEmail(any());
    }

    @Test
    @DisplayName("Should validate names successfully")
    void validateName_Success() {
        // Arrange & Act
        Map<String, String> errors = validator.validateName("John", "Doe");

        // Assert
        assertTrue(errors.isEmpty());
    }

    @Test
    @DisplayName("Should return error when first name is null")
    void validateName_FirstNameNull() {
        // Arrange & Act
        Map<String, String> errors = validator.validateName(null, "Doe");

        // Assert
        assertEquals(1, errors.size());
        assertEquals("First name is required", errors.get("firstName"));
    }

    @Test
    @DisplayName("Should return error when first name is blank")
    void validateName_FirstNameBlank() {
        // Arrange & Act
        Map<String, String> errors = validator.validateName("  ", "Doe");

        // Assert
        assertEquals(1, errors.size());
        assertEquals("First name is required", errors.get("firstName"));
    }

    @Test
    @DisplayName("Should return error when first name exceeds 50 characters")
    void validateName_FirstNameTooLong() {
        // Arrange
        String longName = "a".repeat(51);

        // Act
        Map<String, String> errors = validator.validateName(longName, "Doe");

        // Assert
        assertEquals(1, errors.size());
        assertEquals("First name cannot exceed 50 characters", errors.get("firstName"));
    }

    @Test
    @DisplayName("Should return error when last name is null")
    void validateName_LastNameNull() {
        // Arrange & Act
        Map<String, String> errors = validator.validateName("John", null);

        // Assert
        assertEquals(1, errors.size());
        assertEquals("Last name is required", errors.get("lastName"));
    }

    @Test
    @DisplayName("Should return error when last name is blank")
    void validateName_LastNameBlank() {
        // Arrange & Act
        Map<String, String> errors = validator.validateName("John", "  ");

        // Assert
        assertEquals(1, errors.size());
        assertEquals("Last name is required", errors.get("lastName"));
    }

    @Test
    @DisplayName("Should return error when last name exceeds 50 characters")
    void validateName_LastNameTooLong() {
        // Arrange
        String longName = "a".repeat(51);

        // Act
        Map<String, String> errors = validator.validateName("John", longName);

        // Assert
        assertEquals(1, errors.size());
        assertEquals("Last name cannot exceed 50 characters", errors.get("lastName"));
    }

    @Test
    @DisplayName("Should return multiple errors when both names are invalid")
    void validateName_BothInvalid() {
        // Arrange & Act
        Map<String, String> errors = validator.validateName(null, "");

        // Assert
        assertEquals(2, errors.size());
        assertEquals("First name is required", errors.get("firstName"));
        assertEquals("Last name is required", errors.get("lastName"));
    }

    @Test
    @DisplayName("Should validate phone number successfully")
    void validatePhoneNumber_Success() {
        // Arrange & Act
        Map<String, String> errors = validator.validatePhoneNumber("+1234567890");

        // Assert
        assertTrue(errors.isEmpty());
    }

    @Test
    @DisplayName("Should validate null phone number")
    void validatePhoneNumber_Null() {
        // Arrange & Act
        Map<String, String> errors = validator.validatePhoneNumber(null);

        // Assert
        assertTrue(errors.isEmpty());
    }

    @Test
    @DisplayName("Should return error for invalid phone number")
    void validatePhoneNumber_Invalid() {
        // Arrange & Act
        Map<String, String> errors = validator.validatePhoneNumber("invalid");

        // Assert
        assertEquals(1, errors.size());
        assertEquals("Phone number must be valid", errors.get("phoneNumber"));
    }

    @Test
    @DisplayName("Should validate phone number with 10 digits")
    void validatePhoneNumber_TenDigits() {
        // Arrange & Act
        Map<String, String> errors = validator.validatePhoneNumber("1234567890");

        // Assert
        assertTrue(errors.isEmpty());
    }

    @Test
    @DisplayName("Should validate phone number with 15 digits")
    void validatePhoneNumber_FifteenDigits() {
        // Arrange & Act
        Map<String, String> errors = validator.validatePhoneNumber("123456789012345");

        // Assert
        assertTrue(errors.isEmpty());
    }

    @Test
    @DisplayName("Should return error for phone number with less than 10 digits")
    void validatePhoneNumber_TooShort() {
        // Arrange & Act
        Map<String, String> errors = validator.validatePhoneNumber("123456789");

        // Assert
        assertEquals(1, errors.size());
        assertEquals("Phone number must be valid", errors.get("phoneNumber"));
    }

    @Test
    @DisplayName("Should return error for phone number with more than 15 digits")
    void validatePhoneNumber_TooLong() {
        // Arrange & Act
        Map<String, String> errors = validator.validatePhoneNumber("1234567890123456");

        // Assert
        assertEquals(1, errors.size());
        assertEquals("Phone number must be valid", errors.get("phoneNumber"));
    }
}
