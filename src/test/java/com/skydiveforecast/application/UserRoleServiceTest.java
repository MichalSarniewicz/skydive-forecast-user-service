package com.skydiveforecast.application;

import com.skydiveforecast.application.service.UserRoleService;
import com.skydiveforecast.domain.model.Role;
import com.skydiveforecast.domain.port.out.RoleRepositoryPort;
import com.skydiveforecast.domain.port.out.UserRepositoryPort;
import com.skydiveforecast.domain.port.out.UserRoleRepositoryPort;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreateUserRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserRolesDto;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.UserRoleMapper;
import com.skydiveforecast.infrastructure.persistence.entity.UserEntity;
import com.skydiveforecast.infrastructure.persistence.entity.UserRoleEntity;
import com.skydiveforecast.infrastructure.persistence.mapper.RoleEntityMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceTest {

    @Mock
    private UserRoleRepositoryPort userRoleRepository;

    @Mock
    private UserRoleMapper userRoleMapper;

    @Mock
    private UserRepositoryPort userRepository;

    @Mock
    private RoleRepositoryPort roleRepository;

    @Mock
    private RoleEntityMapper roleEntityMapper;

    @InjectMocks
    private UserRoleService userRoleService;

    @Test
    @DisplayName("Should get all user roles successfully")
    void getAllUserRoles_WhenUserRolesExist_ReturnsAllUserRoles() {
        // Arrange
        UserRoleEntity entity = new UserRoleEntity();
        UserRoleDto dto = new UserRoleDto();

        when(userRoleRepository.findAll()).thenReturn(List.of(entity));
        when(userRoleMapper.toDtoList(any())).thenReturn(List.of(dto));

        // Act
        UserRolesDto result = userRoleService.getAllUserRoles();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getUserRoles().size());
        verify(userRoleRepository).findAll();
    }

    @Test
    @DisplayName("Should get user roles by user id successfully")
    void getUserRoles_WhenUserHasRoles_ReturnsUserRoles() {
        // Arrange
        UserRoleEntity entity = new UserRoleEntity();
        UserRoleDto dto = new UserRoleDto();

        when(userRoleRepository.findByUserId(1L)).thenReturn(List.of(entity));
        when(userRoleMapper.toDtoList(any())).thenReturn(List.of(dto));

        // Act
        UserRolesDto result = userRoleService.getUserRoles(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getUserRoles().size());
    }

    @Test
    @DisplayName("Should assign role to user successfully")
    void assignRoleToUser_WhenValidRequest_AssignsRole() {
        // Arrange
        CreateUserRoleDto dto = new CreateUserRoleDto();
        dto.setUserId(1L);
        dto.setRoleId(2L);

        UserEntity user = new UserEntity();
        Role role = Role.builder().id(2L).name("USER").build();
        UserRoleEntity savedEntity = new UserRoleEntity();
        UserRoleDto resultDto = new UserRoleDto();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(2L)).thenReturn(Optional.of(role));
        when(userRoleRepository.findByUserId(1L)).thenReturn(List.of());
        when(userRoleRepository.save(any())).thenReturn(savedEntity);
        when(userRoleMapper.toDto(savedEntity)).thenReturn(resultDto);

        // Act
        UserRoleDto result = userRoleService.assignRoleToUser(dto);

        // Assert
        assertNotNull(result);
        verify(userRoleRepository).save(any());
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void assignRoleToUser_WhenUserNotFound_ThrowsException() {
        // Arrange
        CreateUserRoleDto dto = new CreateUserRoleDto();
        dto.setUserId(1L);
        dto.setRoleId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userRoleService.assignRoleToUser(dto));
    }

    @Test
    @DisplayName("Should throw exception when role not found")
    void assignRoleToUser_WhenRoleNotFound_ThrowsException() {
        // Arrange
        CreateUserRoleDto dto = new CreateUserRoleDto();
        dto.setUserId(1L);
        dto.setRoleId(2L);

        UserEntity user = new UserEntity();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userRoleService.assignRoleToUser(dto));
    }

    @Test
    @DisplayName("Should remove role from user successfully")
    void removeRoleFromUser_WhenRoleAssigned_RemovesRole() {
        // Arrange
        UserRoleEntity entity = new UserRoleEntity();
        entity.setRole(new com.skydiveforecast.infrastructure.persistence.entity.RoleEntity());
        entity.getRole().setId(2L);

        when(userRoleRepository.findByUserId(1L)).thenReturn(List.of(entity));

        // Act
        userRoleService.removeRoleFromUser(1L, 2L);

        // Assert
        verify(userRoleRepository).deleteByUserIdAndRoleId(1L, 2L);
    }

    @Test
    @DisplayName("Should throw exception when removing non-assigned role")
    void removeRoleFromUser_WhenRoleNotAssigned_ThrowsException() {
        // Arrange
        when(userRoleRepository.findByUserId(1L)).thenReturn(List.of());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userRoleService.removeRoleFromUser(1L, 2L));
    }
}
