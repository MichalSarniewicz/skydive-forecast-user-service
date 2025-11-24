package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.PermissionEntity;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermissionRepositoryAdapterTest {

    @Mock
    private PermissionJpaRepository jpaRepository;

    @InjectMocks
    private PermissionRepositoryAdapter adapter;

    @Test
    @DisplayName("Should save permission successfully")
    void save_Success() {
        // Arrange
        PermissionEntity permission = new PermissionEntity();
        permission.setId(1L);
        when(jpaRepository.save(permission)).thenReturn(permission);

        // Act
        PermissionEntity result = adapter.save(permission);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(jpaRepository).save(permission);
    }

    @Test
    @DisplayName("Should find permission by id")
    void findById_Success() {
        // Arrange
        PermissionEntity permission = new PermissionEntity();
        permission.setId(1L);
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(permission));

        // Act
        Optional<PermissionEntity> result = adapter.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(jpaRepository).findById(1L);
    }

    @Test
    @DisplayName("Should find permission by code")
    void findByCode_Success() {
        // Arrange
        PermissionEntity permission = new PermissionEntity();
        permission.setCode("USER_VIEW");
        when(jpaRepository.findByCode("USER_VIEW")).thenReturn(Optional.of(permission));

        // Act
        Optional<PermissionEntity> result = adapter.findByCode("USER_VIEW");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("USER_VIEW", result.get().getCode());
        verify(jpaRepository).findByCode("USER_VIEW");
    }

    @Test
    @DisplayName("Should find all permissions")
    void findAll_Success() {
        // Arrange
        PermissionEntity perm1 = new PermissionEntity();
        PermissionEntity perm2 = new PermissionEntity();
        when(jpaRepository.findAll()).thenReturn(List.of(perm1, perm2));

        // Act
        List<PermissionEntity> result = adapter.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(jpaRepository).findAll();
    }

    @Test
    @DisplayName("Should check if permission code exists")
    void existsByCode_Success() {
        // Arrange
        when(jpaRepository.existsByCode("USER_VIEW")).thenReturn(true);

        // Act
        boolean result = adapter.existsByCode("USER_VIEW");

        // Assert
        assertTrue(result);
        verify(jpaRepository).existsByCode("USER_VIEW");
    }

    @Test
    @DisplayName("Should delete permission by id")
    void deleteById_Success() {
        // Arrange
        Long permissionId = 1L;

        // Act
        adapter.deleteById(permissionId);

        // Assert
        verify(jpaRepository).deleteById(permissionId);
    }

    @Test
    @DisplayName("Should find permissions by id set")
    void findByIdIn_Success() {
        // Arrange
        Set<Long> ids = Set.of(1L, 2L);
        PermissionEntity perm1 = new PermissionEntity();
        PermissionEntity perm2 = new PermissionEntity();
        when(jpaRepository.findByIdIn(ids)).thenReturn(List.of(perm1, perm2));

        // Act
        List<PermissionEntity> result = adapter.findByIdIn(ids);

        // Assert
        assertEquals(2, result.size());
        verify(jpaRepository).findByIdIn(ids);
    }
}
