package com.skydiveforecast.application;

import com.skydiveforecast.application.service.UserService;
import com.skydiveforecast.domain.exception.ValidationException;
import com.skydiveforecast.domain.model.User;
import com.skydiveforecast.domain.port.out.UserRepositoryPort;
import com.skydiveforecast.domain.service.validation.PasswordValidatorService;
import com.skydiveforecast.domain.service.validation.UserValidator;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.*;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.CreateUserMapper;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CreateUserMapper createUserMapper;

    @Mock
    private PasswordValidatorService passwordValidator;

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should get all users successfully")
    void getAllUsers_Success() {
        // Arrange
        User user = User.builder().id(1L).email("test@test.com").firstName("John").lastName("Doe").isActive(true).build();
        when(userRepository.findAllWithRoles()).thenReturn(List.of(user));
        when(userMapper.toDtoList(any())).thenReturn(List.of(new UserDto()));

        // Act
        UsersDto result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getUsers().size());
        verify(userRepository).findAllWithRoles();
    }

    @Test
    @DisplayName("Should create user successfully")
    void createUser_Success() {
        // Arrange
        CreateUserDto dto = new CreateUserDto();
        dto.setEmail("user@example.com");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPassword("Password123!");
        
        User user = User.builder().email("user@example.com").firstName("John").lastName("Doe").isActive(true).build();
        User savedUser = user.withId(1L).withPasswordHash("encoded");
        
        when(userValidator.validateEmail(any(), eq(true))).thenReturn(new HashMap<>());
        when(userValidator.validateName(any(), any())).thenReturn(new HashMap<>());
        when(userValidator.validatePhoneNumber(any())).thenReturn(new HashMap<>());
        when(passwordValidator.validate(any())).thenReturn(new HashMap<>());
        when(createUserMapper.toDomain(any())).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("encoded");
        when(userRepository.save(any())).thenReturn(savedUser);
        when(userMapper.toDto(any())).thenReturn(new UserDto());

        // Act
        UserDto result = userService.createUser(dto);

        // Assert
        assertNotNull(result);
        verify(userRepository).save(any());
    }

    @Test
    @DisplayName("Should throw ValidationException when email already exists")
    void createUser_EmailExists() {
        // Arrange
        CreateUserDto dto = new CreateUserDto();
        dto.setEmail("existing@example.com");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPassword("Password123!");
        
        Map<String, String> emailErrors = Map.of("email", "Email already in use");
        when(userValidator.validateEmail(any(), eq(true))).thenReturn(emailErrors);
        when(userValidator.validateName(any(), any())).thenReturn(new HashMap<>());
        when(userValidator.validatePhoneNumber(any())).thenReturn(new HashMap<>());
        when(passwordValidator.validate(any())).thenReturn(new HashMap<>());

        // Act & Assert
        assertThrows(ValidationException.class, () -> userService.createUser(dto));
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update user successfully")
    void updateUser_Success() {
        // Arrange
        Long userId = 1L;
        UpdateUserDto dto = new UpdateUserDto();
        dto.setFirstName("Jane");
        dto.setLastName("Smith");
        User user = User.builder().id(userId).email("test@test.com").firstName("John").lastName("Doe")
                .passwordHash("hash").isActive(true).build();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.toDto(any())).thenReturn(new UserDto());

        // Act
        UpdateUserResponse result = userService.updateUser(userId, dto);

        // Assert
        assertNotNull(result);
        assertTrue(result.isSuccess());
        verify(userRepository).save(any());
    }

    @Test
    @DisplayName("Should update user status successfully")
    void updateUserStatus_Success() {
        // Arrange
        Long userId = 1L;
        UserStatusUpdateDto dto = new UserStatusUpdateDto();
        dto.setActive(true);
        User user = User.builder().id(userId).email("test@test.com").firstName("John").lastName("Doe")
                .passwordHash("hash").isActive(false).build();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        // Act
        UserStatusUpdateResponse result = userService.updateUserStatus(userId, dto);

        // Assert
        assertNotNull(result);
        assertTrue(result.isSuccess());
        verify(userRepository).save(any());
    }

    @Test
    @DisplayName("Should change password successfully")
    void changePassword_Success() {
        // Arrange
        Long userId = 1L;
        String currentPassword = "OldPass123!";
        String newPassword = "NewPass123!";
        User user = User.builder().id(userId).email("test@test.com").firstName("John").lastName("Doe")
                .passwordHash("encodedOld").isActive(true).build();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(currentPassword, "encodedOld")).thenReturn(true);
        when(passwordEncoder.matches(newPassword, "encodedOld")).thenReturn(false);
        when(passwordValidator.validate(newPassword)).thenReturn(new HashMap<>());
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNew");
        when(userRepository.save(any())).thenReturn(user);

        // Act
        userService.changePassword(userId, currentPassword, newPassword);

        // Assert
        verify(userRepository).save(any());
    }

    @Test
    @DisplayName("Should throw ValidationException when current password is incorrect")
    void changePassword_IncorrectCurrentPassword() {
        // Arrange
        Long userId = 1L;
        User user = User.builder().id(userId).email("test@test.com").firstName("John").lastName("Doe")
                .passwordHash("encodedOld").isActive(true).build();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        // Act & Assert
        assertThrows(ValidationException.class, 
            () -> userService.changePassword(userId, "wrong", "NewPass123!"));
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when user not found")
    void findUserById_NotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userService.findUserById(1L));
    }

    @Test
    @DisplayName("Should throw ValidationException when new password same as current")
    void changePassword_SamePassword() {
        // Arrange
        Long userId = 1L;
        String password = "SamePass123!";
        User user = User.builder().id(userId).email("test@test.com").firstName("John").lastName("Doe")
                .passwordHash("encoded").isActive(true).build();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, "encoded")).thenReturn(true);
        when(passwordValidator.validate(password)).thenReturn(new HashMap<>());

        // Act & Assert
        assertThrows(ValidationException.class, 
            () -> userService.changePassword(userId, password, password));
    }

    @Test
    @DisplayName("Should throw ValidationException when first name is blank")
    void createUser_BlankFirstName() {
        // Arrange
        CreateUserDto dto = new CreateUserDto();
        dto.setEmail("user@example.com");
        dto.setFirstName("");
        dto.setLastName("Doe");
        dto.setPassword("Password123!");

        Map<String, String> nameErrors = Map.of("firstName", "First name is required");
        when(userValidator.validateEmail(any(), eq(true))).thenReturn(new HashMap<>());
        when(userValidator.validateName(any(), any())).thenReturn(nameErrors);
        when(userValidator.validatePhoneNumber(any())).thenReturn(new HashMap<>());
        when(passwordValidator.validate(any())).thenReturn(new HashMap<>());

        assertThrows(ValidationException.class, () -> userService.createUser(dto));
    }

    @Test
    @DisplayName("Should throw ValidationException when last name is blank")
    void createUser_BlankLastName() {
        // Arrange
        CreateUserDto dto = new CreateUserDto();
        dto.setEmail("user@example.com");
        dto.setFirstName("John");
        dto.setLastName("");
        dto.setPassword("Password123!");

        // Act & Assert
        Map<String, String> nameErrors = Map.of("lastName", "Last name is required");
        when(userValidator.validateEmail(any(), eq(true))).thenReturn(new HashMap<>());
        when(userValidator.validateName(any(), any())).thenReturn(nameErrors);
        when(userValidator.validatePhoneNumber(any())).thenReturn(new HashMap<>());
        when(passwordValidator.validate(any())).thenReturn(new HashMap<>());

        assertThrows(ValidationException.class, () -> userService.createUser(dto));
    }

    @Test
    @DisplayName("Should throw ValidationException when phone number is invalid")
    void createUser_InvalidPhoneNumber() {
        // Arrange
        CreateUserDto dto = new CreateUserDto();
        dto.setEmail("user@example.com");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPhoneNumber("invalid");
        dto.setPassword("Password123!");
        
        Map<String, String> phoneErrors = Map.of("phoneNumber", "Phone number must be valid");
        when(userValidator.validateEmail(any(), eq(true))).thenReturn(new HashMap<>());
        when(userValidator.validateName(any(), any())).thenReturn(new HashMap<>());
        when(userValidator.validatePhoneNumber(any())).thenReturn(phoneErrors);
        when(passwordValidator.validate(any())).thenReturn(new HashMap<>());

        // Act & Assert
        assertThrows(ValidationException.class, () -> userService.createUser(dto));
    }

    @Test
    @DisplayName("Should throw ValidationException when password is invalid")
    void createUser_InvalidPassword() {
        // Arrange
        CreateUserDto dto = new CreateUserDto();
        dto.setEmail("user@example.com");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPassword("weak");
        
        Map<String, String> passwordErrors = Map.of("password", "Password too weak");
        when(userValidator.validateEmail(any(), eq(true))).thenReturn(new HashMap<>());
        when(userValidator.validateName(any(), any())).thenReturn(new HashMap<>());
        when(userValidator.validatePhoneNumber(any())).thenReturn(new HashMap<>());
        when(passwordValidator.validate(any())).thenReturn(passwordErrors);

        // Act & Assert
        assertThrows(ValidationException.class, () -> userService.createUser(dto));
    }

    @Test
    void shouldUpdatePasswordSuccessfullyWhenCurrentPasswordIsCorrect() {
        Long userId = 1L;
        String currentPassword = "OldPassword1!";
        String newPassword = "NewPassword1!";
        String encodedOldPassword = "encodedOldPassword";
        String encodedNewPassword = "encodedNewPassword";

        User user = User.builder().id(userId).email("test@test.com").firstName("John").lastName("Doe")
                .passwordHash(encodedOldPassword).isActive(true).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(currentPassword, encodedOldPassword)).thenReturn(true);
        when(passwordEncoder.matches(newPassword, encodedOldPassword)).thenReturn(false);
        when(passwordValidator.validate(newPassword)).thenReturn(new HashMap<>());
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedNewPassword);
        when(userRepository.save(any())).thenReturn(user);

        userService.changePassword(userId, currentPassword, newPassword);

        Mockito.verify(userRepository).save(any(User.class));
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

        Mockito.verify(userRepository, Mockito.never()).save(any(User.class));
    }
}
