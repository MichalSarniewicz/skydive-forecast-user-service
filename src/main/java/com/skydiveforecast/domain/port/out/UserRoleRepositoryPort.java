package com.skydiveforecast.domain.port.out;

import com.skydiveforecast.infrastructure.persistance.entity.UserRoleEntity;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepositoryPort {

    UserRoleEntity save(UserRoleEntity userRole);

    Optional<UserRoleEntity> findById(Long id);

    List<UserRoleEntity> findByUserId(Long userId);

    List<UserRoleEntity> findAll();

    void deleteById(Long id);

    void deleteByUserIdAndRoleId(Long userId, Long roleId);
}
