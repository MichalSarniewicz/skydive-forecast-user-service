package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.domain.model.UserEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UpdateUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UpdateUserMapperTest {

    private UpdateUserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(UpdateUserMapper.class);
    }

    @Test
    @DisplayName("Should update entity from UpdateUserDto")
    void updateEntityFromDto_Success() {
        // Arrange
        UpdateUserDto dto = new UpdateUserDto();
        dto.setFirstName("Jane");
        dto.setLastName("Smith");

        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setEmail("original@example.com");
        entity.setFirstName("John");
        entity.setLastName("Doe");
        entity.setPasswordHash("hashedPassword");
        entity.setActive(true);

        // Act
        mapper.updateEntityFromDto(dto, entity);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("original@example.com", entity.getEmail());
        assertEquals("Jane", entity.getFirstName());
        assertEquals("Smith", entity.getLastName());
        assertEquals("hashedPassword", entity.getPasswordHash());
        assertTrue(entity.isActive());
    }

    @Test
    @DisplayName("Should handle null fields in UpdateUserDto")
    void updateEntityFromDto_NullFields() {
        // Arrange
        UpdateUserDto dto = new UpdateUserDto();
        dto.setFirstName(null);
        dto.setLastName(null);

        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setEmail("test@example.com");
        entity.setFirstName("John");
        entity.setLastName("Doe");

        // Act
        mapper.updateEntityFromDto(dto, entity);

        // Assert
        assertEquals(1L, entity.getId());
        assertNull(entity.getFirstName());
        assertNull(entity.getLastName());
    }

    @Test
    @DisplayName("Should preserve ignored fields when updating")
    void updateEntityFromDto_PreserveIgnoredFields() {
        // Arrange
        UpdateUserDto dto = new UpdateUserDto();
        dto.setFirstName("NewFirstName");
        dto.setLastName("NewLastName");

        UserEntity entity = new UserEntity();
        entity.setId(999L);
        entity.setEmail("preserve@example.com");
        entity.setPasswordHash("preservedHash");
        entity.setActive(false);

        // Act
        mapper.updateEntityFromDto(dto, entity);

        // Assert
        assertEquals(999L, entity.getId());
        assertEquals("preserve@example.com", entity.getEmail());
        assertEquals("preservedHash", entity.getPasswordHash());
        assertFalse(entity.isActive());
        assertEquals("NewFirstName", entity.getFirstName());
        assertEquals("NewLastName", entity.getLastName());
    }
}
