package com.skydiveforecast.application;

import com.skydiveforecast.domain.model.PermissionEntity;
import com.skydiveforecast.domain.port.in.*;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreatePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionsDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UpdatePermissionDto;
import com.skydiveforecast.infrastructure.adapter.out.persistance.PermissionRepository;
import com.skydiveforecast.infrastructure.adapter.out.persistance.RolePermissionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements GetAllPermissionsUseCase, CreatePermissionUseCase, UpdatePermissionUseCase,
        DeletePermissionUseCase, GetPermissionCodesByUserIdUseCase {

    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

    @Override
    public PermissionsDto getAllPermissions() {
        List<PermissionDto> permissions = permissionRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new PermissionsDto(permissions);
    }

    @Override
    public PermissionDto createPermission(CreatePermissionDto createPermissionDto) {
        PermissionEntity permission = new PermissionEntity();
        permission.setCode(createPermissionDto.getCode());
        permission.setDescription(createPermissionDto.getDescription());

        PermissionEntity savedPermission = permissionRepository.save(permission);
        return mapToDto(savedPermission);
    }

    @Override
    public PermissionDto updatePermission(Long id, UpdatePermissionDto updatePermissionDto) {
        PermissionEntity permission = permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));

        permission.setCode(updatePermissionDto.getCode());
        permission.setDescription(updatePermissionDto.getDescription());

        PermissionEntity updatedPermission = permissionRepository.save(permission);
        return mapToDto(updatedPermission);
    }

    @Override
    public void deletePermission(Long id) {
        permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with ID: " + id));
        permissionRepository.deleteById(id);
    }

    private PermissionDto mapToDto(PermissionEntity entity) {
        return PermissionDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .description(entity.getDescription())
                .build();
    }

    @Override
    public Set<String> getPermissionCodesByUserId(Long userId) {
        return rolePermissionRepository.findPermissionCodesByUserId(userId);
    }
}
