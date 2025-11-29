package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = { "roles", "roles.role" })
    @Query("SELECT u FROM UserEntity u")
    List<UserEntity> findAllWithRoles();
}
