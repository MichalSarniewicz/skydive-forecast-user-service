package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.UserRoleEntity;
import com.skydiveforecast.domain.port.out.UserRoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRoleRepositoryAdapter implements UserRoleRepositoryPort {

    private final UserRoleJpaRepository jpaRepository;

    @Override
    public UserRoleEntity save(UserRoleEntity userRole) {
        return jpaRepository.save(userRole);
    }

    @Override
    public Optional<UserRoleEntity> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<UserRoleEntity> findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId);
    }

    @Override
    public List<UserRoleEntity> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void deleteByUserIdAndRoleId(Long userId, Long roleId) {
        jpaRepository.deleteByUserIdAndRoleId(userId, roleId);
    }
}
