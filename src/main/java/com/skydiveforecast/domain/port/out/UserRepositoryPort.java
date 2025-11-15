package com.skydiveforecast.domain.port.out;

import com.skydiveforecast.domain.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {

    UserEntity save(UserEntity user);

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAll();

    boolean existsByEmail(String email);

    void deleteById(Long id);
}
