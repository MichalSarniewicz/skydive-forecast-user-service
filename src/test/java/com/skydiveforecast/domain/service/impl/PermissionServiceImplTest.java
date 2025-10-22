package com.skydiveforecast.domain.service.impl;

import com.skydiveforecast.application.PermissionServiceImpl;
import com.skydiveforecast.domain.model.*;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.*;
import com.skydiveforecast.infrastructure.adapter.out.persistance.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermissionServiceImplTest {

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private PermissionServiceImpl permissionService;

    @Test
    @DisplayName("Should update permission when valid ID and UpdatePermissionDto are provided")
    void testUpdatePermission_Success() {
        // Arrange
        Long id = 1L;
        UpdatePermissionDto updatePermissionDto = UpdatePermissionDto.builder()
                .code("UPDATED_CODE")
                .description("Updated Description")
                .build();
        PermissionEntity existingPermission = PermissionEntity.builder()
                .id(id)
                .code("ORIGINAL_CODE")
                .description("Original Description")
                .build();
        PermissionEntity updatedPermission = PermissionEntity.builder()
                .id(id)
                .code(updatePermissionDto.getCode())
                .description(updatePermissionDto.getDescription())
                .build();

        when(permissionRepository.findById(id)).thenReturn(Optional.of(existingPermission));
        when(permissionRepository.save(any(PermissionEntity.class))).thenReturn(updatedPermission);

        // Act
        PermissionDto result = permissionService.updatePermission(id, updatePermissionDto);

        // Assert
        assertEquals(updatePermissionDto.getCode(), result.getCode());
        assertEquals(updatePermissionDto.getDescription(), result.getDescription());
        verify(permissionRepository, times(1)).findById(id);
        verify(permissionRepository, times(1)).save(existingPermission);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when ID does not exist")
    void testUpdatePermission_EntityNotFound() {
        // Arrange
        Long id = 1L;
        UpdatePermissionDto updatePermissionDto = UpdatePermissionDto.builder()
                .code("UPDATED_CODE")
                .description("Updated Description")
                .build();

        when(permissionRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                permissionService.updatePermission(id, updatePermissionDto));

        assertEquals("Permission not found with id: " + id, exception.getMessage());
        verify(permissionRepository, times(1)).findById(id);
        verify(permissionRepository, times(0)).save(any(PermissionEntity.class));
    }
}