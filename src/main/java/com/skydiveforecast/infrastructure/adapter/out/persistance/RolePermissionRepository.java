package com.skydiveforecast.infrastructure.adapter.out.persistance;

import com.skydiveforecast.domain.model.RolePermissionEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, Long> {

    @Query("SELECT rp.permission.code FROM RolePermissionEntity rp JOIN rp.role r JOIN r.userRoles ur WHERE ur.user.id = :userId")
    Set<String> findPermissionCodesByUserId(@Param("userId") Long userId);

    List<RolePermissionEntity> findByRoleId(Long roleId);

    List<RolePermissionEntity> findByPermissionId(Long permissionId);

    boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);

    void deleteByRoleId(@NotNull(message = "Role ID is required") Long roleId);

    @Query("SELECT rp.permission.code FROM RolePermissionEntity rp WHERE rp.role.id = :roleId")
    Set<String> findPermissionCodesByRoleId(@Param("roleId") Long roleId);

    void deleteByIdIn(List<Long> ids);
}


