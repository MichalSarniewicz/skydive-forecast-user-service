package com.skydiveforecast.infrastructure.adapter.out.persistance;

import com.skydiveforecast.domain.model.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

    Set<PermissionEntity> findByIdIn(Set<Long> ids);
}
