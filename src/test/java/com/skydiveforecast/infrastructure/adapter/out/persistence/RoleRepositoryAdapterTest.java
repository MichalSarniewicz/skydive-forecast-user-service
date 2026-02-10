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
    void save_WhenValidRole_SavesAndReturnsRole() {
        // Arrange
        Role role = Role.builder().id(1L).name("ADMIN").build();
        RoleEntity entity = new RoleEntity();
        when(mapper.toEntity(role)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(role);

        // Act
        Role result = adapter.save(role);

        // Assert
        assertNotNull(result);
        assertEquals("ADMIN", result.name());
        verify(jpaRepository).save(entity);
    }

    @Test
    @DisplayName("Should find role by id")
    void findById_WhenRoleExists_ReturnsRole() {
        // Arrange
        RoleEntity entity = new RoleEntity();
        Role role = Role.builder().id(1L).name("ADMIN").build();
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(role);

        // Act
        Optional<Role> result = adapter.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("ADMIN", result.get().name());
    }

    @Test
    @DisplayName("Should find role by name")
    void findByName_WhenRoleExists_ReturnsRole() {
        // Arrange
        RoleEntity entity = new RoleEntity();
        Role role = Role.builder().id(1L).name("ADMIN").build();
        when(jpaRepository.findByName("ADMIN")).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(role);

        // Act
        Optional<Role> result = adapter.findByName("ADMIN");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("ADMIN", result.get().name());
    }

    @Test
    @DisplayName("Should find all roles")
    void findAll_WhenRolesExist_ReturnsAllRoles() {
        // Arrange
        RoleEntity entity = new RoleEntity();
        Role role = Role.builder().id(1L).name("ADMIN").build();
        when(jpaRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomainList(any())).thenReturn(List.of(role));

        // Act
        List<Role> result = adapter.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should check if role exists by name")
    void existsByName_WhenRoleExists_ReturnsTrue() {
        // Arrange
        when(jpaRepository.existsByName("ADMIN")).thenReturn(true);

        // Act
        boolean result = adapter.existsByName("ADMIN");

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Should delete role by id")
    void deleteById_WhenCalled_DeletesRole() {
        // Arrange
        doNothing().when(jpaRepository).deleteById(1L);

        // Act
        adapter.deleteById(1L);

        // Assert
        verify(jpaRepository).deleteById(1L);
    }
}
