package com.skydiveforecast.domain.port.out;

import com.skydiveforecast.infrastructure.persistance.entity.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface RoleRepositoryPort {

    RoleEntity save(RoleEntity role);

    Optional<RoleEntity> findById(Long id);

    Optional<RoleEntity> findByName(String name);

    List<RoleEntity> findAll();

    boolean existsByName(String name);

    void deleteById(Long id);
}
