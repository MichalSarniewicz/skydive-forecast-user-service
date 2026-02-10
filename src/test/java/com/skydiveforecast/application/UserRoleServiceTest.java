package com.skydiveforecast.application;

import com.skydiveforecast.application.service.UserRoleService;
import com.skydiveforecast.domain.model.User;
import com.skydiveforecast.domain.port.out.RoleRepositoryPort;
import com.skydiveforecast.domain.port.out.UserRepositoryPort;
import com.skydiveforecast.domain.port.out.UserRoleRepositoryPort;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreateUserRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserRolesDto;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.UserRoleMapper;
import com.skydiveforecast.infrastructure.adapter.out.persistence.UserRoleRepositoryAdapter;
import com.skydiveforecast.infrastructure.persistence.entity.RoleEntity;
import com.skydiveforecast.infrastructure.persistence.entity.UserRoleEntity;
import com.skydiveforecast.infrastructure.persistence.mapper.RoleEntityMapper;
import com.skydiveforecast.infrastructure.persistence.mapper.UserEntityMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
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

    @Mock
    private UserEntityMapper userEntityMapper;

    @Mock
    private UserRoleRepositoryAdapter userRoleRepositoryAdapter;

    @InjectMocks
    private UserRoleService userRoleService;

    @BeforeEach
    void setUp() {
        reset(userRoleRepository, userRoleRepositoryAdapter, userRepository, roleRepository);
    }

    @Test
    @DisplayName("Should get all user roles successfully")
    void getAllUserRoles_WhenUserRolesExist_ReturnsAllUserRoles() {
        // Arrange
        UserRoleEntity entity = new UserRoleEntity();
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        roleEntity.setName("USER");
        entity.setRole(roleEntity);
        UserRoleDto dto = new UserRoleDto();

        when(userRoleRepositoryAdapter.findAllEntities()).thenReturn(List.of(entity));
        when(userRoleMapper.toDtoList(any())).thenReturn(List.of(dto));

        // Act
        UserRolesDto result = userRoleService.getAllUserRoles();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getUserRoles().size());
        verify(userRoleRepositoryAdapter).findAllEntities();
    }

    @Test
    @DisplayName("Should get user roles by user id successfully")
    void getUserRoles_WhenUserHasRoles_ReturnsUserRoles() {
        // Arrange
        UserRoleEntity entity = new UserRoleEntity();
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        roleEntity.setName("USER");
        entity.setRole(roleEntity);
        UserRoleDto dto = new UserRoleDto();

        when(userRoleRepositoryAdapter.findEntitiesByUserId(1L)).thenReturn(List.of(entity));
        when(userRoleMapper.toDtoList(any())).thenReturn(List.of(dto));

        // Act
        UserRolesDto result = userRoleService.getUserRoles(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getUserRoles().size());
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

        User user = User.builder().id(1L).email("test@test.com").firstName("John").lastName("Doe").isActive(true).build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userRoleService.assignRoleToUser(dto));
    }
}
