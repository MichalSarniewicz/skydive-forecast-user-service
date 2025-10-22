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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UpdateUserStatusUseCase updateUserStatusUseCase;

    @Mock
    private GetAllUsersUseCase getAllUsersUseCase;

    @Mock
    private CreateUserUseCase createUserUseCase;

    @Mock
    private UpdateUserUseCase updateUserUseCase;

    @InjectMocks
    private UserController adminUserController;

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        // Arrange
        UsersDto expectedDto = new UsersDto();
        when(getAllUsersUseCase.getAllUsers()).thenReturn(expectedDto);

        // Act
        ResponseEntity<UsersDto> response = adminUserController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(getAllUsersUseCase).getAllUsers();
    }

    @Test
    void createUser_ShouldCreateAndReturnUser() {
        // Arrange
        CreateUserDto createDto = new CreateUserDto();
        UserDto expectedDto = new UserDto();
        when(createUserUseCase.createUser(any())).thenReturn(expectedDto);

        // Act
        ResponseEntity<UserDto> response = adminUserController.createUser(createDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(createUserUseCase).createUser(createDto);
    }

    @Test
    void updateUserStatus_ShouldUpdateAndReturnStatus() {
        // Arrange
        Long userId = 1L;
        UserStatusUpdateDto statusUpdateDto = new UserStatusUpdateDto();
        UserStatusUpdateResponse expectedResponse = new UserStatusUpdateResponse();
        when(updateUserStatusUseCase.updateUserStatus(anyLong(), any())).thenReturn(expectedResponse);

        // Act
        ResponseEntity<UserStatusUpdateResponse> response = adminUserController.updateUserStatus(userId, statusUpdateDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(updateUserStatusUseCase).updateUserStatus(userId, statusUpdateDto);
    }

    @Test
    void updateUser_ShouldUpdateAndReturnUser() {
        // Arrange
        Long userId = 1L;
        UpdateUserDto updateDto = new UpdateUserDto();
        UpdateUserResponse expectedResponse = new UpdateUserResponse();
        when(updateUserUseCase.updateUser(anyLong(), any())).thenReturn(expectedResponse);

        // Act
        ResponseEntity<UpdateUserResponse> response = adminUserController.updateUser(userId, updateDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(updateUserUseCase).updateUser(userId, updateDto);
    }
}
