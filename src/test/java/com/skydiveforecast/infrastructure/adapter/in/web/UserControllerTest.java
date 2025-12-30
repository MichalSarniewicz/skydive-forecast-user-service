package com.skydiveforecast.infrastructure.adapter.in.web;

import com.skydiveforecast.domain.port.in.*;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.*;
import com.skydiveforecast.infrastructure.security.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private GetAllUsersUseCase getAllUsersUseCase;
    @Mock
    private CreateUserUseCase createUserUseCase;
    @Mock
    private UpdateUserUseCase updateUserUseCase;
    @Mock
    private UpdateUserStatusUseCase updateUserStatusUseCase;
    @Mock
    private ChangePasswordUseCase changePasswordUseCase;
    @Mock
    private AuthService authService;

    @InjectMocks
    private UserController userController;

    @Test
    @DisplayName("Should get all users successfully")
    void getAllUsers_Success() {
        // Arrange
        UsersDto dto = new UsersDto(List.of(new UserDto()));
        when(getAllUsersUseCase.getAllUsers()).thenReturn(dto);

        // Act
        ResponseEntity<UsersDto> response = userController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(getAllUsersUseCase).getAllUsers();
    }

    @Test
    @DisplayName("Should create user successfully")
    void createUser_Success() {
        // Arrange
        CreateUserDto dto = new CreateUserDto();
        UserDto result = new UserDto();
        when(createUserUseCase.createUser(any())).thenReturn(result);

        // Act
        ResponseEntity<UserDto> response = userController.createUser(dto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(createUserUseCase).createUser(dto);
    }

    @Test
    @DisplayName("Should update user successfully")
    void updateUser_Success() {
        // Arrange
        Long id = 1L;
        UpdateUserDto dto = new UpdateUserDto();
        UpdateUserResponse result = UpdateUserResponse.success("Updated", new UserDto());
        when(updateUserUseCase.updateUser(any(), any())).thenReturn(result);

        // Act
        ResponseEntity<UpdateUserResponse> response = userController.updateUser(id, dto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(updateUserUseCase).updateUser(id, dto);
    }

    @Test
    @DisplayName("Should update user status successfully")
    void updateUserStatus_Success() {
        // Arrange
        Long id = 1L;
        UserStatusUpdateDto dto = new UserStatusUpdateDto();
        UserStatusUpdateResponse result = UserStatusUpdateResponse.success("Updated");
        when(updateUserStatusUseCase.updateUserStatus(any(), any())).thenReturn(result);

        // Act
        ResponseEntity<UserStatusUpdateResponse> response = userController.updateUserStatus(id, dto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(updateUserStatusUseCase).updateUserStatus(id, dto);
    }

    @Test
    @DisplayName("Should change password successfully")
    void changePassword_Success() {
        // Arrange
        Long userId = 1L;
        PasswordChangeDto dto = new PasswordChangeDto();
        dto.setCurrentPassword("oldPass");
        dto.setNewPassword("newPass");
        when(authService.getCurrentUserId()).thenReturn(userId);

        // Act
        ResponseEntity<Void> response = userController.changePassword(dto);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(authService).getCurrentUserId();
        verify(changePasswordUseCase).changePassword(userId, "oldPass", "newPass");
    }
}
