package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.infrastructure.persistance.entity.UserEntity;
import com.skydiveforecast.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository jpaRepository;

    @Override
    public UserEntity save(UserEntity user) {
        return jpaRepository.save(user);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return jpaRepository.findByEmail(email);
    }

    @Override
    public List<UserEntity> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<UserEntity> findAllWithRoles() {
        return jpaRepository.findAllWithRoles();
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
