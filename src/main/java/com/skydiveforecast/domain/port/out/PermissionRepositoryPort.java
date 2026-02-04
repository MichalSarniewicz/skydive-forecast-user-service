package com.skydiveforecast.domain.port.out;

import com.skydiveforecast.domain.model.Permission;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PermissionRepositoryPort {

    Permission save(Permission permission);

    Optional<Permission> findById(Long id);

    Optional<Permission> findByCode(String code);

    List<Permission> findAll();

    boolean existsByCode(String code);

    void deleteById(Long id);

    List<Permission> findByIdIn(Set<Long> ids);
}
