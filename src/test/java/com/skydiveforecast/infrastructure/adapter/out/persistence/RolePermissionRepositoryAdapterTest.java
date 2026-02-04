package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.infrastructure.persistence.entity.RolePermissionEntity;
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
class RolePermissionRepositoryAdapterTest {

    @Mock
    private RolePermissionJpaRepository jpaRepository;

    @InjectMocks
    private RolePermissionRepositoryAdapter adapter;

    @Test
    @DisplayName("Should save role permission successfully")
    void save_Success() {
        // Arrange
        RolePermissionEntity rolePermission = new RolePermissionEntity();
        rolePermission.setId(1L);
        when(jpaRepository.save(rolePermission)).thenReturn(rolePermission);

        // Act
        RolePermissionEntity result = adapter.save(rolePermission);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(jpaRepository).save(rolePermission);
    }

    @Test
    @DisplayName("Should find role permission by id")
    void findById_Success() {
        // Arrange
        RolePermissionEntity rolePermission = new RolePermissionEntity();
        rolePermission.setId(1L);
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(rolePermission));

        // Act
        Optional<RolePermissionEntity> result = adapter.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(jpaRepository).findById(1L);
    }

    @Test
    @DisplayName("Should find role permissions by role id")
    void findByRoleId_Success() {
        // Arrange
        RolePermissionEntity rp1 = new RolePermissionEntity();
        RolePermissionEntity rp2 = new RolePermissionEntity();
        when(jpaRepository.findByRoleId(1L)).thenReturn(List.of(rp1, rp2));

        // Act
        List<RolePermissionEntity> result = adapter.findByRoleId(1L);

        // Assert
        assertEquals(2, result.size());
        verify(jpaRepository).findByRoleId(1L);
    }

    @Test
    @DisplayName("Should find role permissions by permission id")
    void findByPermissionId_Success() {
        // Arrange
        RolePermissionEntity rp1 = new RolePermissionEntity();
        RolePermissionEntity rp2 = new RolePermissionEntity();
        when(jpaRepository.findByPermissionId(1L)).thenReturn(List.of(rp1, rp2));

        // Act
        List<RolePermissionEntity> result = adapter.findByPermissionId(1L);

        // Assert
        assertEquals(2, result.size());
        verify(jpaRepository).findByPermissionId(1L);
    }

    @Test
    @DisplayName("Should find all role permissions")
    void findAll_Success() {
        // Arrange
        RolePermissionEntity rp1 = new RolePermissionEntity();
        RolePermissionEntity rp2 = new RolePermissionEntity();
        when(jpaRepository.findAll()).thenReturn(List.of(rp1, rp2));

        // Act
        List<RolePermissionEntity> result = adapter.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(jpaRepository).findAll();
    }

    @Test
    @DisplayName("Should delete role permission by id")
    void deleteById_Success() {
        // Arrange
        Long id = 1L;

        // Act
        adapter.deleteById(id);

        // Assert
        verify(jpaRepository).deleteById(id);
    }

    @Test
    @DisplayName("Should delete by role id and permission id")
    void deleteByRoleIdAndPermissionId_Success() {
        // Arrange
        Long roleId = 1L;
        Long permissionId = 2L;

        // Act
        adapter.deleteByRoleIdAndPermissionId(roleId, permissionId);

        // Assert
        verify(jpaRepository).deleteByRoleIdAndPermissionId(roleId, permissionId);
    }

    @Test
    @DisplayName("Should delete all by role id")
    void deleteAllByRoleId_Success() {
        // Arrange
        Long roleId = 1L;

        // Act
        adapter.deleteAllByRoleId(roleId);

        // Assert
        verify(jpaRepository).deleteAllByRoleId(roleId);
    }

    @Test
    @DisplayName("Should delete all by permission id")
    void deleteAllByPermissionId_Success() {
        // Arrange
        Long permissionId = 1L;

        // Act
        adapter.deleteAllByPermissionId(permissionId);

        // Assert
        verify(jpaRepository).deleteAllByPermissionId(permissionId);
    }

    @Test
    @DisplayName("Should find permission codes by user id")
    void findPermissionCodesByUserId_Success() {
        // Arrange
        Set<String> codes = Set.of("USER_VIEW", "USER_CREATE");
        when(jpaRepository.findPermissionCodesByUserId(1L)).thenReturn(codes);

        // Act
        Set<String> result = adapter.findPermissionCodesByUserId(1L);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains("USER_VIEW"));
        verify(jpaRepository).findPermissionCodesByUserId(1L);
    }

    @Test
    @DisplayName("Should find permission codes by role id")
    void findPermissionCodesByRoleId_Success() {
        // Arrange
        Set<String> codes = Set.of("USER_VIEW", "USER_CREATE");
        when(jpaRepository.findPermissionCodesByRoleId(1L)).thenReturn(codes);

        // Act
        Set<String> result = adapter.findPermissionCodesByRoleId(1L);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains("USER_VIEW"));
        verify(jpaRepository).findPermissionCodesByRoleId(1L);
    }

    @Test
    @DisplayName("Should check if exists by role id and permission id")
    void existsByRoleIdAndPermissionId_Success() {
        // Arrange
        when(jpaRepository.existsByRoleIdAndPermissionId(1L, 2L)).thenReturn(true);

        // Act
        boolean result = adapter.existsByRoleIdAndPermissionId(1L, 2L);

        // Assert
        assertTrue(result);
        verify(jpaRepository).existsByRoleIdAndPermissionId(1L, 2L);
    }

    @Test
    @DisplayName("Should save all role permissions")
    void saveAll_Success() {
        // Arrange
        RolePermissionEntity rp1 = new RolePermissionEntity();
        RolePermissionEntity rp2 = new RolePermissionEntity();
        List<RolePermissionEntity> entities = List.of(rp1, rp2);
        when(jpaRepository.saveAll(entities)).thenReturn(entities);

        // Act
        List<RolePermissionEntity> result = adapter.saveAll(entities);

        // Assert
        assertEquals(2, result.size());
        verify(jpaRepository).saveAll(entities);
    }

    @Test
    @DisplayName("Should delete role permission entity")
    void delete_Success() {
        // Arrange
        RolePermissionEntity entity = new RolePermissionEntity();

        // Act
        adapter.delete(entity);

        // Assert
        verify(jpaRepository).delete(entity);
    }
}
