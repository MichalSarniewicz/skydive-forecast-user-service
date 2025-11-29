package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.UserRoleEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface UserRoleJpaRepository extends JpaRepository<UserRoleEntity, Long> {

    @EntityGraph(attributePaths = {"role"})
    List<UserRoleEntity> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"role"})
    @Query("SELECT ur FROM UserRoleEntity ur")
    List<UserRoleEntity> findAllWithRole();

    void deleteByUserIdAndRoleId(Long userId, Long roleId);
}
