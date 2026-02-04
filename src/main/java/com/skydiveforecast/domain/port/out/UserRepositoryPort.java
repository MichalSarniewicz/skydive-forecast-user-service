package com.skydiveforecast.domain.port.out;

import com.skydiveforecast.infrastructure.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {

    UserEntity save(UserEntity user);

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAll();

    List<UserEntity> findAllWithRoles();

    boolean existsByEmail(String email);

    void deleteById(Long id);
}
