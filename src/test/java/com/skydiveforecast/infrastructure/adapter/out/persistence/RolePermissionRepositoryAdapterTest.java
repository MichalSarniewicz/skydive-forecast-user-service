package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.RolePermission;
import com.skydiveforecast.infrastructure.persistence.entity.RolePermissionEntity;
import com.skydiveforecast.infrastructure.persistence.mapper.RolePermissionEntityMapper;
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
class RolePermissionRepositoryAdapterTest {

    @Mock
    private RolePermissionJpaRepository jpaRepository;

    @Mock
    private RolePermissionEntityMapper mapper;

    @InjectMocks
    private RolePermissionRepositoryAdapter adapter;

    @Test
    @DisplayName("Should save role permission successfully")
    void save_WhenValidRolePermission_SavesAndReturnsRolePermission() {
        // Arrange
        RolePermission rolePermission = RolePermission.builder().id(1L).roleId(1L).permissionId(2L).build();
        RolePermissionEntity entity = new RolePermissionEntity();
        when(mapper.toEntity(rolePermission)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(rolePermission);

        // Act
        RolePermission result = adapter.save(rolePermission);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.roleId());
        verify(jpaRepository).save(entity);
    }

    @Test
    @DisplayName("Should find role permission by id")
    void findById_WhenRolePermissionExists_ReturnsRolePermission() {
        // Arrange
        RolePermissionEntity entity = new RolePermissionEntity();
        RolePermission rolePermission = RolePermission.builder().id(1L).roleId(1L).permissionId(2L).build();
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(rolePermission);

        // Act
        Optional<RolePermission> result = adapter.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().roleId());
    }

    @Test
    @DisplayName("Should find role permissions by role id")
    void findByRoleId_WhenRolePermissionsExist_ReturnsRolePermissions() {
        // Arrange
        RolePermissionEntity entity = new RolePermissionEntity();
        RolePermission rolePermission = RolePermission.builder().id(1L).roleId(1L).permissionId(2L).build();
        when(jpaRepository.findByRoleId(1L)).thenReturn(List.of(entity));
        when(mapper.toDomainList(any())).thenReturn(List.of(rolePermission));

        // Act
        List<RolePermission> result = adapter.findByRoleId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should find role permissions by permission id")
    void findByPermissionId_WhenRolePermissionsExist_ReturnsRolePermissions() {
        // Arrange
        RolePermissionEntity entity = new RolePermissionEntity();
        RolePermission rolePermission = RolePermission.builder().id(1L).roleId(1L).permissionId(2L).build();
        when(jpaRepository.findByPermissionId(2L)).thenReturn(List.of(entity));
        when(mapper.toDomainList(any())).thenReturn(List.of(rolePermission));

        // Act
        List<RolePermission> result = adapter.findByPermissionId(2L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should find all role permissions")
    void findAll_WhenRolePermissionsExist_ReturnsAllRolePermissions() {
        // Arrange
        RolePermissionEntity entity = new RolePermissionEntity();
        RolePermission rolePermission = RolePermission.builder().id(1L).roleId(1L).permissionId(2L).build();
        when(jpaRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomainList(any())).thenReturn(List.of(rolePermission));

        // Act
        List<RolePermission> result = adapter.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should delete role permission by id")
    void deleteById_WhenCalled_DeletesRolePermission() {
        // Arrange
        doNothing().when(jpaRepository).deleteById(1L);

        // Act
        adapter.deleteById(1L);

        // Assert
        verify(jpaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should delete role permission by role id and permission id")
    void deleteByRoleIdAndPermissionId_WhenCalled_DeletesRolePermission() {
        // Arrange
        doNothing().when(jpaRepository).deleteByRoleIdAndPermissionId(1L, 2L);

        // Act
        adapter.deleteByRoleIdAndPermissionId(1L, 2L);

        // Assert
        verify(jpaRepository).deleteByRoleIdAndPermissionId(1L, 2L);
    }

    @Test
    @DisplayName("Should delete all role permissions by role id")
    void deleteAllByRoleId_WhenCalled_DeletesAllRolePermissions() {
        // Arrange
        doNothing().when(jpaRepository).deleteAllByRoleId(1L);

        // Act
        adapter.deleteAllByRoleId(1L);

        // Assert
        verify(jpaRepository).deleteAllByRoleId(1L);
    }

    @Test
    @DisplayName("Should delete all role permissions by permission id")
    void deleteAllByPermissionId_WhenCalled_DeletesAllRolePermissions() {
        // Arrange
        doNothing().when(jpaRepository).deleteAllByPermissionId(2L);

        // Act
        adapter.deleteAllByPermissionId(2L);

        // Assert
        verify(jpaRepository).deleteAllByPermissionId(2L);
    }

    @Test
    @DisplayName("Should find permission codes by user id")
    void findPermissionCodesByUserId_WhenPermissionsExist_ReturnsPermissionCodes() {
        // Arrange
        when(jpaRepository.findPermissionCodesByUserId(1L)).thenReturn(Set.of("USER_READ", "USER_WRITE"));

        // Act
        Set<String> result = adapter.findPermissionCodesByUserId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("USER_READ"));
    }

    @Test
    @DisplayName("Should find permission codes by role id")
    void findPermissionCodesByRoleId_WhenPermissionsExist_ReturnsPermissionCodes() {
        // Arrange
        when(jpaRepository.findPermissionCodesByRoleId(1L)).thenReturn(Set.of("USER_READ"));

        // Act
        Set<String> result = adapter.findPermissionCodesByRoleId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains("USER_READ"));
    }

    @Test
    @DisplayName("Should check if role permission exists")
    void existsByRoleIdAndPermissionId_WhenExists_ReturnsTrue() {
        // Arrange
        when(jpaRepository.existsByRoleIdAndPermissionId(1L, 2L)).thenReturn(true);

        // Act
        boolean result = adapter.existsByRoleIdAndPermissionId(1L, 2L);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Should save all role permissions")
    void saveAll_WhenValidRolePermissions_SavesAndReturnsRolePermissions() {
        // Arrange
        RolePermission rolePermission = RolePermission.builder().id(1L).roleId(1L).permissionId(2L).build();
        RolePermissionEntity entity = new RolePermissionEntity();
        when(mapper.toEntity(any())).thenReturn(entity);
        when(jpaRepository.saveAll(any())).thenReturn(List.of(entity));
        when(mapper.toDomainList(any())).thenReturn(List.of(rolePermission));

        // Act
        List<RolePermission> result = adapter.saveAll(List.of(rolePermission));

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should delete role permission entity")
    void delete_WhenCalled_DeletesRolePermission() {
        // Arrange
        RolePermission rolePermission = RolePermission.builder().id(1L).roleId(1L).permissionId(2L).build();
        RolePermissionEntity entity = new RolePermissionEntity();
        when(mapper.toEntity(rolePermission)).thenReturn(entity);
        doNothing().when(jpaRepository).delete(entity);

        // Act
        adapter.delete(rolePermission);

        // Assert
        verify(jpaRepository).delete(entity);
    }
}
