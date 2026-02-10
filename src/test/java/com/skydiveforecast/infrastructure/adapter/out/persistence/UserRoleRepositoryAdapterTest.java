package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.UserRole;
import com.skydiveforecast.infrastructure.persistence.entity.RoleEntity;
import com.skydiveforecast.infrastructure.persistence.entity.UserRoleEntity;
import com.skydiveforecast.infrastructure.persistence.mapper.UserRoleEntityMapper;
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
class UserRoleRepositoryAdapterTest {

    @Mock
    private UserRoleJpaRepository jpaRepository;

    @Mock
    private UserRoleEntityMapper mapper;

    @InjectMocks
    private UserRoleRepositoryAdapter adapter;

    @Test
    @DisplayName("Should save user role successfully")
    void save_WhenValidUserRole_SavesAndReturnsUserRole() {
        // Arrange
        UserRole userRole = UserRole.builder().id(1L).userId(1L).roleId(2L).build();
        UserRoleEntity entity = new UserRoleEntity();
        when(mapper.toEntity(userRole)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(userRole);

        // Act
        UserRole result = adapter.save(userRole);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.userId());
        verify(jpaRepository).save(entity);
    }

    @Test
    @DisplayName("Should find user role by id")
    void findById_WhenUserRoleExists_ReturnsUserRole() {
        // Arrange
        UserRoleEntity entity = new UserRoleEntity();
        UserRole userRole = UserRole.builder().id(1L).userId(1L).roleId(2L).build();
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(userRole);

        // Act
        Optional<UserRole> result = adapter.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().userId());
    }

    @Test
    @DisplayName("Should find user roles by user id")
    void findByUserId_WhenUserRolesExist_ReturnsUserRoles() {
        // Arrange
        UserRoleEntity entity = new UserRoleEntity();
        UserRole userRole = UserRole.builder().id(1L).userId(1L).roleId(2L).build();
        when(jpaRepository.findByUserId(1L)).thenReturn(List.of(entity));
        when(mapper.toDomainList(any())).thenReturn(List.of(userRole));

        // Act
        List<UserRole> result = adapter.findByUserId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should find all user roles")
    void findAll_WhenUserRolesExist_ReturnsAllUserRoles() {
        // Arrange
        UserRoleEntity entity = new UserRoleEntity();
        UserRole userRole = UserRole.builder().id(1L).userId(1L).roleId(2L).build();
        when(jpaRepository.findAllWithRole()).thenReturn(List.of(entity));
        when(mapper.toDomainList(any())).thenReturn(List.of(userRole));

        // Act
        List<UserRole> result = adapter.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should find all user role entities")
    void findAllEntities_WhenUserRolesExist_ReturnsAllEntities() {
        // Arrange
        UserRoleEntity entity = new UserRoleEntity();
        when(jpaRepository.findAllWithRole()).thenReturn(List.of(entity));

        // Act
        List<UserRoleEntity> result = adapter.findAllEntities();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should find entities by user id")
    void findEntitiesByUserId_WhenUserRolesExist_ReturnsEntities() {
        // Arrange
        UserRoleEntity entity = new UserRoleEntity();
        when(jpaRepository.findByUserId(1L)).thenReturn(List.of(entity));

        // Act
        List<UserRoleEntity> result = adapter.findEntitiesByUserId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should save user role entity")
    void saveEntity_WhenValidEntity_SavesAndReturnsEntity() {
        // Arrange
        UserRoleEntity entity = new UserRoleEntity();
        when(jpaRepository.save(entity)).thenReturn(entity);

        // Act
        UserRoleEntity result = adapter.saveEntity(entity);

        // Assert
        assertNotNull(result);
        verify(jpaRepository).save(entity);
    }

    @Test
    @DisplayName("Should find role names by user id")
    void findRoleNamesByUserId_WhenUserHasRoles_ReturnsRoleNames() {
        // Arrange
        UserRoleEntity entity = new UserRoleEntity();
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ADMIN");
        entity.setRole(roleEntity);
        when(jpaRepository.findByUserId(1L)).thenReturn(List.of(entity));

        // Act
        Set<String> result = adapter.findRoleNamesByUserId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains("ADMIN"));
    }

    @Test
    @DisplayName("Should delete user role by id")
    void deleteById_WhenCalled_DeletesUserRole() {
        // Arrange
        doNothing().when(jpaRepository).deleteById(1L);

        // Act
        adapter.deleteById(1L);

        // Assert
        verify(jpaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should delete user role by user id and role id")
    void deleteByUserIdAndRoleId_WhenCalled_DeletesUserRole() {
        // Arrange
        doNothing().when(jpaRepository).deleteByUserIdAndRoleId(1L, 2L);

        // Act
        adapter.deleteByUserIdAndRoleId(1L, 2L);

        // Assert
        verify(jpaRepository).deleteByUserIdAndRoleId(1L, 2L);
    }
}
