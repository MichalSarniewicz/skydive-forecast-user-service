package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.model.PermissionEntity;
import com.skydiveforecast.domain.port.in.*;
import com.skydiveforecast.domain.port.out.PermissionRepositoryPort;
import com.skydiveforecast.domain.port.out.RolePermissionRepositoryPort;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreatePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionsDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UpdatePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.PermissionMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.skydiveforecast.infrastructure.config.CacheConfig.PERMISSIONS_CACHE;
import static com.skydiveforecast.infrastructure.config.CacheConfig.PERMISSION_CODES_CACHE;

@Service
@RequiredArgsConstructor
public class PermissionService implements GetAllPermissionsUseCase, CreatePermissionUseCase, UpdatePermissionUseCase,
        DeletePermissionUseCase, GetPermissionCodesByUserIdUseCase {

    private final PermissionRepositoryPort permissionRepository;
    private final RolePermissionRepositoryPort rolePermissionRepository;
    private final PermissionMapper permissionMapper;

    @Override
    @Cacheable(value = PERMISSIONS_CACHE, key = "'all'")
    public PermissionsDto getAllPermissions() {
        return new PermissionsDto(permissionMapper.toDtoList(permissionRepository.findAll()));
    }

    @Override
    @CacheEvict(value = PERMISSIONS_CACHE, allEntries = true)
    public PermissionDto createPermission(CreatePermissionDto createPermissionDto) {
        PermissionEntity permission = permissionMapper.toEntity(createPermissionDto);
        return permissionMapper.toDto(permissionRepository.save(permission));
    }

    @Override
    @CacheEvict(value = PERMISSIONS_CACHE, allEntries = true)
    public PermissionDto updatePermission(Long id, UpdatePermissionDto updatePermissionDto) {
        PermissionEntity permission = permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));
        permissionMapper.updateEntityFromDto(updatePermissionDto, permission);
        return permissionMapper.toDto(permissionRepository.save(permission));
    }

    @Override
    @CacheEvict(value = PERMISSIONS_CACHE, allEntries = true)
    public void deletePermission(Long id) {
        if (permissionRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Permission not found with ID: " + id);
        }
        permissionRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = PERMISSION_CODES_CACHE, key = "'user:' + #userId")
    public Set<String> getPermissionCodesByUserId(Long userId) {
        return rolePermissionRepository.findPermissionCodesByUserId(userId);
    }
}
