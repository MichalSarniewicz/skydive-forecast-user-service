package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.PermissionEntity;
import com.skydiveforecast.domain.port.out.PermissionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class PermissionRepositoryAdapter implements PermissionRepositoryPort {

    private final PermissionJpaRepository jpaRepository;

    @Override
    public PermissionEntity save(PermissionEntity permission) {
        return jpaRepository.save(permission);
    }

    @Override
    public Optional<PermissionEntity> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<PermissionEntity> findByCode(String code) {
        return jpaRepository.findByCode(code);
    }

    @Override
    public List<PermissionEntity> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public boolean existsByCode(String code) {
        return jpaRepository.existsByCode(code);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<PermissionEntity> findByIdIn(Set<Long> ids) {
        return jpaRepository.findByIdIn(ids);
    }
}
