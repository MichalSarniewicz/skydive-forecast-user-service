package com.skydiveforecast.domain.port.out;

import com.skydiveforecast.domain.model.RolePermission;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RolePermissionRepositoryPort {

    RolePermission save(RolePermission rolePermission);

    Optional<RolePermission> findById(Long id);

    List<RolePermission> findByRoleId(Long roleId);

    List<RolePermission> findByPermissionId(Long permissionId);

    List<RolePermission> findAll();

    void deleteById(Long id);

    void deleteByRoleIdAndPermissionId(Long roleId, Long permissionId);

    void deleteAllByRoleId(Long roleId);

    void deleteAllByPermissionId(Long permissionId);

    Set<String> findPermissionCodesByUserId(Long userId);

    Set<String> findPermissionCodesByRoleId(Long roleId);

    boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);

    List<RolePermission> saveAll(List<RolePermission> entities);

    void delete(RolePermission entity);
}
