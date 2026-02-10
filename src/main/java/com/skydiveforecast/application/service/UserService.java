package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.exception.ValidationException;
import com.skydiveforecast.domain.model.User;
import com.skydiveforecast.domain.port.in.*;
import com.skydiveforecast.domain.port.out.UserRepositoryPort;
import com.skydiveforecast.domain.service.validation.PasswordValidatorService;
import com.skydiveforecast.domain.service.validation.UserValidator;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.*;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.CreateUserMapper;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.UpdateUserMapper;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.UserMapper;
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
public class UserService implements UpdateUserStatusUseCase, FindUserByIdUseCase, ChangePasswordUseCase,
        GetAllUsersUseCase, CreateUserUseCase, UpdateUserUseCase {

    private final UserRepositoryPort userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CreateUserMapper createUserMapper;
    private final PasswordValidatorService passwordValidator;
    private final UserValidator userValidator;

    @Override
    @CacheEvict(value = USERS_CACHE, allEntries = true)
    public UpdateUserResponse updateUser(Long userId, UpdateUserDto updateUserDto) {
        User user = findUserById(userId);
        User updatedUser = User.builder()
                .id(user.id())
                .email(user.email())
                .passwordHash(user.passwordHash())
                .firstName(updateUserDto.getFirstName())
                .lastName(updateUserDto.getLastName())
                .phoneNumber(updateUserDto.getPhoneNumber())
                .isActive(user.isActive())
                .build();
        userRepository.save(updatedUser);
        UserDto updatedUserDto = userMapper.toDto(updatedUser);
        return UpdateUserResponse.success("User updated successfully", updatedUserDto);
    }

    @Override
    @CacheEvict(value = USERS_CACHE, allEntries = true)
    public UserStatusUpdateResponse updateUserStatus(Long userId, UserStatusUpdateDto statusUpdateDto) {
        User user = findUserById(userId);
        User updatedUser = user.withIsActive(statusUpdateDto.getActive());
        userRepository.save(updatedUser);

        String message = statusUpdateDto.getActive()
                ? "User has been successfully activated"
                : "User has been successfully deactivated";

        return UserStatusUpdateResponse.success(message);
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + userId + " not found"));
    }

    @Override
    @CacheEvict(value = USERS_CACHE, allEntries = true)
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        User currentUser = findUserById(userId);

        if (!passwordEncoder.matches(currentPassword, currentUser.passwordHash())) {
            throw new ValidationException(Map.of("currentPassword", "Current password is incorrect"));
        }

        Map<String, String> passwordErrors = passwordValidator.validate(newPassword);

        if (passwordEncoder.matches(newPassword, currentUser.passwordHash())) {
            passwordErrors.put("newPassword", "New password must be different from the current password");
        }

        if (!passwordErrors.isEmpty()) {
            throw new ValidationException(passwordErrors);
        }

        User updatedUser = currentUser.withPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(updatedUser);
    }

    @Override
    @Cacheable(value = USERS_CACHE, key = "'all'")
    public UsersDto getAllUsers() {
        List<User> entities = userRepository.findAllWithRoles();
        return new UsersDto(userMapper.toDtoList(entities));
    }

    @Override
    @CacheEvict(value = USERS_CACHE, allEntries = true)
    public UserDto createUser(CreateUserDto createUserDto) {
        Map<String, String> errors = new HashMap<>();

        errors.putAll(userValidator.validateEmail(createUserDto.getEmail(), true));
        errors.putAll(userValidator.validateName(createUserDto.getFirstName(), createUserDto.getLastName()));
        errors.putAll(userValidator.validatePhoneNumber(createUserDto.getPhoneNumber()));
        errors.putAll(passwordValidator.validate(createUserDto.getPassword()));

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        User user = createUserMapper.toDomain(createUserDto);
        User userWithPassword = user.withPasswordHash(passwordEncoder.encode(createUserDto.getPassword()));
        return userMapper.toDto(userRepository.save(userWithPassword));
    }
}