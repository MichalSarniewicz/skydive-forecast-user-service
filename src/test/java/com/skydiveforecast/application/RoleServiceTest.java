package com.skydiveforecast.application;

import com.skydiveforecast.application.service.RoleService;
import com.skydiveforecast.infrastructure.persistence.entity.RoleEntity;
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

import java.time.OffsetDateTime;
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
    void getAllRoles_Success() {
        // Arrange
        RoleEntity role1 = RoleEntity.builder()
                .id(1L)
                .name("USER")
                .createdAt(OffsetDateTime.now())
                .build();

        RoleEntity role2 = RoleEntity.builder()
                .id(2L)
                .name("ADMIN")
                .createdAt(OffsetDateTime.now())
                .build();

        List<RoleEntity> entities = List.of(role1, role2);
        List<RoleDto> dtos = List.of(
                new RoleDto(1L, "USER"),
                new RoleDto(2L, "ADMIN")
        );

        when(roleRepository.findAll()).thenReturn(entities);
        when(roleMapper.toDtoList(entities)).thenReturn(dtos);

        // Act
        RolesDto result = roleService.getAllRoles();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getRoles().size());
        verify(roleRepository).findAll();
        verify(roleMapper).toDtoList(entities);
    }

    @Test
    @DisplayName("Should add role successfully")
    void addRole_Success() {
        // Arrange
        String roleName = "MODERATOR";
        RoleEntity savedRole = RoleEntity.builder()
                .id(1L)
                .name(roleName)
                .createdAt(OffsetDateTime.now())
                .build();

        RoleDto roleDto = new RoleDto(1L, roleName);

        when(roleRepository.save(any(RoleEntity.class))).thenReturn(savedRole);
        when(roleMapper.toDto(savedRole)).thenReturn(roleDto);

        // Act
        RoleDto result = roleService.addRole(roleName);

        // Assert
        assertNotNull(result);
        assertEquals(roleName, result.getName());
        verify(roleRepository).save(any(RoleEntity.class));
        verify(roleMapper).toDto(savedRole);
    }

    @Test
    @DisplayName("Should delete role successfully")
    void deleteRole_Success() {
        Long roleId = 1L;
        RoleEntity role = RoleEntity.builder()
                .id(roleId)
                .name("USER")
                .createdAt(OffsetDateTime.now())
                .build();

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        // Act
        roleService.deleteRole(roleId);

        // Assert
        verify(roleRepository).findById(roleId);
        verify(roleRepository).deleteById(roleId);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when deleting non-existent role")
    void deleteRole_RoleNotFound() {
        // Arrange
        Long roleId = 999L;

        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> roleService.deleteRole(roleId));

        assertEquals("Role with ID " + roleId + " does not exist", exception.getMessage());
        verify(roleRepository).findById(roleId);
        verify(roleRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Should throw BusinessRuleException when deleting ADMIN role")
    void deleteRole_AdminRoleCannotBeDeleted() {
        // Arrange
        Long roleId = 1L;
        RoleEntity adminRole = RoleEntity.builder()
                .id(roleId)
                .name("ADMIN")
                .createdAt(OffsetDateTime.now())
                .build();

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(adminRole));

        // Act & Assert
        Exception exception = assertThrows(Exception.class,
                () -> roleService.deleteRole(roleId));

        assertEquals("The ADMIN role cannot be deleted", exception.getMessage());
        verify(roleRepository).findById(roleId);
        verify(roleRepository, never()).deleteById(any());
    }
}
