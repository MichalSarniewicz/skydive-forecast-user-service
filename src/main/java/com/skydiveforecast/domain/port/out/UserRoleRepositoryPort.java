package com.skydiveforecast.domain.port.out;

import com.skydiveforecast.domain.model.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRoleRepositoryPort {

    UserRole save(UserRole userRole);

    Optional<UserRole> findById(Long id);

    List<UserRole> findByUserId(Long userId);

    List<UserRole> findAll();

    Set<String> findRoleNamesByUserId(Long userId);

    void deleteById(Long id);

    void deleteByUserIdAndRoleId(Long userId, Long roleId);
}
