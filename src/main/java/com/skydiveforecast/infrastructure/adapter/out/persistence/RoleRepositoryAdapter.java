package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.infrastructure.persistance.entity.RoleEntity;
import com.skydiveforecast.domain.port.out.RoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepositoryPort {

    private final RoleJpaRepository jpaRepository;

    @Override
    public RoleEntity save(RoleEntity role) {
        return jpaRepository.save(role);
    }

    @Override
    public Optional<RoleEntity> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<RoleEntity> findByName(String name) {
        return jpaRepository.findByName(name);
    }

    @Override
    public List<RoleEntity> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
