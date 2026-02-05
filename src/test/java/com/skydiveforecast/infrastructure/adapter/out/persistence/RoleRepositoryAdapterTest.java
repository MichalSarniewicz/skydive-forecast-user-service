package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.Role;
import com.skydiveforecast.infrastructure.persistence.entity.RoleEntity;
import com.skydiveforecast.infrastructure.persistence.mapper.RoleEntityMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleRepositoryAdapterTest {

    @Mock
    private RoleJpaRepository jpaRepository;

    @Mock
    private RoleEntityMapper mapper;

    @InjectMocks
    private RoleRepositoryAdapter adapter;

    @Test
    @DisplayName("Should save role successfully")
    void save_WhenRoleProvided_SavesAndReturnsRole() {
        // Arrange
        Role domain = Role.builder().id(1L).name("ADMIN").build();
        RoleEntity entity = new RoleEntity();
        RoleEntity savedEntity = new RoleEntity();
        Role savedDomain = Role.builder().id(1L).name("ADMIN").build();

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(savedDomain);

        // Act
        Role result = adapter.save(domain);

        // Assert
        assertNotNull(result);
        verify(jpaRepository).save(entity);
    }

    @Test
    @DisplayName("Should find role by id successfully")
    void findById_WhenRoleExists_ReturnsRole() {
        // Arrange
        RoleEntity entity = new RoleEntity();
        Role domain = Role.builder().id(1L).name("ADMIN").build();

        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        // Act
        Optional<Role> result = adapter.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().id());
    }

    @Test
    @DisplayName("Should return empty when role not found by id")
    void findById_WhenRoleNotExists_ReturnsEmpty() {
        // Arrange
        when(jpaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Role> result = adapter.findById(1L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should find role by name successfully")
    void findByName_WhenRoleExists_ReturnsRole() {
        // Arrange
        RoleEntity entity = new RoleEntity();
        Role domain = Role.builder().name("ADMIN").build();

        when(jpaRepository.findByName("ADMIN")).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        // Act
        Optional<Role> result = adapter.findByName("ADMIN");

        // Assert
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Should find all roles successfully")
    void findAll_WhenRolesExist_ReturnsAllRoles() {
        // Arrange
        RoleEntity entity = new RoleEntity();
        Role domain = Role.builder().id(1L).build();

        when(jpaRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomainList(any())).thenReturn(List.of(domain));

        // Act
        List<Role> result = adapter.findAll();

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should return true when role exists by name")
    void existsByName_WhenRoleExists_ReturnsTrue() {
        // Arrange
        when(jpaRepository.existsByName("ADMIN")).thenReturn(true);

        // Act
        boolean result = adapter.existsByName("ADMIN");

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Should delete role by id successfully")
    void deleteById_WhenIdProvided_DeletesRole() {
        // Act
        adapter.deleteById(1L);

        // Assert
        verify(jpaRepository).deleteById(1L);
    }
}
