package com.skydiveforecast.infrastructure.security;

import com.skydiveforecast.domain.port.out.RolePermissionRepositoryPort;
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

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermissionSecurityServiceTest {

    @Mock
    private RolePermissionRepositoryPort rolePermissionRepository;

    @InjectMocks
    private PermissionSecurityService permissionSecurityService;

    @Test
    @DisplayName("Should return true when user has permission")
    void hasPermission_UserHasPermission() {
        // Arrange
        Long userId = 1L;
        String permission = "USER_READ";
        Set<String> userPermissions = Set.of("USER_READ", "USER_WRITE");

        CustomUserPrincipal userPrincipal = new CustomUserPrincipal(
                userId,
                "test@example.com",
                "password",
                true,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userPrincipal);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(rolePermissionRepository.findPermissionCodesByUserId(userId)).thenReturn(userPermissions);

        // Act
        boolean hasPermission = permissionSecurityService.hasPermission(permission);

        // Assert
        assertTrue(hasPermission);
        verify(rolePermissionRepository).findPermissionCodesByUserId(userId);

        // Cleanup
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should return false when user does not have permission")
    void hasPermission_UserDoesNotHavePermission() {
        // Arrange
        Long userId = 1L;
        String permission = "ADMIN_DELETE";
        Set<String> userPermissions = Set.of("USER_READ", "USER_WRITE");

        CustomUserPrincipal userPrincipal = new CustomUserPrincipal(
                userId,
                "test@example.com",
                "password",
                true,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userPrincipal);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(rolePermissionRepository.findPermissionCodesByUserId(userId)).thenReturn(userPermissions);

        // Act
        boolean hasPermission = permissionSecurityService.hasPermission(permission);

        // Assert
        assertFalse(hasPermission);
        verify(rolePermissionRepository).findPermissionCodesByUserId(userId);

        // Cleanup
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should return false when authentication is null")
    void hasPermission_NoAuthentication() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        // Act
        boolean hasPermission = permissionSecurityService.hasPermission("USER_READ");

        // Assert
        assertFalse(hasPermission);
        verifyNoInteractions(rolePermissionRepository);

        // Cleanup
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should return false when user is not authenticated")
    void hasPermission_NotAuthenticated() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        boolean hasPermission = permissionSecurityService.hasPermission("USER_READ");

        // Assert
        assertFalse(hasPermission);
        verifyNoInteractions(rolePermissionRepository);

        // Cleanup
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should return false when principal is not CustomUserPrincipal")
    void hasPermission_WrongPrincipalType() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("string-principal");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        boolean hasPermission = permissionSecurityService.hasPermission("USER_READ");

        // Assert
        assertFalse(hasPermission);
        verifyNoInteractions(rolePermissionRepository);

        // Cleanup
        SecurityContextHolder.clearContext();
    }
}
