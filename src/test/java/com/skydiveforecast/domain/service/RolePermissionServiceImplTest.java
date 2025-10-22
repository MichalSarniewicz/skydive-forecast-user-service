package com.skydiveforecast.domain.service;

import com.skydiveforecast.application.RolePermissionServiceImpl;
import com.skydiveforecast.domain.model.*;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.*;
import com.skydiveforecast.infrastructure.adapter.out.persistance.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Rollback
class RolePermissionServiceImplTest {

    @Autowired
    private RolePermissionServiceImpl rolePermissionService;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Test
    @DisplayName("Should create a RolePermission relationship successfully")
    void createRolePermission_Success() {
        // Arrange
        RoleEntity role = RoleEntity.builder().name("Admin").build();
        role = roleRepository.save(role);

        PermissionEntity permission = PermissionEntity.builder().code("READ_PRIVILEGES").description("Allows read access").build();
        permission = permissionRepository.save(permission);

        CreateRolePermissionDto createRolePermissionDto = new CreateRolePermissionDto();
        createRolePermissionDto.setRoleId(role.getId());
        createRolePermissionDto.setPermissionId(permission.getId());

        // Act
        var savedDto = rolePermissionService.createRolePermission(createRolePermissionDto);

        // Assert
        assertThat(savedDto).isNotNull();
        assertThat(savedDto.getRoleId()).isEqualTo(role.getId());
        assertThat(savedDto.getPermissionId()).isEqualTo(permission.getId());
        assertThat(rolePermissionRepository.existsById(savedDto.getId())).isTrue();
    }

    @Test
    @DisplayName("Should throw exception when creating a duplicate RolePermission relationship")
    void createRolePermission_Duplicate() {
        // Arrange
        RoleEntity role = RoleEntity.builder().name("User").build();
        role = roleRepository.save(role);

        PermissionEntity permission = PermissionEntity.builder().code("WRITE_PRIVILEGES").description("Allows write access").build();
        permission = permissionRepository.save(permission);

        RolePermissionEntity rolePermission = RolePermissionEntity.builder().role(role).permission(permission).build();
        rolePermissionRepository.save(rolePermission);

        CreateRolePermissionDto createRolePermissionDto = new CreateRolePermissionDto();
        createRolePermissionDto.setRoleId(role.getId());
        createRolePermissionDto.setPermissionId(permission.getId());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                rolePermissionService.createRolePermission(createRolePermissionDto));

        assertThat(exception.getMessage()).isEqualTo("Role-Permission relationship already exists");
    }

    @Test
    @DisplayName("Should throw exception when Role does not exist")
    void createRolePermission_RoleNotFound() {
        // Arrange
        PermissionEntity permission = PermissionEntity.builder().code("DELETE_PRIVILEGES").description("Allows delete access").build();
        permission = permissionRepository.save(permission);

        CreateRolePermissionDto createRolePermissionDto = new CreateRolePermissionDto();
        createRolePermissionDto.setRoleId(999L);
        createRolePermissionDto.setPermissionId(permission.getId());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                rolePermissionService.createRolePermission(createRolePermissionDto));

        assertThat(exception.getMessage()).isEqualTo("Role not found");
    }

    @Test
    @DisplayName("Should throw exception when Permission does not exist")
    void createRolePermission_PermissionNotFound() {
        // Arrange
        RoleEntity role = RoleEntity.builder().name("Admin").build();
        role = roleRepository.save(role);

        CreateRolePermissionDto createRolePermissionDto = new CreateRolePermissionDto();
        createRolePermissionDto.setRoleId(role.getId());
        createRolePermissionDto.setPermissionId(999L);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                rolePermissionService.createRolePermission(createRolePermissionDto));

        assertThat(exception.getMessage()).isEqualTo("Permission not found");
    }
}