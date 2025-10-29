package com.skydiveforecast.application;

import com.skydiveforecast.domain.model.PermissionEntity;
import com.skydiveforecast.domain.model.RoleEntity;
import com.skydiveforecast.domain.model.RolePermissionEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.AssignPermissionsToRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreateRolePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolePermissionsDto;
import com.skydiveforecast.infrastructure.adapter.out.persistance.PermissionRepository;
import com.skydiveforecast.infrastructure.adapter.out.persistance.RolePermissionRepository;
import com.skydiveforecast.infrastructure.adapter.out.persistance.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
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
class RolePermissionServiceImplTest {

    @Mock
    private RolePermissionRepository rolePermissionRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private RolePermissionServiceImpl rolePermissionService;

    @Test
    @DisplayName("Should get all role permissions successfully")
    void getAllRolePermissions_Success() {
        // Arrange
        RolePermissionEntity entity = createRolePermissionEntity();
        when(rolePermissionRepository.findAll()).thenReturn(List.of(entity));

        // Act
        RolePermissionsDto result = rolePermissionService.getAllRolePermissions();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getRolePermissions().size());
        verify(rolePermissionRepository).findAll();
    }

    @Test
    @DisplayName("Should get role permissions by role ID")
    void getRolePermissionsByRoleId_Success() {
        // Arrange
        Long roleId = 1L;
        RolePermissionEntity entity = createRolePermissionEntity();
        when(rolePermissionRepository.findByRoleId(roleId)).thenReturn(List.of(entity));

        // Act
        RolePermissionsDto result = rolePermissionService.getRolePermissionsByRoleId(roleId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getRolePermissions().size());
        verify(rolePermissionRepository).findByRoleId(roleId);
    }

    @Test
    @DisplayName("Should get role permissions by permission ID")
    void getRolePermissionsByPermissionId_Success() {
        // Arrange
        Long permissionId = 1L;
        RolePermissionEntity entity = createRolePermissionEntity();
        when(rolePermissionRepository.findByPermissionId(permissionId)).thenReturn(List.of(entity));

        // Act
        RolePermissionsDto result = rolePermissionService.getRolePermissionsByPermissionId(permissionId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getRolePermissions().size());
        verify(rolePermissionRepository).findByPermissionId(permissionId);
    }

    @Test
    @DisplayName("Should create role permission successfully")
    void createRolePermission_Success() {
        // Arrange
        CreateRolePermissionDto dto = new CreateRolePermissionDto();
        dto.setRoleId(1L);
        dto.setPermissionId(1L);
        
        RoleEntity role = new RoleEntity();
        role.setId(1L);
        PermissionEntity permission = new PermissionEntity();
        permission.setId(1L);
        RolePermissionEntity saved = createRolePermissionEntity();
        
        when(rolePermissionRepository.existsByRoleIdAndPermissionId(any(), any())).thenReturn(false);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(permissionRepository.findById(1L)).thenReturn(Optional.of(permission));
        when(rolePermissionRepository.save(any())).thenReturn(saved);

        // Act
        RolePermissionDto result = rolePermissionService.createRolePermission(dto);

        // Assert
        assertNotNull(result);
        verify(rolePermissionRepository).save(any());
    }

    @Test
    @DisplayName("Should throw exception when role permission already exists")
    void createRolePermission_AlreadyExists() {
        // Arrange
        CreateRolePermissionDto dto = new CreateRolePermissionDto();
        dto.setRoleId(1L);
        dto.setPermissionId(1L);
        
        when(rolePermissionRepository.existsByRoleIdAndPermissionId(1L, 1L)).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> rolePermissionService.createRolePermission(dto));
        verify(rolePermissionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should assign permissions to role successfully")
    void assignPermissionsToRole_Success() {
        // Arrange
        AssignPermissionsToRoleDto dto = new AssignPermissionsToRoleDto();
        dto.setRoleId(1L);
        dto.setPermissionIds(Set.of(1L, 2L));
        
        RoleEntity role = new RoleEntity();
        role.setId(1L);
        PermissionEntity perm1 = new PermissionEntity();
        perm1.setId(1L);
        PermissionEntity perm2 = new PermissionEntity();
        perm2.setId(2L);
        
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(permissionRepository.findByIdIn(any())).thenReturn(Set.of(perm1, perm2));
        when(rolePermissionRepository.saveAll(any())).thenReturn(List.of(createRolePermissionEntity()));

        // Act
        List<RolePermissionDto> result = rolePermissionService.assignPermissionsToRole(dto);

        // Assert
        assertNotNull(result);
        verify(rolePermissionRepository).deleteByRoleId(1L);
        verify(rolePermissionRepository).saveAll(any());
    }

    @Test
    @DisplayName("Should delete role permission successfully")
    void deleteRolePermission_Success() {
        // Arrange
        Long id = 1L;
        RolePermissionEntity entity = createRolePermissionEntity();
        when(rolePermissionRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        rolePermissionService.deleteRolePermission(id);

        // Assert
        verify(rolePermissionRepository).delete(entity);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when deleting non-existent role permission")
    void deleteRolePermission_NotFound() {
        // Arrange
        Long id = 999L;
        when(rolePermissionRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, 
            () -> rolePermissionService.deleteRolePermission(id));
        verify(rolePermissionRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Should get permission codes by role ID")
    void getPermissionCodesByRoleId_Success() {
        // Arrange
        Long roleId = 1L;
        Set<String> codes = Set.of("USER_READ", "USER_WRITE");
        when(rolePermissionRepository.findPermissionCodesByRoleId(roleId)).thenReturn(codes);

        // Act
        Set<String> result = rolePermissionService.getPermissionCodesByRoleId(roleId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(rolePermissionRepository).findPermissionCodesByRoleId(roleId);
    }

    @Test
    @DisplayName("Should delete all role permissions by role ID")
    void deleteAllRolePermissionsByRoleId_Success() {
        // Arrange
        Long roleId = 1L;
        RolePermissionEntity entity = createRolePermissionEntity();
        when(rolePermissionRepository.findByRoleId(roleId)).thenReturn(List.of(entity));

        // Act
        rolePermissionService.deleteAllRolePermissionsByRoleId(roleId);

        // Assert
        verify(rolePermissionRepository).deleteByIdIn(any());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when no permissions found for role")
    void deleteAllRolePermissionsByRoleId_NotFound() {
        // Arrange
        Long roleId = 999L;
        when(rolePermissionRepository.findByRoleId(roleId)).thenReturn(List.of());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, 
            () -> rolePermissionService.deleteAllRolePermissionsByRoleId(roleId));
    }

    @Test
    @DisplayName("Should delete all role permissions by permission ID")
    void deleteAllRolePermissionsByPermissionId_Success() {
        // Arrange
        Long permissionId = 1L;
        RolePermissionEntity entity = createRolePermissionEntity();
        when(rolePermissionRepository.findByPermissionId(permissionId)).thenReturn(List.of(entity));

        // Act
        rolePermissionService.deleteAllRolePermissionsByPermissionId(permissionId);

        // Assert
        verify(rolePermissionRepository).deleteByIdIn(any());
    }

    private RolePermissionEntity createRolePermissionEntity() {
        RoleEntity role = new RoleEntity();
        role.setId(1L);
        role.setName("USER");
        
        PermissionEntity permission = new PermissionEntity();
        permission.setId(1L);
        permission.setCode("USER_READ");
        permission.setDescription("Read users");
        
        return RolePermissionEntity.builder()
                .id(1L)
                .role(role)
                .permission(permission)
                .build();
    }
}
