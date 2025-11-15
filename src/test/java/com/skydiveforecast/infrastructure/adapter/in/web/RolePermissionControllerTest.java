package com.skydiveforecast.infrastructure.adapter.in.web;

import com.skydiveforecast.domain.port.in.*;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.AssignPermissionsToRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreateRolePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolePermissionsDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RolePermissionControllerTest {

    @Mock
    private GetAllRolePermissionsUseCase getAllRolePermissionsUseCase;

    @Mock
    private GetRolePermissionsByRoleIdUseCase getRolePermissionsByRoleIdUseCase;

    @Mock
    private GetRolePermissionsByPermissionIdUseCase getRolePermissionsByPermissionIdUseCase;

    @Mock
    private CreateRolePermissionUseCase createRolePermissionUseCase;

    @Mock
    private AssignPermissionsToRoleUseCase assignPermissionsToRoleUseCase;

    @Mock
    private DeleteRolePermissionUseCase deleteRolePermissionUseCase;

    @Mock
    private GetPermissionCodesByRoleIdUseCase getPermissionCodesByRoleIdUseCase;

    @Mock
    private DeleteAllRolePermissionsByRoleIdUseCase deleteAllRolePermissionsByRoleIdUseCase;

    @Mock
    private DeleteAllRolePermissionsByPermissionIdUseCase deleteAllRolePermissionsByPermissionIdUseCase;

    @InjectMocks
    private RolePermissionController adminRolePermissionController;

    @Test
    void getAllRolePermissions_ShouldReturnAllRolePermissions() {
        // Arrange
        RolePermissionsDto expectedDto = RolePermissionsDto.builder().build();
        when(getAllRolePermissionsUseCase.getAllRolePermissions()).thenReturn(expectedDto);

        // Act
        ResponseEntity<RolePermissionsDto> response = adminRolePermissionController.getAllRolePermissions();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(getAllRolePermissionsUseCase).getAllRolePermissions();
    }

    @Test
    void getRolePermissionsByRoleId_ShouldReturnRolePermissions() {
        // Arrange
        Long roleId = 1L;
        RolePermissionsDto expectedDto = RolePermissionsDto.builder().build();
        when(getRolePermissionsByRoleIdUseCase.getRolePermissionsByRoleId(roleId)).thenReturn(expectedDto);

        // Act
        ResponseEntity<RolePermissionsDto> response = adminRolePermissionController.getRolePermissionsByRoleId(roleId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(getRolePermissionsByRoleIdUseCase).getRolePermissionsByRoleId(roleId);
    }

    @Test
    void getRolePermissionsByPermissionId_ShouldReturnRolePermissions() {
        // Arrange
        Long permissionId = 1L;
        RolePermissionsDto expectedDto = RolePermissionsDto.builder().build();
        when(getRolePermissionsByPermissionIdUseCase.getRolePermissionsByPermissionId(permissionId)).thenReturn(expectedDto);

        // Act
        ResponseEntity<RolePermissionsDto> response = adminRolePermissionController.getRolePermissionsByPermissionId(permissionId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(getRolePermissionsByPermissionIdUseCase).getRolePermissionsByPermissionId(permissionId);
    }

    @Test
    void createRolePermission_ShouldCreateAndReturnRolePermission() {
        // Arrange
        CreateRolePermissionDto createDto = new CreateRolePermissionDto();
        RolePermissionDto expectedDto = RolePermissionDto.builder().build();
        when(createRolePermissionUseCase.createRolePermission(any())).thenReturn(expectedDto);

        // Act
        ResponseEntity<RolePermissionDto> response = adminRolePermissionController.createRolePermission(createDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(createRolePermissionUseCase).createRolePermission(createDto);
    }

    @Test
    void assignPermissionsToRole_ShouldAssignAndReturnRolePermissions() {
        // Arrange
        AssignPermissionsToRoleDto assignDto = new AssignPermissionsToRoleDto();
        List<RolePermissionDto> expectedDtos = Collections.singletonList(RolePermissionDto.builder().build());
        when(assignPermissionsToRoleUseCase.assignPermissionsToRole(any())).thenReturn(expectedDtos);

        // Act
        ResponseEntity<List<RolePermissionDto>> response = adminRolePermissionController.assignPermissionsToRole(assignDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDtos, response.getBody());
        verify(assignPermissionsToRoleUseCase).assignPermissionsToRole(assignDto);
    }

    @Test
    void deleteRolePermission_ShouldDeleteAndReturnNoContent() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Void> response = adminRolePermissionController.deleteRolePermission(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deleteRolePermissionUseCase).deleteRolePermission(id);
    }

    @Test
    void getPermissionCodesByRoleId_ShouldReturnPermissionCodes() {
        // Arrange
        Long roleId = 1L;
        Set<String> expectedCodes = new HashSet<>(Collections.singletonList("TEST_PERMISSION"));
        when(getPermissionCodesByRoleIdUseCase.getPermissionCodesByRoleId(roleId)).thenReturn(expectedCodes);

        // Act
        ResponseEntity<Set<String>> response = adminRolePermissionController.getPermissionCodesByRoleId(roleId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCodes, response.getBody());
        verify(getPermissionCodesByRoleIdUseCase).getPermissionCodesByRoleId(roleId);
    }

    @Test
    void deleteAllRolePermissionsByRoleId_ShouldDeleteAndReturnNoContent() {
        // Arrange
        Long roleId = 1L;

        // Act
        ResponseEntity<Void> response = adminRolePermissionController.deleteAllRolePermissionsByRoleId(roleId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deleteAllRolePermissionsByRoleIdUseCase).deleteAllRolePermissionsByRoleId(roleId);
    }

    @Test
    void deleteAllRolePermissionsByPermissionId_ShouldDeleteAndReturnNoContent() {
        // Arrange
        Long permissionId = 1L;

        // Act
        ResponseEntity<Void> response = adminRolePermissionController.deleteAllRolePermissionsByPermissionId(permissionId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deleteAllRolePermissionsByPermissionIdUseCase).deleteAllRolePermissionsByPermissionId(permissionId);
    }
}
