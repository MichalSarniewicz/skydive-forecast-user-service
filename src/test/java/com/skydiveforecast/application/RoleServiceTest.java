package com.skydiveforecast.application;

import com.skydiveforecast.application.service.RoleService;
import com.skydiveforecast.domain.exception.BusinessRuleException;
import com.skydiveforecast.domain.model.Role;
import com.skydiveforecast.domain.port.out.RoleRepositoryPort;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolesDto;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.RoleMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepositoryPort roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleService roleService;

    @Test
    @DisplayName("Should get all roles successfully")
    void getAllRoles_WhenRolesExist_ReturnsAllRoles() {
        // Arrange
        Role role = Role.builder().id(1L).name("ADMIN").build();
        RoleDto dto = new RoleDto(1L, "ADMIN");

        when(roleRepository.findAll()).thenReturn(List.of(role));
        when(roleMapper.toDtoList(any())).thenReturn(List.of(dto));

        // Act
        RolesDto result = roleService.getAllRoles();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getRoles().size());
        verify(roleRepository).findAll();
    }

    @Test
    @DisplayName("Should add role successfully")
    void addRole_WhenValidName_CreatesRole() {
        // Arrange
        Role savedRole = Role.builder().id(1L).name("USER").build();
        RoleDto dto = new RoleDto(1L, "USER");

        when(roleRepository.save(any())).thenReturn(savedRole);
        when(roleMapper.toDto(savedRole)).thenReturn(dto);

        // Act
        RoleDto result = roleService.addRole("USER");

        // Assert
        assertNotNull(result);
        assertEquals("USER", result.getName());
        verify(roleRepository).save(any());
    }

    @Test
    @DisplayName("Should delete role successfully")
    void deleteRole_WhenRoleExists_DeletesRole() {
        // Arrange
        Role role = Role.builder().id(1L).name("USER").build();

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        // Act
        roleService.deleteRole(1L);

        // Assert
        verify(roleRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting ADMIN role")
    void deleteRole_WhenAdminRole_ThrowsException() {
        // Arrange
        Role adminRole = Role.builder().id(1L).name("ADMIN").build();

        when(roleRepository.findById(1L)).thenReturn(Optional.of(adminRole));

        // Act & Assert
        assertThrows(BusinessRuleException.class, () -> roleService.deleteRole(1L));
        verify(roleRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent role")
    void deleteRole_WhenRoleNotExists_ThrowsException() {
        // Arrange
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> roleService.deleteRole(1L));
        verify(roleRepository, never()).deleteById(any());
    }
}
