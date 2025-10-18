package com.skydiveforecast.infrastructure.adapter.in.web;

import com.skydiveforecast.domain.port.in.*;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.*;
import com.skydiveforecast.infrastructure.security.AuthService;
import com.skydiveforecast.infrastructure.security.PermissionSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for managing users.")
public class UserController {

    private final UpdateUserStatusUseCase updateUserStatusUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;

    private final AuthService authService;

    @GetMapping
    @PermissionSecurity(permission = "USER_VIEW")
    @Operation(summary = "Get All Users", description = "Retrieve a list of all users.")
    public ResponseEntity<UsersDto> getAllUsers() {
        UsersDto users = getAllUsersUseCase.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    @PermissionSecurity(permission = "USER_CREATE")
    @Operation(summary = "Create User", description = "Create a new user with the specified details.")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.ok(createUserUseCase.createUser(createUserDto));
    }

    @PatchMapping("/{user-id}/status")
    @PermissionSecurity(permission = "USER_STATUS_UPDATE")
    @Operation(summary = "Activate/Deactivate User", description = "Activate or deactivate a user by setting their status.")
    public ResponseEntity<UserStatusUpdateResponse> updateUserStatus(
            @PathVariable("user-id") Long userId,
            @Valid @RequestBody UserStatusUpdateDto statusUpdateDto) {
        return ResponseEntity.ok(updateUserStatusUseCase.updateUserStatus(userId, statusUpdateDto));
    }

    @PutMapping("/{user-id}")
    @PermissionSecurity(permission = "USER_EDIT")
    @Operation(summary = "Update User", description = "Update an existing user's information.")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable("user-id") Long userId,
            @Valid @RequestBody UpdateUserDto updateUserDto) {
        return ResponseEntity.ok(updateUserUseCase.updateUser(userId, updateUserDto));
    }

    @PermissionSecurity(permission = "USER_PASSWORD_CHANGE")
    @Operation(
            summary = "Change user password",
            description = "Allows the authenticated user to change their password")
    @PatchMapping("/me/password")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid PasswordChangeDto request) {
        Long userId = authService.getCurrentUserId();
        changePasswordUseCase.changePassword(userId, request.getCurrentPassword(),
                request.getNewPassword());
        return ResponseEntity.noContent().build();
    }
}