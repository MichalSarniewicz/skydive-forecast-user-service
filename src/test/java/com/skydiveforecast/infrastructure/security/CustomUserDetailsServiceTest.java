package com.skydiveforecast.infrastructure.security;

import com.skydiveforecast.domain.model.RoleEntity;
import com.skydiveforecast.domain.model.UserEntity;
import com.skydiveforecast.domain.model.UserRoleEntity;
import com.skydiveforecast.domain.port.out.UserRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepositoryPort userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @DisplayName("Should load user by username successfully")
    void loadUserByUsername_Success() {
        // Arrange
        String email = "test@example.com";
        RoleEntity role = RoleEntity.builder()
                .id(1L)
                .name("USER")
                .build();

        UserRoleEntity userRole = UserRoleEntity.builder()
                .role(role)
                .build();

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail(email);
        user.setPasswordHash("hashed-password");
        user.setActive(true);
        user.setRoles(new java.util.HashSet<>(List.of(userRole)));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertTrue(userDetails instanceof CustomUserPrincipal);
        CustomUserPrincipal principal = (CustomUserPrincipal) userDetails;
        assertEquals(1L, principal.getUserId());
        assertEquals(email, principal.getUsername());
        assertEquals("hashed-password", principal.getPassword());
        assertTrue(principal.isActive());
        assertTrue(principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        verify(userRepository).findByEmail(email);
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user not found")
    void loadUserByUsername_UserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername(email));

        assertEquals("User not found with email: " + email, exception.getMessage());
        verify(userRepository).findByEmail(email);
    }

    @Test
    @DisplayName("Should load user with multiple roles")
    void loadUserByUsername_MultipleRoles() {
        // Arrange
        String email = "admin@example.com";
        RoleEntity adminRole = RoleEntity.builder()
                .id(1L)
                .name("ADMIN")
                .build();

        RoleEntity userRole = RoleEntity.builder()
                .id(2L)
                .name("USER")
                .build();

        UserRoleEntity userRoleEntity1 = UserRoleEntity.builder()
                .role(adminRole)
                .build();

        UserRoleEntity userRoleEntity2 = UserRoleEntity.builder()
                .role(userRole)
                .build();

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail(email);
        user.setPasswordHash("hashed-password");
        user.setFirstName("Admin");
        user.setLastName("User");
        user.setPhoneNumber(null);
        user.setActive(true);
        user.setRoles(new java.util.HashSet<>(List.of(userRoleEntity1, userRoleEntity2)));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(2, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        verify(userRepository).findByEmail(email);
    }
}
