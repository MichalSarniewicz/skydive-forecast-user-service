package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface UserRoleJpaRepository extends JpaRepository<UserRoleEntity, Long> {

    List<UserRoleEntity> findByUserId(Long userId);

    void deleteByUserIdAndRoleId(Long userId, Long roleId);
}
