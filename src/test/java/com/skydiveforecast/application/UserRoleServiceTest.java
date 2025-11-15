package com.skydiveforecast.application;

import com.skydiveforecast.application.service.UserRoleService;
import com.skydiveforecast.domain.model.RoleEntity;
import com.skydiveforecast.domain.model.UserEntity;
import com.skydiveforecast.domain.model.UserRoleEntity;
import com.skydiveforecast.domain.port.out.RoleRepositoryPort;
import com.skydiveforecast.domain.port.out.UserRepositoryPort;
import com.skydiveforecast.domain.port.out.UserRoleRepositoryPort;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreateUserRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserRolesDto;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.UserRoleMapper;
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

    @InjectMocks
    private UserRoleService userRoleService;

    @Test
    @DisplayName("Should get all user roles successfully")
    void getAllUserRoles_Success() {
        // Arrange
        UserRoleEntity entity = createUserRoleEntity();
        when(userRoleRepository.findAll()).thenReturn(List.of(entity));
        when(userRoleMapper.toDtoList(any())).thenReturn(List.of(new UserRoleDto()));

        // Act
        UserRolesDto result = userRoleService.getAllUserRoles();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getUserRoles().size());
        verify(userRoleRepository).findAll();
    }

    @Test
    @DisplayName("Should get user roles by user ID")
    void getUserRoles_Success() {
        // Arrange
        Long userId = 1L;
        UserRoleEntity entity = createUserRoleEntity();
        when(userRoleRepository.findByUserId(userId)).thenReturn(List.of(entity));
        when(userRoleMapper.toDtoList(any())).thenReturn(List.of(new UserRoleDto()));

        // Act
        UserRolesDto result = userRoleService.getUserRoles(userId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getUserRoles().size());
        verify(userRoleRepository).findByUserId(userId);
    }

    @Test
    @DisplayName("Should assign role to user successfully")
    void assignRoleToUser_Success() {
        // Arrange
        CreateUserRoleDto dto = new CreateUserRoleDto();
        dto.setUserId(1L);
        dto.setRoleId(1L);
        
        UserEntity user = new UserEntity();
        user.setId(1L);
        RoleEntity role = new RoleEntity();
        role.setId(1L);
        UserRoleEntity saved = createUserRoleEntity();
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(userRoleRepository.findByUserId(1L)).thenReturn(List.of());
        when(userRoleRepository.save(any())).thenReturn(saved);
        when(userRoleMapper.toDto(any())).thenReturn(new UserRoleDto());

        // Act
        UserRoleDto result = userRoleService.assignRoleToUser(dto);

        // Assert
        assertNotNull(result);
        verify(userRoleRepository).save(any());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when user not found")
    void assignRoleToUser_UserNotFound() {
        // Arrange
        CreateUserRoleDto dto = new CreateUserRoleDto();
        dto.setUserId(999L);
        dto.setRoleId(1L);
        
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, 
            () -> userRoleService.assignRoleToUser(dto));
        verify(userRoleRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when role not found")
    void assignRoleToUser_RoleNotFound() {
        // Arrange
        CreateUserRoleDto dto = new CreateUserRoleDto();
        dto.setUserId(1L);
        dto.setRoleId(999L);
        
        UserEntity user = new UserEntity();
        user.setId(1L);
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, 
            () -> userRoleService.assignRoleToUser(dto));
        verify(userRoleRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when role already assigned")
    void assignRoleToUser_RoleAlreadyAssigned() {
        // Arrange
        CreateUserRoleDto dto = new CreateUserRoleDto();
        dto.setUserId(1L);
        dto.setRoleId(1L);
        
        UserEntity user = new UserEntity();
        user.setId(1L);
        RoleEntity role = new RoleEntity();
        role.setId(1L);
        UserRoleEntity existing = createUserRoleEntity();
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(userRoleRepository.findByUserId(1L)).thenReturn(List.of(existing));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> userRoleService.assignRoleToUser(dto));
        verify(userRoleRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should remove role from user successfully")
    void removeRoleFromUser_Success() {
        // Arrange
        Long userId = 1L;
        Long roleId = 1L;
        UserRoleEntity entity = createUserRoleEntity();
        
        when(userRoleRepository.findByUserId(userId)).thenReturn(List.of(entity));

        // Act
        userRoleService.removeRoleFromUser(userId, roleId);

        // Assert
        verify(userRoleRepository).deleteByUserIdAndRoleId(userId, roleId);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when role not assigned to user")
    void removeRoleFromUser_RoleNotAssigned() {
        // Arrange
        Long userId = 1L;
        Long roleId = 999L;
        
        when(userRoleRepository.findByUserId(userId)).thenReturn(List.of());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> userRoleService.removeRoleFromUser(userId, roleId));
        verify(userRoleRepository, never()).deleteByUserIdAndRoleId(any(), any());
    }

    private UserRoleEntity createUserRoleEntity() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        
        RoleEntity role = new RoleEntity();
        role.setId(1L);
        role.setName("USER");
        
        return UserRoleEntity.builder()
                .id(1L)
                .user(user)
                .role(role)
                .build();
    }
}
