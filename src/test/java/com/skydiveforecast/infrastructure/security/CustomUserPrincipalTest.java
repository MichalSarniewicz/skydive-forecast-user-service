package com.skydiveforecast.infrastructure.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserPrincipalTest {

    @Test
    @DisplayName("Should create CustomUserPrincipal with all properties")
    void customUserPrincipal_Creation() {
        // Arrange
        Long userId = 1L;
        String username = "test@example.com";
        String password = "password";
        boolean isActive = true;
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        // Act
        CustomUserPrincipal principal = new CustomUserPrincipal(userId, username, password, isActive, authorities);

        // Assert
        assertEquals(userId, principal.getUserId());
        assertEquals(username, principal.getUsername());
        assertEquals(password, principal.getPassword());
        assertTrue(principal.isActive());
        assertEquals(authorities, principal.getAuthorities());
    }

    @Test
    @DisplayName("Should return true for isAccountNonExpired")
    void isAccountNonExpired() {
        // Arrange
        CustomUserPrincipal principal = new CustomUserPrincipal(
                1L, "test@example.com", "password", true, List.of()
        );

        // Act & Assert
        assertTrue(principal.isAccountNonExpired());
    }

    @Test
    @DisplayName("Should return isActive for isAccountNonLocked")
    void isAccountNonLocked() {
        // Arrange
        CustomUserPrincipal activePrincipal = new CustomUserPrincipal(
                1L, "test@example.com", "password", true, List.of()
        );
        CustomUserPrincipal inactivePrincipal = new CustomUserPrincipal(
                2L, "test2@example.com", "password", false, List.of()
        );

        // Act & Assert
        assertTrue(activePrincipal.isAccountNonLocked());
        assertFalse(inactivePrincipal.isAccountNonLocked());
    }

    @Test
    @DisplayName("Should return true for isCredentialsNonExpired")
    void isCredentialsNonExpired() {
        // Arrange
        CustomUserPrincipal principal = new CustomUserPrincipal(
                1L, "test@example.com", "password", true, List.of()
        );

        // Act & Assert
        assertTrue(principal.isCredentialsNonExpired());
    }

    @Test
    @DisplayName("Should return isActive for isEnabled")
    void isEnabled() {
        // Arrange
        CustomUserPrincipal activePrincipal = new CustomUserPrincipal(
                1L, "test@example.com", "password", true, List.of()
        );
        CustomUserPrincipal inactivePrincipal = new CustomUserPrincipal(
                2L, "test2@example.com", "password", false, List.of()
        );

        // Act & Assert
        assertTrue(activePrincipal.isEnabled());
        assertFalse(inactivePrincipal.isEnabled());
    }
}
