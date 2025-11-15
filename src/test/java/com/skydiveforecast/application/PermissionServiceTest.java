package com.skydiveforecast.application;

import com.skydiveforecast.application.service.PermissionService;
import com.skydiveforecast.domain.model.PermissionEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreatePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionsDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UpdatePermissionDto;
import com.skydiveforecast.domain.port.out.PermissionRepositoryPort;
import com.skydiveforecast.domain.port.out.RolePermissionRepositoryPort;
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
class PermissionServiceTest {

    @Mock
    private PermissionRepositoryPort permissionRepository;

    @Mock
    private RolePermissionRepositoryPort rolePermissionRepository;

    @Mock
    private com.skydiveforecast.infrastructure.adapter.in.web.mapper.PermissionMapper permissionMapper;

    @InjectMocks
    private PermissionService permissionService;

    @Test
    @DisplayName("Should get all permissions successfully")
    void getAllPermissions_Success() {
        // Arrange
        PermissionEntity permission = new PermissionEntity();
        permission.setId(1L);
        permission.setCode("USER_READ");
        permission.setDescription("Read users");
        
        PermissionDto dto = PermissionDto.builder()
                .id(1L)
                .code("USER_READ")
                .description("Read users")
                .build();
        
        when(permissionRepository.findAll()).thenReturn(List.of(permission));
        when(permissionMapper.toDtoList(any())).thenReturn(List.of(dto));

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
        
        PermissionEntity entity = new PermissionEntity();
        entity.setCode("USER_WRITE");
        entity.setDescription("Write users");
        
        PermissionEntity saved = new PermissionEntity();
        saved.setId(1L);
        saved.setCode("USER_WRITE");
        saved.setDescription("Write users");
        
        PermissionDto resultDto = PermissionDto.builder()
                .id(1L)
                .code("USER_WRITE")
                .description("Write users")
                .build();
        
        when(permissionMapper.toEntity(dto)).thenReturn(entity);
        when(permissionRepository.save(entity)).thenReturn(saved);
        when(permissionMapper.toDto(saved)).thenReturn(resultDto);

        // Act
        PermissionDto result = permissionService.createPermission(dto);

        // Assert
        assertNotNull(result);
        assertEquals("USER_WRITE", result.getCode());
        verify(permissionRepository).save(entity);
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
        
        PermissionDto resultDto = PermissionDto.builder()
                .id(id)
                .code("USER_UPDATE")
                .description("Update users")
                .build();
        
        when(permissionRepository.findById(id)).thenReturn(Optional.of(existing));
        when(permissionRepository.save(existing)).thenReturn(existing);
        when(permissionMapper.toDto(existing)).thenReturn(resultDto);

        // Act
        PermissionDto result = permissionService.updatePermission(id, dto);

        // Assert
        assertNotNull(result);
        verify(permissionMapper).updateEntityFromDto(dto, existing);
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
