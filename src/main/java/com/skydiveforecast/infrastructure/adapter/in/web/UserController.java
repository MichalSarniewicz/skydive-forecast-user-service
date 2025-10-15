package com.skydiveforecast.infrastructure.adapter.in.web;

import com.skydiveforecast.domain.port.in.CreateUserUseCase;
import com.skydiveforecast.domain.port.in.GetAllUsersUseCase;
import com.skydiveforecast.domain.port.in.UpdateUserStatusUseCase;
import com.skydiveforecast.domain.port.in.UpdateUserUseCase;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for managing users as an admin.")
public class UserController {

    private final UpdateUserStatusUseCase updateUserStatusUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    @GetMapping
    @Operation(summary = "Get All Users", description = "Retrieve a list of all users.")
    public ResponseEntity<UsersDto> getAllUsers() {
        UsersDto users = getAllUsersUseCase.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    @Operation(summary = "Create User", description = "Create a new user with the specified details.")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.ok(createUserUseCase.createUser(createUserDto));
    }

    @PatchMapping("/{user-id}/status")
    @Operation(summary = "Activate/Deactivate User", description = "Activate or deactivate a user by setting their status.")
    public ResponseEntity<UserStatusUpdateResponse> updateUserStatus(
            @PathVariable("user-id") Long userId,
            @Valid @RequestBody UserStatusUpdateDto statusUpdateDto) {
        return ResponseEntity.ok(updateUserStatusUseCase.updateUserStatus(userId, statusUpdateDto));
    }

    @PutMapping("/{user-id}")
    @Operation(summary = "Update User", description = "Update an existing user's information.")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable("user-id") Long userId,
            @Valid @RequestBody UpdateUserDto updateUserDto) {
        return ResponseEntity.ok(updateUserUseCase.updateUser(userId, updateUserDto));
    }
}