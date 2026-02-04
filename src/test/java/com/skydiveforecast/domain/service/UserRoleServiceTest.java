package com.skydiveforecast.domain.service;

import com.skydiveforecast.application.service.RoleService;
import com.skydiveforecast.application.service.UserRoleService;
import com.skydiveforecast.infrastructure.persistence.entity.RoleEntity;
import com.skydiveforecast.infrastructure.persistence.entity.UserEntity;
import com.skydiveforecast.infrastructure.persistence.entity.UserRoleEntity;
import com.skydiveforecast.domain.port.out.RoleRepositoryPort;
import com.skydiveforecast.domain.port.out.UserRepositoryPort;
import com.skydiveforecast.domain.port.out.UserRoleRepositoryPort;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreateUserRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolesDto;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.RoleMapper;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.UserRoleMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceTest {

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleService roleService;

    @Mock
    private UserRoleRepositoryPort userRoleRepository;

    @Mock
    private UserRepositoryPort userRepository;

    @Mock
    private RoleRepositoryPort roleRepository;

    @InjectMocks
    private UserRoleService userRoleService;

    @Mock
    private UserRoleMapper userRoleMapper;

    @Test
    void assignRoleToUser_Success() {
        CreateUserRoleDto dto = new CreateUserRoleDto(1L, 1L);
        UserEntity mockUser = new UserEntity();
        RoleEntity mockRole = new RoleEntity();
        mockRole.setId(dto.getRoleId());

        when(userRepository.findById(dto.getUserId())).thenReturn(Optional.of(mockUser));
        when(roleRepository.findById(dto.getRoleId())).thenReturn(Optional.of(mockRole));
        when(userRoleRepository.findByUserId(dto.getUserId())).thenReturn(Collections.emptyList());
        when(userRoleRepository.save(any(UserRoleEntity.class))).thenReturn(null);

        userRoleService.assignRoleToUser(dto);

        verify(userRepository, times(1)).findById(dto.getUserId());
        verify(roleRepository, times(1)).findById(dto.getRoleId());
        verify(userRoleRepository, times(1)).findByUserId(dto.getUserId());
        verify(userRoleRepository, times(1)).save(any(UserRoleEntity.class));
    }

    @Test
    void getAllRoles_EmptyList() {
        when(roleRepository.findAll()).thenReturn(Collections.emptyList());
        when(roleMapper.toDtoList(Collections.emptyList())).thenReturn(Collections.emptyList());

        RolesDto rolesDto = roleService.getAllRoles();

        assertEquals(0, rolesDto.getRoles().size());
        verify(roleRepository, times(1)).findAll();
        verify(roleMapper, times(1)).toDtoList(Collections.emptyList());
    }

    @Test
    void getAllRoles_MultipleRoles() {
        RoleEntity role1 = RoleEntity.builder().id(1L).name("Admin").build();
        RoleEntity role2 = RoleEntity.builder().id(2L).name("User").build();
        List<RoleEntity> entities = Arrays.asList(role1, role2);

        RoleDto roleDto1 = new RoleDto(1L, "Admin");
        RoleDto roleDto2 = new RoleDto(2L, "User");
        List<RoleDto> dtoList = Arrays.asList(roleDto1, roleDto2);

        when(roleRepository.findAll()).thenReturn(entities);
        when(roleMapper.toDtoList(entities)).thenReturn(dtoList);

        RolesDto rolesDto = roleService.getAllRoles();

        assertEquals(2, rolesDto.getRoles().size());
        assertEquals("Admin", rolesDto.getRoles().get(0).getName());
        assertEquals("User", rolesDto.getRoles().get(1).getName());
        verify(roleRepository, times(1)).findAll();
        verify(roleMapper, times(1)).toDtoList(entities);
    }

    @Test
    void assignRoleToUser_UserNotFound_ThrowsException() {
        CreateUserRoleDto dto = new CreateUserRoleDto(1L, 1L);

        when(userRepository.findById(dto.getUserId())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                userRoleService.assignRoleToUser(dto)
        );

        assertEquals("User not found with ID: 1", exception.getMessage());
        verify(userRepository, times(1)).findById(dto.getUserId());
        verifyNoInteractions(roleRepository, userRoleRepository, userRoleMapper);
    }

    @Test
    void assignRoleToUser_RoleNotFound_ThrowsException() {
        CreateUserRoleDto dto = new CreateUserRoleDto(1L, 1L);

        UserEntity mockUser = new UserEntity();
        when(userRepository.findById(dto.getUserId())).thenReturn(Optional.of(mockUser));
        when(roleRepository.findById(dto.getRoleId())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                userRoleService.assignRoleToUser(dto)
        );

        assertEquals("Role not found with ID: 1", exception.getMessage());
        verify(userRepository, times(1)).findById(dto.getUserId());
        verify(roleRepository, times(1)).findById(dto.getRoleId());
        verifyNoInteractions(userRoleRepository, userRoleMapper);
    }

    @Test
    void assignRoleToUser_RoleAlreadyAssigned_ThrowsException() {
        CreateUserRoleDto dto = new CreateUserRoleDto(1L, 1L);

        UserEntity mockUser = new UserEntity();
        RoleEntity mockRole = new RoleEntity();
        mockRole.setId(dto.getRoleId());

        UserRoleEntity existingUserRole = UserRoleEntity.builder()
                .user(mockUser)
                .role(mockRole)
                .build();

        when(userRepository.findById(dto.getUserId())).thenReturn(Optional.of(mockUser));
        when(roleRepository.findById(dto.getRoleId())).thenReturn(Optional.of(mockRole));
        when(userRoleRepository.findByUserId(dto.getUserId())).thenReturn(Collections.singletonList(existingUserRole));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                userRoleService.assignRoleToUser(dto)
        );

        assertEquals("User with ID: 1 already has role with ID: 1", exception.getMessage());
        verify(userRepository, times(1)).findById(dto.getUserId());
        verify(roleRepository, times(1)).findById(dto.getRoleId());
        verify(userRoleRepository, times(1)).findByUserId(dto.getUserId());
        verifyNoInteractions(userRoleMapper);
    }
}