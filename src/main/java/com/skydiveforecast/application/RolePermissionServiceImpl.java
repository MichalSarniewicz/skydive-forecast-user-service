package com.skydiveforecast.application;

import com.skydiveforecast.domain.model.PermissionEntity;
import com.skydiveforecast.domain.model.RoleEntity;
import com.skydiveforecast.domain.model.RolePermissionEntity;
import com.skydiveforecast.domain.port.in.*;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.AssignPermissionsToRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreateRolePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolePermissionsDto;
import com.skydiveforecast.infrastructure.adapter.out.persistance.PermissionRepository;
import com.skydiveforecast.infrastructure.adapter.out.persistance.RolePermissionRepository;
import com.skydiveforecast.infrastructure.adapter.out.persistance.RoleRepository;
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
public class RolePermissionServiceImpl implements
        GetAllRolePermissionsUseCase,
        GetRolePermissionsByRoleIdUseCase,
        GetRolePermissionsByPermissionIdUseCase,
        CreateRolePermissionUseCase,
        AssignPermissionsToRoleUseCase,
        DeleteRolePermissionUseCase,
        GetPermissionCodesByRoleIdUseCase,
        DeleteAllRolePermissionsByRoleIdUseCase,
        DeleteAllRolePermissionsByPermissionIdUseCase {

    private final RolePermissionRepository rolePermissionRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = ROLE_PERMISSIONS_CACHE, key = "'all'")
    public RolePermissionsDto getAllRolePermissions() {
        List<RolePermissionEntity> entities = rolePermissionRepository.findAll();
        List<RolePermissionDto> dtoList = entities.stream()
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
        List<RolePermissionEntity> entities = rolePermissionRepository.findByRoleId(roleId);
        List<RolePermissionDto> dtoList = entities.stream()
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
        List<RolePermissionEntity> entities = rolePermissionRepository.findByPermissionId(permissionId);
        List<RolePermissionDto> dtoList = entities.stream()
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
        RoleEntity role = roleRepository.findById(createRolePermissionDto.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        PermissionEntity permission = permissionRepository.findById(createRolePermissionDto.getPermissionId())
                .orElseThrow(() -> new IllegalArgumentException("Permission not found"));

        RolePermissionEntity entity = RolePermissionEntity.builder()
                .role(role)
                .permission(permission)
                .build();

        RolePermissionEntity savedEntity = rolePermissionRepository.save(entity);
        return mapToDto(savedEntity);
    }

    @Override
    @CacheEvict(value = {ROLE_PERMISSIONS_CACHE, PERMISSION_CODES_CACHE}, allEntries = true)
    public List<RolePermissionDto> assignPermissionsToRole(AssignPermissionsToRoleDto assignPermissionsToRoleDto) {
        RoleEntity role = roleRepository.findById(assignPermissionsToRoleDto.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        Set<PermissionEntity> permissions = permissionRepository.findByIdIn(assignPermissionsToRoleDto.getPermissionIds());

        if (permissions.size() != assignPermissionsToRoleDto.getPermissionIds().size()) {
            throw new IllegalArgumentException("One or more permissions not found");
        }

        // Remove existing permissions for this role
        rolePermissionRepository.deleteByRoleId(assignPermissionsToRoleDto.getRoleId());

        // Create new role-permission relationships
        List<RolePermissionEntity> entities = permissions.stream()
                .map(permission -> RolePermissionEntity.builder()
                        .role(role)
                        .permission(permission)
                        .build())
                .collect(Collectors.toList());

        List<RolePermissionEntity> savedEntities = rolePermissionRepository.saveAll(entities);
        return savedEntities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = {ROLE_PERMISSIONS_CACHE, PERMISSION_CODES_CACHE}, allEntries = true)
    public void deleteRolePermission(Long id) {
        RolePermissionEntity rolePermission = rolePermissionRepository.findById(id)
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
        List<RolePermissionEntity> entities = rolePermissionRepository.findByRoleId(roleId);
        if (entities.isEmpty()) {
            throw new EntityNotFoundException("No permissions found for role with ID: " + roleId);
        }

        List<Long> ids = entities.stream()
                .map(RolePermissionEntity::getId)
                .collect(Collectors.toList());

        rolePermissionRepository.deleteByIdIn(ids);
    }

    @Override
    @CacheEvict(value = {ROLE_PERMISSIONS_CACHE, PERMISSION_CODES_CACHE}, allEntries = true)
    public void deleteAllRolePermissionsByPermissionId(Long permissionId) {
        List<RolePermissionEntity> entities = rolePermissionRepository.findByPermissionId(permissionId);
        if (entities.isEmpty()) {
            throw new EntityNotFoundException("No role assignments found for permission with ID: " + permissionId);
        }
        List<Long> ids = entities.stream()
                .map(RolePermissionEntity::getId)
                .collect(Collectors.toList());

        rolePermissionRepository.deleteByIdIn(ids);
    }

    private RolePermissionDto mapToDto(RolePermissionEntity entity) {
        return RolePermissionDto.builder()
                .id(entity.getId())
                .roleId(entity.getRole().getId())
                .roleName(entity.getRole().getName())
                .permissionId(entity.getPermission().getId())
                .permissionCode(entity.getPermission().getCode())
                .permissionDescription(entity.getPermission().getDescription())
                .build();
    }
}