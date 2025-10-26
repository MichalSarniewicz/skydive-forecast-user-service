package com.skydiveforecast.application;

import com.skydiveforecast.domain.exception.ValidationException;
import com.skydiveforecast.domain.model.UserEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.CreateUserMapper;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.UpdateUserMapper;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.UserMapper;
import com.skydiveforecast.domain.port.in.*;
import com.skydiveforecast.domain.service.validation.PasswordValidatorService;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.*;
import com.skydiveforecast.infrastructure.adapter.out.persistance.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.skydiveforecast.infrastructure.config.CacheConfig.USERS_CACHE;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UpdateUserStatusUseCase, FindUserByIdUseCase, ChangePasswordUseCase,
        GetAllUsersUseCase, CreateUserUseCase, UpdateUserUseCase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UpdateUserMapper updateUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final CreateUserMapper createUserMapper;
    private final PasswordValidatorService passwordValidator;

    @Override
    @CacheEvict(value = USERS_CACHE, allEntries = true)
    public UpdateUserResponse updateUser(Long userId, UpdateUserDto updateUserDto) {
        UserEntity user = findUserById(userId);
        updateUserMapper.updateEntityFromDto(updateUserDto, user);
        UserDto updatedUserDto = userMapper.toDto(user);
        return UpdateUserResponse.success("User updated successfully", updatedUserDto);
    }

    @Override
    @CacheEvict(value = USERS_CACHE, allEntries = true)
    public UserStatusUpdateResponse updateUserStatus(Long userId, UserStatusUpdateDto statusUpdateDto) {
        UserEntity user = findUserById(userId);

        user.setActive(statusUpdateDto.getActive());
        userRepository.save(user);

        String message = statusUpdateDto.getActive()
                ? "User has been successfully activated"
                : "User has been successfully deactivated";

        return UserStatusUpdateResponse.success(message);
    }

    @Override
    public UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + userId + " not found"));
    }

    @Override
    @CacheEvict(value = USERS_CACHE, allEntries = true)
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        UserEntity currentUser = findUserById(userId);

        // Validate current password
        if (!passwordEncoder.matches(currentPassword, currentUser.getPasswordHash())) {
            throw new ValidationException(Map.of("currentPassword", "Current password is incorrect"));
        }

        // Validate new password
        Map<String, String> passwordErrors = passwordValidator.validate(newPassword);

        // Check if the new password is the same as a current password
        if (passwordEncoder.matches(newPassword, currentUser.getPasswordHash())) {
            passwordErrors.put("newPassword", "New password must be different from the current password");
        }

        // If there are any validation errors, throw ValidationException
        if (!passwordErrors.isEmpty()) {
            throw new ValidationException(passwordErrors);
        }

        // If validation passes, update the password
        currentUser.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(currentUser);
    }

    @Override
    @Cacheable(value = USERS_CACHE, key = "'all'")
    public UsersDto getAllUsers() {
        List<UserEntity> entities = userRepository.findAll();
        return new UsersDto(userMapper.toDtoList(entities));
    }

    @Override
    @CacheEvict(value = USERS_CACHE, allEntries = true)
    public UserDto createUser(CreateUserDto createUserDto) {
        Map<String, String> errors = new HashMap<>();

        // Email validation
        if (createUserDto.getEmail() == null || createUserDto.getEmail().isBlank()) {
            errors.put("email", "Email is required");
        } else if (userRepository.existsByEmail(createUserDto.getEmail())) {
            errors.put("email", "Email already in use");
        }

        // Name validation
        if (createUserDto.getFirstName() == null || createUserDto.getFirstName().isBlank()) {
            errors.put("firstName", "First name is required");
        } else if (createUserDto.getFirstName().length() > 50) {
            errors.put("firstName", "First name cannot exceed 50 characters");
        }

        if (createUserDto.getLastName() == null || createUserDto.getLastName().isBlank()) {
            errors.put("lastName", "Last name is required");
        } else if (createUserDto.getLastName().length() > 50) {
            errors.put("lastName", "Last name cannot exceed 50 characters");
        }

        // Phone number validation
        if (createUserDto.getPhoneNumber() != null && !createUserDto.getPhoneNumber().matches("^\\+?[0-9]{10,15}$")) {
            errors.put("phoneNumber", "Phone number must be valid");
        }

        // Password validation
        Map<String, String> passwordErrors = passwordValidator.validate(createUserDto.getPassword());
        errors.putAll(passwordErrors);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        // If validation passes, create the user
        UserEntity entity = createUserMapper.toEntity(createUserDto);
        entity.setPasswordHash(passwordEncoder.encode(createUserDto.getPassword()));

        UserEntity savedEntity = userRepository.save(entity);
        return userMapper.toDto(savedEntity);
    }
}