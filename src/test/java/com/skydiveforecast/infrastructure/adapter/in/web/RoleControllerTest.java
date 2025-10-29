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
class RoleControllerTest {

    @Mock
    private GetAllRolesUseCase getAllRolesUseCase;
    @Mock
    private AddRoleUseCase addRoleUseCase;
    @Mock
    private DeleteRoleUseCase deleteRoleUseCase;

    @InjectMocks
    private RoleController roleController;

    @Test
    @DisplayName("Should get all roles successfully")
    void getAllRoles_Success() {
        // Arrange
        RolesDto dto = new RolesDto(List.of(new RoleDto()));
        when(getAllRolesUseCase.getAllRoles()).thenReturn(dto);

        // Act
        ResponseEntity<RolesDto> response = roleController.getAllRoles();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(getAllRolesUseCase).getAllRoles();
    }

    @Test
    @DisplayName("Should add role successfully")
    void addRole_Success() {
        // Arrange
        String roleName = "MODERATOR";
        RoleDto result = new RoleDto();
        when(addRoleUseCase.addRole(any())).thenReturn(result);

        // Act
        ResponseEntity<RoleDto> response = roleController.addRole(roleName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(addRoleUseCase).addRole(roleName);
    }

    @Test
    @DisplayName("Should delete role successfully")
    void deleteRole_Success() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<String> response = roleController.deleteRole(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deleteRoleUseCase).deleteRole(id);
    }

}
