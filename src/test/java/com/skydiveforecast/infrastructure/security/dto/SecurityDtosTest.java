package com.skydiveforecast.infrastructure.security.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurityDtosTest {

    @Test
    @DisplayName("Should create AuthenticationRequest with username and password")
    void authenticationRequest() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();

        // Act
        request.setUsername("test@example.com");
        request.setPassword("password123");

        // Assert
        assertEquals("test@example.com", request.getUsername());
        assertEquals("password123", request.getPassword());
    }

    @Test
    @DisplayName("Should create AuthenticationResponse with all fields")
    void authenticationResponse() {
        // Arrange
        String accessToken = "access-token";
        String refreshToken = "refresh-token";
        String email = "test@example.com";
        Long userId = 1L;
        List<String> roles = List.of("ROLE_USER");
        Set<String> permissions = Set.of("USER_READ", "USER_WRITE");
        boolean isActive = true;

        // Act
        AuthenticationResponse response = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(email)
                .userId(userId)
                .roles(roles)
                .permissions(permissions)
                .isActive(isActive)
                .build();

        // Assert
        assertEquals(accessToken, response.accessToken());
        assertEquals(refreshToken, response.refreshToken());
        assertEquals(email, response.email());
        assertEquals(userId, response.userId());
        assertEquals(roles, response.roles());
        assertEquals(permissions, response.permissions());
        assertTrue(response.isActive());
    }
}
