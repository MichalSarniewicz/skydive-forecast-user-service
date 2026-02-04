package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.infrastructure.persistance.entity.UserEntity;
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
class UserRepositoryAdapterTest {

    @Mock
    private UserJpaRepository jpaRepository;

    @InjectMocks
    private UserRepositoryAdapter adapter;

    @Test
    @DisplayName("Should save user successfully")
    void save_Success() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setId(1L);
        when(jpaRepository.save(user)).thenReturn(user);

        // Act
        UserEntity result = adapter.save(user);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(jpaRepository).save(user);
    }

    @Test
    @DisplayName("Should find user by id")
    void findById_Success() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setId(1L);
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        Optional<UserEntity> result = adapter.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(jpaRepository).findById(1L);
    }

    @Test
    @DisplayName("Should find user by email")
    void findByEmail_Success() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        when(jpaRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        // Act
        Optional<UserEntity> result = adapter.findByEmail("test@example.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
        verify(jpaRepository).findByEmail("test@example.com");
    }

    @Test
    @DisplayName("Should find all users")
    void findAll_Success() {
        // Arrange
        UserEntity user1 = new UserEntity();
        UserEntity user2 = new UserEntity();
        when(jpaRepository.findAll()).thenReturn(List.of(user1, user2));

        // Act
        List<UserEntity> result = adapter.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(jpaRepository).findAll();
    }

    @Test
    @DisplayName("Should check if email exists")
    void existsByEmail_Success() {
        // Arrange
        when(jpaRepository.existsByEmail("test@example.com")).thenReturn(true);

        // Act
        boolean result = adapter.existsByEmail("test@example.com");

        // Assert
        assertTrue(result);
        verify(jpaRepository).existsByEmail("test@example.com");
    }

    @Test
    @DisplayName("Should delete user by id")
    void deleteById_Success() {
        // Arrange
        Long userId = 1L;

        // Act
        adapter.deleteById(userId);

        // Assert
        verify(jpaRepository).deleteById(userId);
    }
}
