package com.skydiveforecast.application;

import com.skydiveforecast.application.service.RolePermissionService;
import com.skydiveforecast.domain.model.Permission;
import com.skydiveforecast.domain.model.Role;
import com.skydiveforecast.domain.port.out.PermissionRepositoryPort;
import com.skydiveforecast.domain.port.out.RolePermissionRepositoryPort;
import com.skydiveforecast.domain.port.out.RoleRepositoryPort;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreateRolePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolePermissionsDto;
import com.skydiveforecast.infrastructure.persistence.entity.RolePermissionEntity;
import com.skydiveforecast.infrastructure.persistence.mapper.PermissionEntityMapper;
import com.skydiveforecast.infrastructure.persistence.mapper.RoleEntityMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RolePermissionServiceTest {

    @Mock
    private RolePermissionRepositoryPort rolePermissionRepository;

    @Mock
    private RoleRepositoryPort roleRepository;

    @Mock
    private PermissionRepositoryPort permissionRepository;

    @Mock
    private PermissionEntityMapper permissionEntityMapper;

    @Mock
    private RoleEntityMapper roleEntityMapper;

    @InjectMocks
    private RolePermissionService rolePermissionService;

    @Test
    @DisplayName("Should get all role permissions successfully")
    void getAllRolePermissions_WhenRolePermissionsExist_ReturnsAll() {
        // Arrange
        RolePermissionEntity entity = new RolePermissionEntity();
        entity.setId(1L);
        entity.setRole(new com.skydiveforecast.infrastructure.persistence.entity.RoleEntity());
        entity.getRole().setId(1L);
        entity.getRole().setName("ADMIN");
        entity.setPermission(new com.skydiveforecast.infrastructure.persistence.entity.PermissionEntity());
        entity.getPermission().setId(1L);
        entity.getPermission().setCode("USER_READ");

        when(rolePermissionRepository.findAll()).thenReturn(List.of(entity));

        // Act
        RolePermissionsDto result = rolePermissionService.getAllRolePermissions();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getRolePermissions().size());
    }

    @Test
    @DisplayName("Should create role permission successfully")
    void createRolePermission_WhenValidRequest_CreatesRolePermission() {
        // Arrange
        CreateRolePermissionDto dto = new CreateRolePermissionDto();
        dto.setRoleId(1L);
        dto.setPermissionId(2L);

        Role role = Role.builder().id(1L).name("ADMIN").build();
        Permission permission = Permission.builder().id(2L).code("USER_READ").build();
        RolePermissionEntity savedEntity = new RolePermissionEntity();
        savedEntity.setId(1L);
        savedEntity.setRole(new com.skydiveforecast.infrastructure.persistence.entity.RoleEntity());
        savedEntity.getRole().setId(1L);
        savedEntity.getRole().setName("ADMIN");
        savedEntity.setPermission(new com.skydiveforecast.infrastructure.persistence.entity.PermissionEntity());
        savedEntity.getPermission().setId(2L);
        savedEntity.getPermission().setCode("USER_READ");

        when(rolePermissionRepository.existsByRoleIdAndPermissionId(1L, 2L)).thenReturn(false);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(permissionRepository.findById(2L)).thenReturn(Optional.of(permission));
        when(rolePermissionRepository.save(any())).thenReturn(savedEntity);

        // Act
        RolePermissionDto result = rolePermissionService.createRolePermission(dto);

        // Assert
        assertNotNull(result);
        verify(rolePermissionRepository).save(any());
    }

    @Test
    @DisplayName("Should throw exception when role permission already exists")
    void createRolePermission_WhenAlreadyExists_ThrowsException() {
        // Arrange
        CreateRolePermissionDto dto = new CreateRolePermissionDto();
        dto.setRoleId(1L);
        dto.setPermissionId(2L);

        when(rolePermissionRepository.existsByRoleIdAndPermissionId(1L, 2L)).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> rolePermissionService.createRolePermission(dto));
    }

    @Test
    @DisplayName("Should get permission codes by role id successfully")
    void getPermissionCodesByRoleId_WhenRoleHasPermissions_ReturnsCodes() {
        // Arrange
        when(rolePermissionRepository.findPermissionCodesByRoleId(1L)).thenReturn(Set.of("USER_READ", "USER_WRITE"));

        // Act
        Set<String> result = rolePermissionService.getPermissionCodesByRoleId(1L);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains("USER_READ"));
    }
}
