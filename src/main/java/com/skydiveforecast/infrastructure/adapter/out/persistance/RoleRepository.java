package com.skydiveforecast.infrastructure.adapter.out.persistance;

import com.skydiveforecast.domain.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}