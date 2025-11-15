package com.skydiveforecast.domain.service;

import com.skydiveforecast.application.service.UserService;
import com.skydiveforecast.domain.model.*;
import com.skydiveforecast.domain.port.out.UserRepositoryPort;
import com.skydiveforecast.domain.service.validation.PasswordValidatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepositoryPort userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PasswordValidatorService passwordValidator;

    @Test
    void shouldUpdatePasswordSuccessfullyWhenCurrentPasswordIsCorrect() {
        Long userId = 1L;
        String currentPassword = "OldPassword1!";
        String newPassword = "NewPassword1!";
        String encodedOldPassword = "encodedOldPassword";
        String encodedNewPassword = "encodedNewPassword";

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setPasswordHash(encodedOldPassword);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(currentPassword, encodedOldPassword)).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedNewPassword);

        userService.changePassword(userId, currentPassword, newPassword);

        Mockito.verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        Long userId = 1L;
        String currentPassword = "OldPassword1!";
        String newPassword = "NewPassword1!";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        try {
            userService.changePassword(userId, currentPassword, newPassword);
        } catch (RuntimeException e) {
            assertEquals("User with id: 1 not found", e.getMessage());
        }

        Mockito.verify(userRepository, Mockito.never()).save(any(UserEntity.class));
    }
}