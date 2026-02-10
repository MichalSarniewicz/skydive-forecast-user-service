package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.model.Permission;
import com.skydiveforecast.domain.model.Role;
import com.skydiveforecast.domain.model.RolePermission;
import com.skydiveforecast.infrastructure.persistence.entity.RolePermissionEntity;
import com.skydiveforecast.domain.port.in.*;
import com.skydiveforecast.domain.port.out.PermissionRepositoryPort;
import com.skydiveforecast.domain.port.out.RolePermissionRepositoryPort;
import com.skydiveforecast.domain.port.out.RoleRepositoryPort;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.AssignPermissionsToRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreateRolePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolePermissionsDto;
import com.skydiveforecast.infrastructure.persistence.mapper.PermissionEntityMapper;
import com.skydiveforecast.infrastructure.persistence.mapper.RoleEntityMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.skydiveforecast.infrastructure.config.CacheConfig.PERMISSION_CODES_CACHE;
import static com.skydiveforecast.infrastructure.config.CacheConfig.ROLE_PERMISSIONS_CACHE;

@Service
@RequiredArgsConstructor
@Transactional
public class RolePermissionService implements
        GetAllRolePermissionsUseCase,
        GetRolePermissionsByRoleIdUseCase,
        GetRolePermissionsByPermissionIdUseCase,
        CreateRolePermissionUseCase,
        AssignPermissionsToRoleUseCase,
        DeleteRolePermissionUseCase,
        GetPermissionCodesByRoleIdUseCase,
        DeleteAllRolePermissionsByRoleIdUseCase,
        DeleteAllRolePermissionsByPermissionIdUseCase {

    private final RolePermissionRepositoryPort rolePermissionRepository;
    private final RoleRepositoryPort roleRepository;
    private final PermissionRepositoryPort permissionRepository;
    private final PermissionEntityMapper permissionEntityMapper;
    private final RoleEntityMapper roleEntityMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = ROLE_PERMISSIONS_CACHE, key = "'all'")
    public RolePermissionsDto getAllRolePermissions() {
        List<RolePermission> rolePermissions = rolePermissionRepository.findAll();
        List<RolePermissionDto> dtoList = rolePermissions.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return RolePermissionsDto.builder()
                .rolePermissions(dtoList)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = ROLE_PERMISSIONS_CACHE, key = "'role:' + #roleId")
    public RolePermissionsDto getRolePermissionsByRoleId(Long roleId) {
        List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(roleId);
        List<RolePermissionDto> dtoList = rolePermissions.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return RolePermissionsDto.builder()
                .rolePermissions(dtoList)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = ROLE_PERMISSIONS_CACHE, key = "'permission:' + #permissionId")
    public RolePermissionsDto getRolePermissionsByPermissionId(Long permissionId) {
        List<RolePermission> rolePermissions = rolePermissionRepository.findByPermissionId(permissionId);
        List<RolePermissionDto> dtoList = rolePermissions.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return RolePermissionsDto.builder()
                .rolePermissions(dtoList)
                .build();
    }

    @Override
    @CacheEvict(value = {ROLE_PERMISSIONS_CACHE, PERMISSION_CODES_CACHE}, allEntries = true)
    public RolePermissionDto createRolePermission(CreateRolePermissionDto createRolePermissionDto) {
        // Check if a relationship already exists
        if (rolePermissionRepository.existsByRoleIdAndPermissionId(
                createRolePermissionDto.getRoleId(),
                createRolePermissionDto.getPermissionId())) {
            throw new IllegalArgumentException("Role-Permission relationship already exists");
        }
        Role role = roleRepository.findById(createRolePermissionDto.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        Permission permission = permissionRepository.findById(createRolePermissionDto.getPermissionId())
                .orElseThrow(() -> new IllegalArgumentException("Permission not found"));

        RolePermission rolePermission = RolePermission.builder()
                .roleId(role.id())
                .permissionId(permission.id())
                .build();

        RolePermission savedRolePermission = rolePermissionRepository.save(rolePermission);
        return mapToDto(savedRolePermission);
    }

    @Override
    @CacheEvict(value = {ROLE_PERMISSIONS_CACHE, PERMISSION_CODES_CACHE}, allEntries = true)
    public List<RolePermissionDto> assignPermissionsToRole(AssignPermissionsToRoleDto assignPermissionsToRoleDto) {
        Role role = roleRepository.findById(assignPermissionsToRoleDto.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        List<Permission> permissionsList = permissionRepository.findByIdIn(assignPermissionsToRoleDto.getPermissionIds());
        Set<Permission> permissions = Set.copyOf(permissionsList);

        if (permissions.size() != assignPermissionsToRoleDto.getPermissionIds().size()) {
            throw new IllegalArgumentException("One or more permissions not found");
        }

        // Remove existing permissions for this role
        rolePermissionRepository.deleteAllByRoleId(assignPermissionsToRoleDto.getRoleId());

        // Create new role-permission relationships
        List<RolePermission> rolePermissions = permissions.stream()
                .map(permission -> RolePermission.builder()
                        .roleId(role.id())
                        .permissionId(permission.id())
                        .build())
                .collect(Collectors.toList());

        List<RolePermission> savedRolePermissions = rolePermissionRepository.saveAll(rolePermissions);
        return savedRolePermissions.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = {ROLE_PERMISSIONS_CACHE, PERMISSION_CODES_CACHE}, allEntries = true)
    public void deleteRolePermission(Long id) {
        RolePermission rolePermission = rolePermissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role-Permission not found with ID: " + id));
        rolePermissionRepository.delete(rolePermission);
    }

    @Override
    @Cacheable(value = PERMISSION_CODES_CACHE, key = "'role:' + #roleId")
    public Set<String> getPermissionCodesByRoleId(Long roleId) {
        return rolePermissionRepository.findPermissionCodesByRoleId(roleId);
    }

    @Override
    @CacheEvict(value = {ROLE_PERMISSIONS_CACHE, PERMISSION_CODES_CACHE}, allEntries = true)
    public void deleteAllRolePermissionsByRoleId(Long roleId) {
        List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(roleId);
        if (rolePermissions.isEmpty()) {
            throw new EntityNotFoundException("No permissions found for role with ID: " + roleId);
        }

        List<Long> ids = rolePermissions.stream()
                .map(RolePermission::id)
                .collect(Collectors.toList());

        ids.forEach(rolePermissionRepository::deleteById);
    }

    @Override
    @CacheEvict(value = {ROLE_PERMISSIONS_CACHE, PERMISSION_CODES_CACHE}, allEntries = true)
    public void deleteAllRolePermissionsByPermissionId(Long permissionId) {
        List<RolePermission> rolePermissions = rolePermissionRepository.findByPermissionId(permissionId);
        if (rolePermissions.isEmpty()) {
            throw new EntityNotFoundException("No role assignments found for permission with ID: " + permissionId);
        }
        List<Long> ids = rolePermissions.stream()
                .map(RolePermission::id)
                .collect(Collectors.toList());

        ids.forEach(rolePermissionRepository::deleteById);
    }

    private RolePermissionDto mapToDto(RolePermission rolePermission) {
        Role role = roleRepository.findById(rolePermission.roleId())
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        Permission permission = permissionRepository.findById(rolePermission.permissionId())
                .orElseThrow(() -> new EntityNotFoundException("Permission not found"));
        
        return RolePermissionDto.builder()
                .id(rolePermission.id())
                .roleId(role.id())
                .roleName(role.name())
                .permissionId(permission.id())
                .permissionCode(permission.code())
                .permissionDescription(permission.description())
                .build();
    }
}