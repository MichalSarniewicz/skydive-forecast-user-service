package com.skydiveforecast.infrastructure.security;

import com.skydiveforecast.domain.port.in.GetPermissionCodesByUserIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private GetPermissionCodesByUserIdUseCase getPermissionCodesByUserIdUseCase;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(authService, "secretKey", "testSecretKeyThatIsLongEnoughForHS256Algorithm");
        ReflectionTestUtils.setField(authService, "jwtExpiration", 3600000L);
        ReflectionTestUtils.setField(authService, "refreshExpiration", 604800000L);
    }

    @Test
    @DisplayName("Should generate token with roles and permissions for CustomUserPrincipal")
    void generateToken_WithCustomUserPrincipal() {
        // Arrange
        Long userId = 1L;
        Set<String> permissions = Set.of("USER_READ", "USER_WRITE");
        CustomUserPrincipal userPrincipal = new CustomUserPrincipal(
                userId,
                "test@example.com",
                "password",
                true,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        when(getPermissionCodesByUserIdUseCase.getPermissionCodesByUserId(userId)).thenReturn(permissions);

        // Act
        String token = authService.generateToken(userPrincipal);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        verify(getPermissionCodesByUserIdUseCase).getPermissionCodesByUserId(userId);
    }

    @Test
    @DisplayName("Should generate token without permissions for regular UserDetails")
    void generateToken_WithRegularUserDetails() {
        // Arrange
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");
        when(userDetails.getAuthorities()).thenAnswer(invocation -> List.of(new SimpleGrantedAuthority("ROLE_USER")));

        // Act
        String token = authService.generateToken(userDetails);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        verifyNoInteractions(getPermissionCodesByUserIdUseCase);
    }

    @Test
    @DisplayName("Should extract username from token")
    void extractUsername() {
        // Arrange
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");
        when(userDetails.getAuthorities()).thenReturn(List.of());
        String token = authService.generateToken(userDetails);

        // Act
        String username = authService.extractUsername(token);

        // Assert
        assertEquals("test@example.com", username);
    }

    @Test
    @DisplayName("Should validate token successfully")
    void validateToken_Success() {
        // Arrange
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");
        when(userDetails.getAuthorities()).thenReturn(List.of());
        String token = authService.generateToken(userDetails);

        // Act
        boolean isValid = authService.validateToken(token, userDetails);

        // Assert
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should fail validation for wrong username")
    void validateToken_WrongUsername() {
        // Arrange
        UserDetails userDetails1 = mock(UserDetails.class);
        when(userDetails1.getUsername()).thenReturn("test1@example.com");
        when(userDetails1.getAuthorities()).thenReturn(List.of());
        String token = authService.generateToken(userDetails1);

        UserDetails userDetails2 = mock(UserDetails.class);
        when(userDetails2.getUsername()).thenReturn("test2@example.com");

        // Act
        boolean isValid = authService.validateToken(token, userDetails2);

        // Assert
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should generate refresh token")
    void generateRefreshToken() {
        // Arrange
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        // Act
        String refreshToken = authService.generateRefreshToken(userDetails);

        // Assert
        assertNotNull(refreshToken);
        assertFalse(refreshToken.isEmpty());
    }

    @Test
    @DisplayName("Should validate refresh token successfully")
    void validateRefreshToken_Success() {
        // Arrange
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");
        String refreshToken = authService.generateRefreshToken(userDetails);

        // Act
        boolean isValid = authService.validateRefreshToken(refreshToken);

        // Assert
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should fail validation for invalid refresh token")
    void validateRefreshToken_Invalid() {
        // Arrange
        String invalidToken = "invalid.token.here";

        // Act
        boolean isValid = authService.validateRefreshToken(invalidToken);

        // Assert
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should extract username from refresh token")
    void getUsernameFromRefreshToken() {
        // Arrange
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");
        String refreshToken = authService.generateRefreshToken(userDetails);

        // Act
        String username = authService.getUsernameFromRefreshToken(refreshToken);

        // Assert
        assertEquals("test@example.com", username);
    }

    @Test
    @DisplayName("Should get current user ID from security context")
    void getCurrentUserId_Success() {
        // Arrange
        Long userId = 1L;
        CustomUserPrincipal userPrincipal = new CustomUserPrincipal(
                userId,
                "test@example.com",
                "password",
                true,
                List.of()
        );

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userPrincipal);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        Long currentUserId = authService.getCurrentUserId();

        // Assert
        assertEquals(userId, currentUserId);

        // Cleanup
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should return null when no authentication in context")
    void getCurrentUserId_NoAuthentication() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        // Act
        Long currentUserId = authService.getCurrentUserId();

        // Assert
        assertNull(currentUserId);

        // Cleanup
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should return null when principal is not CustomUserPrincipal")
    void getCurrentUserId_WrongPrincipal() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn("string-principal");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        Long currentUserId = authService.getCurrentUserId();

        // Assert
        assertNull(currentUserId);

        // Cleanup
        SecurityContextHolder.clearContext();
    }
}
