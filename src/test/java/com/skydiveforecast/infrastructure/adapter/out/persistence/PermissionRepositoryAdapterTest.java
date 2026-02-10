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
    void save_WhenValidPermission_SavesAndReturnsPermission() {
        // Arrange
        Permission permission = Permission.builder().id(1L).code("USER_READ").description("Read users").build();
        PermissionEntity entity = new PermissionEntity();
        when(mapper.toEntity(permission)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(permission);

        // Act
        Permission result = adapter.save(permission);

        // Assert
        assertNotNull(result);
        assertEquals("USER_READ", result.code());
        verify(jpaRepository).save(entity);
    }

    @Test
    @DisplayName("Should find permission by id")
    void findById_WhenPermissionExists_ReturnsPermission() {
        // Arrange
        PermissionEntity entity = new PermissionEntity();
        Permission permission = Permission.builder().id(1L).code("USER_READ").description("Read users").build();
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(permission);

        // Act
        Optional<Permission> result = adapter.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("USER_READ", result.get().code());
    }

    @Test
    @DisplayName("Should find permission by code")
    void findByCode_WhenPermissionExists_ReturnsPermission() {
        // Arrange
        PermissionEntity entity = new PermissionEntity();
        Permission permission = Permission.builder().id(1L).code("USER_READ").description("Read users").build();
        when(jpaRepository.findByCode("USER_READ")).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(permission);

        // Act
        Optional<Permission> result = adapter.findByCode("USER_READ");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("USER_READ", result.get().code());
    }

    @Test
    @DisplayName("Should find all permissions")
    void findAll_WhenPermissionsExist_ReturnsAllPermissions() {
        // Arrange
        PermissionEntity entity = new PermissionEntity();
        Permission permission = Permission.builder().id(1L).code("USER_READ").description("Read users").build();
        when(jpaRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomainList(any())).thenReturn(List.of(permission));

        // Act
        List<Permission> result = adapter.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should check if permission exists by code")
    void existsByCode_WhenPermissionExists_ReturnsTrue() {
        // Arrange
        when(jpaRepository.existsByCode("USER_READ")).thenReturn(true);

        // Act
        boolean result = adapter.existsByCode("USER_READ");

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Should delete permission by id")
    void deleteById_WhenCalled_DeletesPermission() {
        // Arrange
        doNothing().when(jpaRepository).deleteById(1L);

        // Act
        adapter.deleteById(1L);

        // Assert
        verify(jpaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should find permissions by ids")
    void findByIdIn_WhenPermissionsExist_ReturnsPermissions() {
        // Arrange
        PermissionEntity entity = new PermissionEntity();
        Permission permission = Permission.builder().id(1L).code("USER_READ").description("Read users").build();
        when(jpaRepository.findByIdIn(Set.of(1L))).thenReturn(List.of(entity));
        when(mapper.toDomainList(any())).thenReturn(List.of(permission));

        // Act
        List<Permission> result = adapter.findByIdIn(Set.of(1L));

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
