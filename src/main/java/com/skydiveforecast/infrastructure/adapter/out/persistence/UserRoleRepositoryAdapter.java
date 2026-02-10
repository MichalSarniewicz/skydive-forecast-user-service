package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.UserRole;
import com.skydiveforecast.infrastructure.persistence.entity.UserRoleEntity;
import com.skydiveforecast.domain.port.out.UserRoleRepositoryPort;
import com.skydiveforecast.infrastructure.persistence.mapper.UserRoleEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRoleRepositoryAdapter implements UserRoleRepositoryPort {

    private final UserRoleJpaRepository jpaRepository;
    private final UserRoleEntityMapper mapper;

    @Override
    public UserRole save(UserRole userRole) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(userRole)));
    }

    @Override
    public Optional<UserRole> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<UserRole> findByUserId(Long userId) {
        return mapper.toDomainList(jpaRepository.findByUserId(userId));
    }

    @Override
    public List<UserRole> findAll() {
        return mapper.toDomainList(jpaRepository.findAllWithRole());
    }

    public List<UserRoleEntity> findAllEntities() {
        return jpaRepository.findAllWithRole();
    }

    public List<UserRoleEntity> findEntitiesByUserId(Long userId) {
        return jpaRepository.findByUserId(userId);
    }

    public UserRoleEntity saveEntity(UserRoleEntity entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public Set<String> findRoleNamesByUserId(Long userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(userRole -> userRole.getRole().getName())
                .collect(Collectors.toSet());
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
