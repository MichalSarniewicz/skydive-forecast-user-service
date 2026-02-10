package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.UpdateUserDto;
import com.skydiveforecast.infrastructure.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
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
    void updateEntityFromDto_ShouldUpdateEntityFields() {
        // Arrange
        UpdateUserDto dto = new UpdateUserDto();
        dto.setFirstName("Jane");
        dto.setLastName("Smith");
        dto.setPhoneNumber("987654321");

        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setEmail("test@test.com");
        entity.setFirstName("John");
        entity.setLastName("Doe");
        entity.setPhoneNumber("123456789");

        // Act
        mapper.updateEntityFromDto(dto, entity);

        // Assert
        assertEquals("Jane", entity.getFirstName());
        assertEquals("Smith", entity.getLastName());
        assertEquals("987654321", entity.getPhoneNumber());
        assertEquals(1L, entity.getId());
        assertEquals("test@test.com", entity.getEmail());
    }

    @Test
    void updateEntityFromDto_WithNullDto_ShouldNotUpdateEntity() {
        // Arrange
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setFirstName("John");

        // Act
        mapper.updateEntityFromDto(null, entity);

        // Assert
        assertEquals("John", entity.getFirstName());
    }
}
