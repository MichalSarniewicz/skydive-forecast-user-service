package com.skydiveforecast.domain.port.out;

import com.skydiveforecast.infrastructure.persistence.entity.RolePermissionEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RolePermissionRepositoryPort {

    RolePermissionEntity save(RolePermissionEntity rolePermission);

    Optional<RolePermissionEntity> findById(Long id);

    List<RolePermissionEntity> findByRoleId(Long roleId);

    List<RolePermissionEntity> findByPermissionId(Long permissionId);

    List<RolePermissionEntity> findAll();

    void deleteById(Long id);

    void deleteByRoleIdAndPermissionId(Long roleId, Long permissionId);

    void deleteAllByRoleId(Long roleId);

    void deleteAllByPermissionId(Long permissionId);

    Set<String> findPermissionCodesByUserId(Long userId);

    Set<String> findPermissionCodesByRoleId(Long roleId);

    boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);

    List<RolePermissionEntity> saveAll(List<RolePermissionEntity> entities);

    void delete(RolePermissionEntity entity);
}
