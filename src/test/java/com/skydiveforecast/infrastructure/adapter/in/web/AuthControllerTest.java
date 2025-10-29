package com.skydiveforecast.infrastructure.adapter.in.web;

import com.skydiveforecast.infrastructure.security.AuthBusinessService;
import com.skydiveforecast.infrastructure.security.dto.AuthenticationRequest;
import com.skydiveforecast.infrastructure.security.dto.AuthenticationResponse;
import com.skydiveforecast.infrastructure.security.dto.RefreshTokenRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthBusinessService authBusinessService;

    @InjectMocks
    private AuthController authController;

    @Test
    @DisplayName("Should authenticate successfully")
    void authenticate_Success() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("user@test.com");
        request.setPassword("password");
        AuthenticationResponse result = AuthenticationResponse.builder()
                .accessToken("token")
                .refreshToken("refresh")
                .email("user@test.com")
                .userId(1L)
                .roles(List.of("ROLE_USER"))
                .permissions(Set.of("READ"))
                .isActive(true)
                .build();
        when(authBusinessService.authenticate(any())).thenReturn(result);

        // Act
        ResponseEntity<AuthenticationResponse> response = authController.createAuthenticationToken(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(authBusinessService).authenticate(request);
    }

    @Test
    @DisplayName("Should refresh token successfully")
    void refreshToken_Success() {
        // Arrange
        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken("refresh");
        AuthenticationResponse result = AuthenticationResponse.builder()
                .accessToken("newToken")
                .refreshToken("newRefresh")
                .email("user@test.com")
                .userId(1L)
                .roles(List.of("ROLE_USER"))
                .permissions(Set.of("READ"))
                .isActive(true)
                .build();
        when(authBusinessService.refreshToken(anyString())).thenReturn(result);

        // Act
        ResponseEntity<AuthenticationResponse> response = authController.refreshToken(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(authBusinessService).refreshToken("refresh");
    }
}
