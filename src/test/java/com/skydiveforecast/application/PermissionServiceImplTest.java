package com.skydiveforecast.application;

import com.skydiveforecast.domain.model.PermissionEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreatePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionsDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UpdatePermissionDto;
import com.skydiveforecast.infrastructure.adapter.out.persistance.PermissionRepository;
import com.skydiveforecast.infrastructure.adapter.out.persistance.RolePermissionRepository;
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
class PermissionServiceImplTest {

    @Mock
    private PermissionRepository permissionRepository;
    @Mock
    private RolePermissionRepository rolePermissionRepository;

    @InjectMocks
    private PermissionServiceImpl permissionService;

    @Test
    @DisplayName("Should get all permissions successfully")
    void getAllPermissions_Success() {
        // Arrange
        PermissionEntity permission = new PermissionEntity();
        permission.setId(1L);
        permission.setCode("USER_READ");
        permission.setDescription("Read users");
        
        when(permissionRepository.findAll()).thenReturn(List.of(permission));

        // Act
        PermissionsDto result = permissionService.getAllPermissions();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getPermissions().size());
        verify(permissionRepository).findAll();
    }

    @Test
    @DisplayName("Should create permission successfully")
    void createPermission_Success() {
        // Arrange
        CreatePermissionDto dto = new CreatePermissionDto();
        dto.setCode("USER_WRITE");
        dto.setDescription("Write users");
        
        PermissionEntity saved = new PermissionEntity();
        saved.setId(1L);
        saved.setCode("USER_WRITE");
        saved.setDescription("Write users");
        
        when(permissionRepository.save(any())).thenReturn(saved);

        // Act
        PermissionDto result = permissionService.createPermission(dto);

        // Assert
        assertNotNull(result);
        assertEquals("USER_WRITE", result.getCode());
        verify(permissionRepository).save(any());
    }

    @Test
    @DisplayName("Should update permission successfully")
    void updatePermission_Success() {
        // Arrange
        Long id = 1L;
        UpdatePermissionDto dto = new UpdatePermissionDto();
        dto.setCode("USER_UPDATE");
        dto.setDescription("Update users");
        
        PermissionEntity existing = new PermissionEntity();
        existing.setId(id);
        
        when(permissionRepository.findById(id)).thenReturn(Optional.of(existing));
        when(permissionRepository.save(any())).thenReturn(existing);

        // Act
        PermissionDto result = permissionService.updatePermission(id, dto);

        // Assert
        assertNotNull(result);
        verify(permissionRepository).save(existing);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when updating non-existent permission")
    void updatePermission_NotFound() {
        // Arrange
        Long id = 999L;
        UpdatePermissionDto dto = new UpdatePermissionDto();
        
        when(permissionRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, 
            () -> permissionService.updatePermission(id, dto));
        verify(permissionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete permission successfully")
    void deletePermission_Success() {
        // Arrange
        Long id = 1L;
        PermissionEntity permission = new PermissionEntity();
        permission.setId(id);
        
        when(permissionRepository.findById(id)).thenReturn(Optional.of(permission));

        // Act
        permissionService.deletePermission(id);

        // Assert
        verify(permissionRepository).deleteById(id);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when deleting non-existent permission")
    void deletePermission_NotFound() {
        // Arrange
        Long id = 999L;
        
        when(permissionRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, 
            () -> permissionService.deletePermission(id));
        verify(permissionRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Should get permission codes by user ID")
    void getPermissionCodesByUserId_Success() {
        // Arrange
        Long userId = 1L;
        Set<String> codes = Set.of("USER_READ", "USER_WRITE");
        
        when(rolePermissionRepository.findPermissionCodesByUserId(userId)).thenReturn(codes);

        // Act
        Set<String> result = permissionService.getPermissionCodesByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("USER_READ"));
        verify(rolePermissionRepository).findPermissionCodesByUserId(userId);
    }
}
