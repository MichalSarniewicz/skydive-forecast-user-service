package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.RoleEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleRepositoryAdapterTest {

    @Mock
    private RoleJpaRepository jpaRepository;

    @InjectMocks
    private RoleRepositoryAdapter adapter;

    @Test
    @DisplayName("Should save role successfully")
    void save_Success() {
        // Arrange
        RoleEntity role = new RoleEntity();
        role.setId(1L);
        when(jpaRepository.save(role)).thenReturn(role);

        // Act
        RoleEntity result = adapter.save(role);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(jpaRepository).save(role);
    }

    @Test
    @DisplayName("Should find role by id")
    void findById_Success() {
        // Arrange
        RoleEntity role = new RoleEntity();
        role.setId(1L);
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(role));

        // Act
        Optional<RoleEntity> result = adapter.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(jpaRepository).findById(1L);
    }

    @Test
    @DisplayName("Should find role by name")
    void findByName_Success() {
        // Arrange
        RoleEntity role = new RoleEntity();
        role.setName("ADMIN");
        when(jpaRepository.findByName("ADMIN")).thenReturn(Optional.of(role));

        // Act
        Optional<RoleEntity> result = adapter.findByName("ADMIN");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("ADMIN", result.get().getName());
        verify(jpaRepository).findByName("ADMIN");
    }

    @Test
    @DisplayName("Should find all roles")
    void findAll_Success() {
        // Arrange
        RoleEntity role1 = new RoleEntity();
        RoleEntity role2 = new RoleEntity();
        when(jpaRepository.findAll()).thenReturn(List.of(role1, role2));

        // Act
        List<RoleEntity> result = adapter.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(jpaRepository).findAll();
    }

    @Test
    @DisplayName("Should check if role name exists")
    void existsByName_Success() {
        // Arrange
        when(jpaRepository.existsByName("ADMIN")).thenReturn(true);

        // Act
        boolean result = adapter.existsByName("ADMIN");

        // Assert
        assertTrue(result);
        verify(jpaRepository).existsByName("ADMIN");
    }

    @Test
    @DisplayName("Should delete role by id")
    void deleteById_Success() {
        // Arrange
        Long roleId = 1L;

        // Act
        adapter.deleteById(roleId);

        // Assert
        verify(jpaRepository).deleteById(roleId);
    }
}
