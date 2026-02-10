package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.domain.model.User;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    @DisplayName("Should map User domain to UserDto")
    void toDto_Success() {
        // Arrange
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123456789")
                .passwordHash("hash")
                .isActive(true)
                .build();

        // Act
        UserDto dto = mapper.toDto(user);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("123456789", dto.getPhoneNumber());
        assertEquals(user.isActive(), dto.isActive());
    }

    @Test
    @DisplayName("Should handle null User")
    void toDto_NullEntity() {
        // Arrange
        User user = null;

        // Act
        UserDto dto = mapper.toDto(user);

        // Assert
        assertNull(dto);
    }

    @Test
    @DisplayName("Should map User with minimal fields")
    void toDto_MinimalFields() {
        // Arrange
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .isActive(false)
                .build();

        // Act
        UserDto dto = mapper.toDto(user);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertFalse(dto.isActive());
    }

    @Test
    @DisplayName("Should map list of User to list of UserDto")
    void toDtoList_Success() {
        // Arrange
        User user1 = User.builder().id(1L).email("test1@example.com").firstName("John").lastName("Doe").isActive(true).build();
        User user2 = User.builder().id(2L).email("test2@example.com").firstName("Jane").lastName("Doe").isActive(true).build();

        List<User> users = List.of(user1, user2);

        // Act
        List<UserDto> dtos = mapper.toDtoList(users);

        // Assert
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(1L, dtos.get(0).getId());
        assertEquals(2L, dtos.get(1).getId());
    }

    @Test
    @DisplayName("Should handle empty list")
    void toDtoList_EmptyList() {
        // Arrange
        List<User> users = List.of();

        // Act
        List<UserDto> dtos = mapper.toDtoList(users);

        // Assert
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }
}
