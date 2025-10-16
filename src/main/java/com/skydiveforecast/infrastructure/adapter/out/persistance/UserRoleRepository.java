package com.skydiveforecast.infrastructure.adapter.out.persistance;

import com.skydiveforecast.domain.model.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    List<UserRoleEntity> findByUserId(Long userId);

    void deleteByUserIdAndRoleId(Long userId, Long roleId);
}