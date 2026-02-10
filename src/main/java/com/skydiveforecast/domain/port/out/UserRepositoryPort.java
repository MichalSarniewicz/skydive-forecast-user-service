package com.skydiveforecast.domain.port.out;

import com.skydiveforecast.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    List<User> findAllWithRoles();

    boolean existsByEmail(String email);

    void deleteById(Long id);
}
