package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.UserRoleEntity;
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
class UserRoleRepositoryAdapterTest {

    @Mock
    private UserRoleJpaRepository jpaRepository;

    @InjectMocks
    private UserRoleRepositoryAdapter adapter;

    @Test
    @DisplayName("Should save user role successfully")
    void save_Success() {
        // Arrange
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setId(1L);
        when(jpaRepository.save(userRole)).thenReturn(userRole);

        // Act
        UserRoleEntity result = adapter.save(userRole);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(jpaRepository).save(userRole);
    }

    @Test
    @DisplayName("Should find user role by id")
    void findById_Success() {
        // Arrange
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setId(1L);
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(userRole));

        // Act
        Optional<UserRoleEntity> result = adapter.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(jpaRepository).findById(1L);
    }

    @Test
    @DisplayName("Should find user roles by user id")
    void findByUserId_Success() {
        // Arrange
        UserRoleEntity userRole1 = new UserRoleEntity();
        UserRoleEntity userRole2 = new UserRoleEntity();
        when(jpaRepository.findByUserId(1L)).thenReturn(List.of(userRole1, userRole2));

        // Act
        List<UserRoleEntity> result = adapter.findByUserId(1L);

        // Assert
        assertEquals(2, result.size());
        verify(jpaRepository).findByUserId(1L);
    }

    @Test
    @DisplayName("Should find all user roles")
    void findAll_Success() {
        // Arrange
        UserRoleEntity userRole1 = new UserRoleEntity();
        UserRoleEntity userRole2 = new UserRoleEntity();
        when(jpaRepository.findAll()).thenReturn(List.of(userRole1, userRole2));

        // Act
        List<UserRoleEntity> result = adapter.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(jpaRepository).findAll();
    }

    @Test
    @DisplayName("Should delete user role by id")
    void deleteById_Success() {
        // Arrange
        Long userRoleId = 1L;

        // Act
        adapter.deleteById(userRoleId);

        // Assert
        verify(jpaRepository).deleteById(userRoleId);
    }

    @Test
    @DisplayName("Should delete user role by user id and role id")
    void deleteByUserIdAndRoleId_Success() {
        // Arrange
        Long userId = 1L;
        Long roleId = 2L;

        // Act
        adapter.deleteByUserIdAndRoleId(userId, roleId);

        // Assert
        verify(jpaRepository).deleteByUserIdAndRoleId(userId, roleId);
    }
}
