package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.infrastructure.persistance.entity.RolePermissionEntity;
import com.skydiveforecast.domain.port.out.RolePermissionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class RolePermissionRepositoryAdapter implements RolePermissionRepositoryPort {

    private final RolePermissionJpaRepository jpaRepository;

    @Override
    public RolePermissionEntity save(RolePermissionEntity rolePermission) {
        return jpaRepository.save(rolePermission);
    }

    @Override
    public Optional<RolePermissionEntity> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<RolePermissionEntity> findByRoleId(Long roleId) {
        return jpaRepository.findByRoleId(roleId);
    }

    @Override
    public List<RolePermissionEntity> findByPermissionId(Long permissionId) {
        return jpaRepository.findByPermissionId(permissionId);
    }

    @Override
    public List<RolePermissionEntity> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void deleteByRoleIdAndPermissionId(Long roleId, Long permissionId) {
        jpaRepository.deleteByRoleIdAndPermissionId(roleId, permissionId);
    }

    @Override
    public void deleteAllByRoleId(Long roleId) {
        jpaRepository.deleteAllByRoleId(roleId);
    }

    @Override
    public void deleteAllByPermissionId(Long permissionId) {
        jpaRepository.deleteAllByPermissionId(permissionId);
    }

    @Override
    public Set<String> findPermissionCodesByUserId(Long userId) {
        return jpaRepository.findPermissionCodesByUserId(userId);
    }

    @Override
    public Set<String> findPermissionCodesByRoleId(Long roleId) {
        return jpaRepository.findPermissionCodesByRoleId(roleId);
    }

    @Override
    public boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId) {
        return jpaRepository.existsByRoleIdAndPermissionId(roleId, permissionId);
    }

    @Override
    public List<RolePermissionEntity> saveAll(List<RolePermissionEntity> entities) {
        return jpaRepository.saveAll(entities);
    }

    @Override
    public void delete(RolePermissionEntity entity) {
        jpaRepository.delete(entity);
    }
}
