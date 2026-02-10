package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.User;
import com.skydiveforecast.infrastructure.persistence.entity.UserEntity;
import com.skydiveforecast.infrastructure.persistence.mapper.UserEntityMapper;
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
class UserRepositoryAdapterTest {

    @Mock
    private UserJpaRepository jpaRepository;

    @Mock
    private UserEntityMapper mapper;

    @InjectMocks
    private UserRepositoryAdapter adapter;

    @Test
    @DisplayName("Should save user successfully")
    void save_WhenValidUser_SavesAndReturnsUser() {
        // Arrange
        User user = User.builder().id(1L).email("test@test.com").firstName("John").lastName("Doe").isActive(true).build();
        UserEntity entity = new UserEntity();
        when(mapper.toEntity(user)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(user);

        // Act
        User result = adapter.save(user);

        // Assert
        assertNotNull(result);
        assertEquals(user.email(), result.email());
        verify(jpaRepository).save(entity);
    }

    @Test
    @DisplayName("Should find user by id")
    void findById_WhenUserExists_ReturnsUser() {
        // Arrange
        UserEntity entity = new UserEntity();
        User user = User.builder().id(1L).email("test@test.com").firstName("John").lastName("Doe").isActive(true).build();
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(user);

        // Act
        Optional<User> result = adapter.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user.email(), result.get().email());
    }

    @Test
    @DisplayName("Should return empty when user not found by id")
    void findById_WhenUserNotExists_ReturnsEmpty() {
        // Arrange
        when(jpaRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = adapter.findById(999L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should find user by email")
    void findByEmail_WhenUserExists_ReturnsUser() {
        // Arrange
        UserEntity entity = new UserEntity();
        User user = User.builder().id(1L).email("test@test.com").firstName("John").lastName("Doe").isActive(true).build();
        when(jpaRepository.findByEmail("test@test.com")).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(user);

        // Act
        Optional<User> result = adapter.findByEmail("test@test.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("test@test.com", result.get().email());
    }

    @Test
    @DisplayName("Should find all users")
    void findAll_WhenUsersExist_ReturnsAllUsers() {
        // Arrange
        UserEntity entity = new UserEntity();
        User user = User.builder().id(1L).email("test@test.com").firstName("John").lastName("Doe").isActive(true).build();
        when(jpaRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomainList(any())).thenReturn(List.of(user));

        // Act
        List<User> result = adapter.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should find all users with roles")
    void findAllWithRoles_WhenUsersExist_ReturnsUsersWithRoles() {
        // Arrange
        UserEntity entity = new UserEntity();
        User user = User.builder().id(1L).email("test@test.com").firstName("John").lastName("Doe").isActive(true).build();
        when(jpaRepository.findAllWithRoles()).thenReturn(List.of(entity));
        when(mapper.toDomainList(any())).thenReturn(List.of(user));

        // Act
        List<User> result = adapter.findAllWithRoles();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should check if user exists by email")
    void existsByEmail_WhenUserExists_ReturnsTrue() {
        // Arrange
        when(jpaRepository.existsByEmail("test@test.com")).thenReturn(true);

        // Act
        boolean result = adapter.existsByEmail("test@test.com");

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Should delete user by id")
    void deleteById_WhenCalled_DeletesUser() {
        // Arrange
        doNothing().when(jpaRepository).deleteById(1L);

        // Act
        adapter.deleteById(1L);

        // Assert
        verify(jpaRepository).deleteById(1L);
    }
}
