package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.infrastructure.persistance.entity.UserEntity;
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
    @DisplayName("Should map CreateUserDto to UserEntity")
    void toEntity_Success() {
        // Arrange
        CreateUserDto dto = new CreateUserDto();
        dto.setEmail("test@example.com");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPassword("Password123!");

        // Act
        UserEntity entity = mapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals("test@example.com", entity.getEmail());
        assertEquals("John", entity.getFirstName());
        assertEquals("Doe", entity.getLastName());
        assertNull(entity.getId());
        assertNull(entity.getPasswordHash());
    }

    @Test
    @DisplayName("Should handle null CreateUserDto")
    void toEntity_NullDto() {
        // Arrange
        CreateUserDto dto = null;

        // Act
        UserEntity entity = mapper.toEntity(dto);

        // Assert
        assertNull(entity);
    }

    @Test
    @DisplayName("Should map CreateUserDto with null fields")
    void toEntity_NullFields() {
        // Arrange
        CreateUserDto dto = new CreateUserDto();
        dto.setEmail(null);
        dto.setFirstName(null);
        dto.setLastName(null);

        // Act
        UserEntity entity = mapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertNull(entity.getEmail());
        assertNull(entity.getFirstName());
        assertNull(entity.getLastName());
    }
}
