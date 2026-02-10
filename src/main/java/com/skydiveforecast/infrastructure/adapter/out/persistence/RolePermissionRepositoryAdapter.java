package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.RolePermission;
import com.skydiveforecast.infrastructure.persistence.entity.RolePermissionEntity;
import com.skydiveforecast.domain.port.out.RolePermissionRepositoryPort;
import com.skydiveforecast.infrastructure.persistence.mapper.RolePermissionEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RolePermissionRepositoryAdapter implements RolePermissionRepositoryPort {

    private final RolePermissionJpaRepository jpaRepository;
    private final RolePermissionEntityMapper mapper;

    @Override
    public RolePermission save(RolePermission rolePermission) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(rolePermission)));
    }

    @Override
    public Optional<RolePermission> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<RolePermission> findByRoleId(Long roleId) {
        return mapper.toDomainList(jpaRepository.findByRoleId(roleId));
    }

    @Override
    public List<RolePermission> findByPermissionId(Long permissionId) {
        return mapper.toDomainList(jpaRepository.findByPermissionId(permissionId));
    }

    @Override
    public List<RolePermission> findAll() {
        return mapper.toDomainList(jpaRepository.findAll());
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
    public List<RolePermission> saveAll(List<RolePermission> rolePermissions) {
        List<RolePermissionEntity> entityList = rolePermissions.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());
        return mapper.toDomainList(jpaRepository.saveAll(entityList));
    }

    @Override
    public void delete(RolePermission entity) {
        jpaRepository.delete(mapper.toEntity(entity));
    }
}
