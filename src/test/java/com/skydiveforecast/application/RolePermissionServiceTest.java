package com.skydiveforecast.application;

import com.skydiveforecast.application.service.RolePermissionService;
import com.skydiveforecast.domain.model.Permission;
import com.skydiveforecast.domain.model.Role;
import com.skydiveforecast.domain.model.RolePermission;
import com.skydiveforecast.domain.port.out.PermissionRepositoryPort;
import com.skydiveforecast.domain.port.out.RolePermissionRepositoryPort;
import com.skydiveforecast.domain.port.out.RoleRepositoryPort;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreateRolePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolePermissionsDto;
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
        Role role = Role.builder().id(1L).name("ADMIN").build();
        Permission permission = Permission.builder().id(1L).code("USER_READ").build();
        RolePermission rolePermission = RolePermission.builder().id(1L).roleId(1L).permissionId(1L).build();

        when(rolePermissionRepository.findAll()).thenReturn(List.of(rolePermission));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(permissionRepository.findById(1L)).thenReturn(Optional.of(permission));

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
        RolePermission savedRolePermission = RolePermission.builder().id(1L).roleId(1L).permissionId(2L).build();

        when(rolePermissionRepository.existsByRoleIdAndPermissionId(1L, 2L)).thenReturn(false);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(permissionRepository.findById(2L)).thenReturn(Optional.of(permission));
        when(rolePermissionRepository.save(any())).thenReturn(savedRolePermission);

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

    @Test
    @DisplayName("Should get role permissions by role id")
    void getRolePermissionsByRoleId_Success() {
        // Arrange
        Role role = Role.builder().id(1L).name("ADMIN").build();
        Permission permission = Permission.builder().id(1L).code("USER_READ").build();
        RolePermission rolePermission = RolePermission.builder().id(1L).roleId(1L).permissionId(1L).build();

        when(rolePermissionRepository.findByRoleId(1L)).thenReturn(List.of(rolePermission));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(permissionRepository.findById(1L)).thenReturn(Optional.of(permission));

        // Act
        RolePermissionsDto result = rolePermissionService.getRolePermissionsByRoleId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getRolePermissions().size());
    }

    @Test
    @DisplayName("Should get role permissions by permission id")
    void getRolePermissionsByPermissionId_Success() {
        // Arrange
        Role role = Role.builder().id(1L).name("ADMIN").build();
        Permission permission = Permission.builder().id(1L).code("USER_READ").build();
        RolePermission rolePermission = RolePermission.builder().id(1L).roleId(1L).permissionId(1L).build();

        when(rolePermissionRepository.findByPermissionId(1L)).thenReturn(List.of(rolePermission));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(permissionRepository.findById(1L)).thenReturn(Optional.of(permission));

        // Act
        RolePermissionsDto result = rolePermissionService.getRolePermissionsByPermissionId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getRolePermissions().size());
    }

    @Test
    @DisplayName("Should delete role permission successfully")
    void deleteRolePermission_Success() {
        // Arrange
        RolePermission rolePermission = RolePermission.builder().id(1L).roleId(1L).permissionId(1L).build();
        when(rolePermissionRepository.findById(1L)).thenReturn(Optional.of(rolePermission));

        // Act
        rolePermissionService.deleteRolePermission(1L);

        // Assert
        verify(rolePermissionRepository).delete(rolePermission);
    }

    @Test
    @DisplayName("Should throw exception when role not found")
    void createRolePermission_WhenRoleNotFound_ThrowsException() {
        // Arrange
        CreateRolePermissionDto dto = new CreateRolePermissionDto();
        dto.setRoleId(1L);
        dto.setPermissionId(2L);

        when(rolePermissionRepository.existsByRoleIdAndPermissionId(1L, 2L)).thenReturn(false);
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> rolePermissionService.createRolePermission(dto));
    }

    @Test
    @DisplayName("Should throw exception when permission not found")
    void createRolePermission_WhenPermissionNotFound_ThrowsException() {
        // Arrange
        CreateRolePermissionDto dto = new CreateRolePermissionDto();
        dto.setRoleId(1L);
        dto.setPermissionId(2L);

        Role role = Role.builder().id(1L).name("ADMIN").build();

        when(rolePermissionRepository.existsByRoleIdAndPermissionId(1L, 2L)).thenReturn(false);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(permissionRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> rolePermissionService.createRolePermission(dto));
    }

    @Test
    @DisplayName("Should delete role permission when not found throws exception")
    void deleteRolePermission_NotFound_ThrowsException() {
        // Arrange
        when(rolePermissionRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(jakarta.persistence.EntityNotFoundException.class, 
            () -> rolePermissionService.deleteRolePermission(999L));
    }
}
