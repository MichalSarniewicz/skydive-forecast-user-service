package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.domain.model.User;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreateUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserMapperTest {

    private CreateUserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(CreateUserMapper.class);
    }

    @Test
    @DisplayName("Should map CreateUserDto to User domain")
    void toDomain_Success() {
        // Arrange
        CreateUserDto dto = new CreateUserDto();
        dto.setEmail("test@example.com");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPassword("Password123!");

        // Act
        User user = mapper.toDomain(dto);

        // Assert
        assertNotNull(user);
        assertEquals("test@example.com", user.email());
        assertEquals("John", user.firstName());
        assertEquals("Doe", user.lastName());
        assertNull(user.id());
        assertNull(user.passwordHash());
        assertTrue(user.isActive());
    }

    @Test
    @DisplayName("Should handle null CreateUserDto")
    void toDomain_NullDto() {
        // Arrange
        CreateUserDto dto = null;

        // Act
        User user = mapper.toDomain(dto);

        // Assert
        assertNull(user);
    }

    @Test
    @DisplayName("Should map CreateUserDto with null fields")
    void toDomain_NullFields() {
        // Arrange
        CreateUserDto dto = new CreateUserDto();
        dto.setEmail(null);
        dto.setFirstName(null);
        dto.setLastName(null);

        // Act
        User user = mapper.toDomain(dto);

        // Assert
        assertNotNull(user);
        assertNull(user.email());
        assertNull(user.firstName());
        assertNull(user.lastName());
        assertTrue(user.isActive());
    }
}
