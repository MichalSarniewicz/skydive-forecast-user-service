package com.skydiveforecast.domain.port.out;

import com.skydiveforecast.infrastructure.persistance.entity.PermissionEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PermissionRepositoryPort {

    PermissionEntity save(PermissionEntity permission);

    Optional<PermissionEntity> findById(Long id);

    Optional<PermissionEntity> findByCode(String code);

    List<PermissionEntity> findAll();

    boolean existsByCode(String code);

    void deleteById(Long id);

    List<PermissionEntity> findByIdIn(Set<Long> ids);
}
