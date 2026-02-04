package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.Permission;
import com.skydiveforecast.infrastructure.persistence.entity.PermissionEntity;
import com.skydiveforecast.infrastructure.persistence.mapper.PermissionEntityMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermissionRepositoryAdapterTest {

    @Mock
    private PermissionJpaRepository jpaRepository;

    @Mock
    private PermissionEntityMapper mapper;

    @InjectMocks
    private PermissionRepositoryAdapter adapter;

    @Test
    @DisplayName("Should save permission successfully")
    void save_WhenPermissionProvided_SavesAndReturnsPermission() {
        // Arrange
        Permission domain = Permission.builder().id(1L).code("USER_READ").build();
        PermissionEntity entity = new PermissionEntity();
        PermissionEntity savedEntity = new PermissionEntity();
        Permission savedDomain = Permission.builder().id(1L).code("USER_READ").build();

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(savedDomain);

        // Act
        Permission result = adapter.save(domain);

        // Assert
        assertNotNull(result);
        verify(jpaRepository).save(entity);
    }

    @Test
    @DisplayName("Should find permission by id successfully")
    void findById_WhenPermissionExists_ReturnsPermission() {
        // Arrange
        PermissionEntity entity = new PermissionEntity();
        Permission domain = Permission.builder().id(1L).build();

        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        // Act
        Optional<Permission> result = adapter.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().id());
    }

    @Test
    @DisplayName("Should return empty when permission not found by id")
    void findById_WhenPermissionNotExists_ReturnsEmpty() {
        // Arrange
        when(jpaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Permission> result = adapter.findById(1L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should find permission by code successfully")
    void findByCode_WhenPermissionExists_ReturnsPermission() {
        // Arrange
        PermissionEntity entity = new PermissionEntity();
        Permission domain = Permission.builder().code("USER_READ").build();

        when(jpaRepository.findByCode("USER_READ")).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        // Act
        Optional<Permission> result = adapter.findByCode("USER_READ");

        // Assert
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Should find all permissions successfully")
    void findAll_WhenPermissionsExist_ReturnsAllPermissions() {
        // Arrange
        PermissionEntity entity = new PermissionEntity();
        Permission domain = Permission.builder().id(1L).build();

        when(jpaRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomainList(any())).thenReturn(List.of(domain));

        // Act
        List<Permission> result = adapter.findAll();

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should return true when permission exists by code")
    void existsByCode_WhenPermissionExists_ReturnsTrue() {
        // Arrange
        when(jpaRepository.existsByCode("USER_READ")).thenReturn(true);

        // Act
        boolean result = adapter.existsByCode("USER_READ");

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Should delete permission by id successfully")
    void deleteById_WhenIdProvided_DeletesPermission() {
        // Act
        adapter.deleteById(1L);

        // Assert
        verify(jpaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should find permissions by ids successfully")
    void findByIdIn_WhenIdsProvided_ReturnsPermissions() {
        // Arrange
        PermissionEntity entity = new PermissionEntity();
        Permission domain = Permission.builder().id(1L).build();

        when(jpaRepository.findByIdIn(Set.of(1L))).thenReturn(List.of(entity));
        when(mapper.toDomainList(any())).thenReturn(List.of(domain));

        // Act
        List<Permission> result = adapter.findByIdIn(Set.of(1L));

        // Assert
        assertEquals(1, result.size());
    }
}
