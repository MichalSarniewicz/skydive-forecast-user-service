package com.skydiveforecast.infrastructure.adapter.in.web;

import com.skydiveforecast.domain.port.in.*;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRoleControllerTest {

    @Mock
    private GetAllUserRolesUseCase getAllUserRolesUseCase;

    @Mock
    private GetUserRolesUseCase getUserRolesUseCase;

    @Mock
    private AssignRoleToUserUseCase assignRoleToUserUseCase;

    @Mock
    private RemoveRoleFromUserUseCase removeRoleFromUserUseCase;

    @InjectMocks
    private UserRoleController adminUserRoleController;

    @Test
    void getAllUserRoles_ShouldReturnAllUserRoles() {
        // Arrange
        UserRolesDto expectedDto = new UserRolesDto();
        when(getAllUserRolesUseCase.getAllUserRoles()).thenReturn(expectedDto);

        // Act
        ResponseEntity<UserRolesDto> response = adminUserRoleController.getAllUserRoles();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(getAllUserRolesUseCase).getAllUserRoles();
    }

    @Test
    void getUserRoles_ShouldReturnUserRoles() {
        // Arrange
        Long userId = 1L;
        UserRolesDto expectedDto = new UserRolesDto();
        when(getUserRolesUseCase.getUserRoles(userId)).thenReturn(expectedDto);

        // Act
        ResponseEntity<UserRolesDto> response = adminUserRoleController.getUserRoles(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(getUserRolesUseCase).getUserRoles(userId);
    }

    @Test
    void assignRoleToUser_ShouldAssignAndReturnUserRole() {
        // Arrange
        CreateUserRoleDto createDto = new CreateUserRoleDto();
        UserRoleDto expectedDto = new UserRoleDto();
        when(assignRoleToUserUseCase.assignRoleToUser(any())).thenReturn(expectedDto);

        // Act
        ResponseEntity<UserRoleDto> response = adminUserRoleController.assignRoleToUser(createDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(assignRoleToUserUseCase).assignRoleToUser(createDto);
    }

    @Test
    void removeRoleFromUser_ShouldRemoveAndReturnNoContent() {
        // Arrange
        Long userId = 1L;
        Long roleId = 1L;

        // Act
        ResponseEntity<Void> response = adminUserRoleController.removeRoleFromUser(userId, roleId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(removeRoleFromUserUseCase).removeRoleFromUser(userId, roleId);
    }
}
