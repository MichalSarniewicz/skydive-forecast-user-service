package com.skydiveforecast.infrastructure.adapter.in.web;

import com.skydiveforecast.domain.port.in.*;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermissionControllerTest {

    @Mock
    private GetAllPermissionsUseCase getAllPermissionsUseCase;
    @Mock
    private CreatePermissionUseCase createPermissionUseCase;
    @Mock
    private UpdatePermissionUseCase updatePermissionUseCase;
    @Mock
    private DeletePermissionUseCase deletePermissionUseCase;

    @InjectMocks
    private PermissionController permissionController;

    @Test
    @DisplayName("Should get all permissions successfully")
    void getAllPermissions_Success() {
        // Arrange
        PermissionsDto dto = new PermissionsDto(List.of(new PermissionDto()));
        when(getAllPermissionsUseCase.getAllPermissions()).thenReturn(dto);

        // Act
        ResponseEntity<PermissionsDto> response = permissionController.getAllPermissions();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(getAllPermissionsUseCase).getAllPermissions();
    }

    @Test
    @DisplayName("Should create permission successfully")
    void createPermission_Success() {
        // Arrange
        CreatePermissionDto dto = new CreatePermissionDto();
        PermissionDto result = new PermissionDto();
        when(createPermissionUseCase.createPermission(any())).thenReturn(result);

        // Act
        ResponseEntity<PermissionDto> response = permissionController.createPermission(dto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(createPermissionUseCase).createPermission(dto);
    }

    @Test
    @DisplayName("Should update permission successfully")
    void updatePermission_Success() {
        // Arrange
        Long id = 1L;
        UpdatePermissionDto dto = new UpdatePermissionDto();
        PermissionDto result = new PermissionDto();
        when(updatePermissionUseCase.updatePermission(any(), any())).thenReturn(result);

        // Act
        ResponseEntity<PermissionDto> response = permissionController.updatePermission(id, dto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(updatePermissionUseCase).updatePermission(id, dto);
    }

    @Test
    @DisplayName("Should delete permission successfully")
    void deletePermission_Success() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Void> response = permissionController.deletePermission(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deletePermissionUseCase).deletePermission(id);
    }
}
