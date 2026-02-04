package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.infrastructure.persistance.entity.RolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

interface RolePermissionJpaRepository extends JpaRepository<RolePermissionEntity, Long> {

    List<RolePermissionEntity> findByRoleId(Long roleId);

    List<RolePermissionEntity> findByPermissionId(Long permissionId);

    void deleteByRoleIdAndPermissionId(Long roleId, Long permissionId);

    void deleteAllByRoleId(Long roleId);

    void deleteAllByPermissionId(Long permissionId);

    boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);

    @Query("SELECT p.code FROM RolePermissionEntity rp JOIN rp.permission p JOIN UserRoleEntity ur ON rp.role.id = ur.role.id WHERE ur.user.id = :userId")
    Set<String> findPermissionCodesByUserId(Long userId);

    @Query("SELECT p.code FROM RolePermissionEntity rp JOIN rp.permission p WHERE rp.role.id = :roleId")
    Set<String> findPermissionCodesByRoleId(Long roleId);
}
