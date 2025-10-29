package com.skydiveforecast.infrastructure.adapter.in.web.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseDtosTest {

    @Test
    @DisplayName("Should create CreateUserResponse with success")
    void createUserResponse_Success() {
        // Arrange
        UserDto userDto = new UserDto();
        
        // Act
        CreateUserResponse response = CreateUserResponse.success("User created", userDto);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("User created", response.getMessage());
        assertNotNull(response.getUser());
    }

    @Test
    @DisplayName("Should create UpdateUserResponse with success")
    void updateUserResponse_Success() {
        // Arrange
        UserDto userDto = new UserDto();
        
        // Act
        UpdateUserResponse response = UpdateUserResponse.success("User updated", userDto);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("User updated", response.getMessage());
        assertNotNull(response.getUser());
    }

    @Test
    @DisplayName("Should create UpdateUserResponse with error")
    void updateUserResponse_Error() {
        // Arrange & Act
        UpdateUserResponse response = UpdateUserResponse.error("Error occurred");

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Error occurred", response.getMessage());
        assertNull(response.getUser());
    }

    @Test
    @DisplayName("Should create UserStatusUpdateResponse with success")
    void userStatusUpdateResponse_Success() {
        // Arrange & Act
        UserStatusUpdateResponse response = UserStatusUpdateResponse.success("Status updated");

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("Status updated", response.getMessage());
    }

    @Test
    @DisplayName("Should create UserStatusUpdateResponse with error")
    void userStatusUpdateResponse_Error() {
        // Arrange & Act
        UserStatusUpdateResponse response = UserStatusUpdateResponse.error("Error occurred");

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Error occurred", response.getMessage());
    }
}
