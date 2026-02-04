package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.infrastructure.persistance.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);

    boolean existsByName(String name);
}
