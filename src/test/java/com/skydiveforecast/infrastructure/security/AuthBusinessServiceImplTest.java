package com.skydiveforecast.infrastructure.security;

import com.skydiveforecast.domain.port.in.GetPermissionCodesByUserIdUseCase;
import com.skydiveforecast.infrastructure.security.dto.AuthenticationRequest;
import com.skydiveforecast.infrastructure.security.dto.AuthenticationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthBusinessServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private AuthService authService;

    @Mock
    private GetPermissionCodesByUserIdUseCase getPermissionCodesByUserIdUseCase;

    @InjectMocks
    private AuthBusinessServiceImpl authBusinessService;

    @Test
    @DisplayName("Should authenticate user successfully")
    void authenticate_Success() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("test@example.com");
        request.setPassword("password");
        Long userId = 1L;
        Set<String> permissions = Set.of("USER_READ", "USER_WRITE");
        CustomUserPrincipal userPrincipal = new CustomUserPrincipal(
                userId,
                "test@example.com",
                "password",
                true,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(userPrincipal);
        when(authService.generateToken(userPrincipal)).thenReturn("access-token");
        when(authService.generateRefreshToken(userPrincipal)).thenReturn("refresh-token");
        when(getPermissionCodesByUserIdUseCase.getPermissionCodesByUserId(userId)).thenReturn(permissions);

        // Act
        AuthenticationResponse response = authBusinessService.authenticate(request);

        // Assert
        assertNotNull(response);
        assertEquals("access-token", response.accessToken());
        assertEquals("refresh-token", response.refreshToken());
        assertEquals("test@example.com", response.email());
        assertEquals(userId, response.userId());
        assertTrue(response.isActive());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService).loadUserByUsername("test@example.com");
        verify(authService).generateToken(userPrincipal);
        verify(authService).generateRefreshToken(userPrincipal);
    }

    @Test
    @DisplayName("Should throw BadCredentialsException for invalid credentials")
    void authenticate_InvalidCredentials() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("test@example.com");
        request.setPassword("wrong-password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act & Assert
        BadCredentialsException exception = assertThrows(BadCredentialsException.class,
                () -> authBusinessService.authenticate(request));

        assertEquals("Invalid username or password", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(userDetailsService);
    }

    @Test
    @DisplayName("Should refresh token successfully")
    void refreshToken_Success() {
        // Arrange
        String refreshToken = "valid-refresh-token";
        CustomUserPrincipal userPrincipal = new CustomUserPrincipal(
                1L,
                "test@example.com",
                "password",
                true,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        when(authService.validateRefreshToken(refreshToken)).thenReturn(true);
        when(authService.getUsernameFromRefreshToken(refreshToken)).thenReturn("test@example.com");
        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(userPrincipal);
        when(authService.generateToken(userPrincipal)).thenReturn("new-access-token");
        when(authService.generateRefreshToken(userPrincipal)).thenReturn("new-refresh-token");

        // Act
        AuthenticationResponse response = authBusinessService.refreshToken(refreshToken);

        // Assert
        assertNotNull(response);
        assertEquals("new-access-token", response.accessToken());
        assertEquals("new-refresh-token", response.refreshToken());
        assertEquals("test@example.com", response.email());
        verify(authService).validateRefreshToken(refreshToken);
        verify(authService).getUsernameFromRefreshToken(refreshToken);
        verify(userDetailsService).loadUserByUsername("test@example.com");
    }

    @Test
    @DisplayName("Should throw BadCredentialsException for invalid refresh token")
    void refreshToken_InvalidToken() {
        // Arrange
        String invalidRefreshToken = "invalid-refresh-token";

        when(authService.validateRefreshToken(invalidRefreshToken)).thenReturn(false);

        // Act & Assert
        BadCredentialsException exception = assertThrows(BadCredentialsException.class,
                () -> authBusinessService.refreshToken(invalidRefreshToken));

        assertEquals("Invalid refresh token", exception.getMessage());
        verify(authService).validateRefreshToken(invalidRefreshToken);
        verify(authService, never()).getUsernameFromRefreshToken(anyString());
        verifyNoInteractions(userDetailsService);
    }
}
