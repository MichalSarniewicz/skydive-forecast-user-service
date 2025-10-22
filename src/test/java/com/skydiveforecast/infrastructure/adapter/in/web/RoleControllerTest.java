package com.skydiveforecast.infrastructure.adapter.in.web;

import com.skydiveforecast.domain.port.in.AddRoleUseCase;
import com.skydiveforecast.domain.port.in.DeleteRoleUseCase;
import com.skydiveforecast.domain.port.in.GetAllRolesUseCase;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {

    private RoleController adminRoleController;

    @Mock
    private GetAllRolesUseCase getAllRolesUseCase;

    @Mock
    private AddRoleUseCase addRoleUseCase;

    @Mock
    private DeleteRoleUseCase deleteRoleUseCase;

    @BeforeEach
    void setUp() {
        adminRoleController = new RoleController(getAllRolesUseCase, addRoleUseCase, deleteRoleUseCase);
    }
    
    @Test
    void testGetAllRolesReturnsOkWithRoles() {
        // Arrange
        RolesDto rolesDto = new RolesDto();
        when(getAllRolesUseCase.getAllRoles()).thenReturn(rolesDto);

        // Act
        ResponseEntity<RolesDto> response = adminRoleController.getAllRoles();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(rolesDto);
    }


    @Test
    void testGetAllRolesReturnsOkWhenNoRolesExist() {
        // Arrange
        RolesDto emptyRolesDto = new RolesDto();
        when(getAllRolesUseCase.getAllRoles()).thenReturn(emptyRolesDto);

        // Act
        ResponseEntity<RolesDto> response = adminRoleController.getAllRoles();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(emptyRolesDto);
    }
    
    @Test
    void testGetAllRolesHandlesServiceException() {
        // Arrange
        when(getAllRolesUseCase.getAllRoles()).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        try {
            adminRoleController.getAllRoles();
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Service error");
        }
    }
}