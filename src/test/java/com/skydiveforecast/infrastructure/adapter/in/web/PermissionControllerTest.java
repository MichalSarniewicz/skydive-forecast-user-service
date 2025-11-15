package com.skydiveforecast.infrastructure.adapter.in.web;

import com.skydiveforecast.domain.port.in.CreatePermissionUseCase;
import com.skydiveforecast.domain.port.in.DeletePermissionUseCase;
import com.skydiveforecast.domain.port.in.GetAllPermissionsUseCase;
import com.skydiveforecast.domain.port.in.UpdatePermissionUseCase;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreatePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionsDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UpdatePermissionDto;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        PermissionDto permission = PermissionDto.builder()
                .id(1L)
                .code("USER_READ")
                .description("Read user data")
                .build();
        PermissionsDto dto = new PermissionsDto(List.of(permission));
        when(getAllPermissionsUseCase.getAllPermissions()).thenReturn(dto);

        // Act
        ResponseEntity<PermissionsDto> response = permissionController.getAllPermissions();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getPermissions().size());
        assertEquals("USER_READ", response.getBody().getPermissions().get(0).getCode());
        verify(getAllPermissionsUseCase).getAllPermissions();
    }

    @Test
    @DisplayName("Should create permission successfully")
    void createPermission_Success() {
        // Arrange
        CreatePermissionDto createDto = CreatePermissionDto.builder()
                .code("USER_DELETE")
                .description("Delete user")
                .build();
        PermissionDto result = PermissionDto.builder()
                .id(1L)
                .code("USER_DELETE")
                .description("Delete user")
                .build();
        when(createPermissionUseCase.createPermission(createDto)).thenReturn(result);

        // Act
        ResponseEntity<PermissionDto> response = permissionController.createPermission(createDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("USER_DELETE", response.getBody().getCode());
        verify(createPermissionUseCase).createPermission(createDto);
    }

    @Test
    @DisplayName("Should update permission successfully")
    void updatePermission_Success() {
        // Arrange
        Long id = 1L;
        UpdatePermissionDto updateDto = UpdatePermissionDto.builder()
                .code("USER_UPDATE")
                .description("Update user data")
                .build();
        PermissionDto result = PermissionDto.builder()
                .id(id)
                .code("USER_UPDATE")
                .description("Update user data")
                .build();
        when(updatePermissionUseCase.updatePermission(id, updateDto)).thenReturn(result);

        // Act
        ResponseEntity<PermissionDto> response = permissionController.updatePermission(id, updateDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
        assertEquals("USER_UPDATE", response.getBody().getCode());
        verify(updatePermissionUseCase).updatePermission(id, updateDto);
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
        assertNull(response.getBody());
        verify(deletePermissionUseCase).deletePermission(id);
    }
}
