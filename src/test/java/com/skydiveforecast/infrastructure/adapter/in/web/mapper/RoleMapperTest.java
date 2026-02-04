package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.infrastructure.persistence.entity.RoleEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RoleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleMapperTest {

    private RoleMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(RoleMapper.class);
    }

    @Test
    @DisplayName("Should map RoleEntity to RoleDto")
    void toDto_Success() {
        // Arrange
        RoleEntity entity = new RoleEntity();
        entity.setId(1L);
        entity.setName("ADMIN");

        // Act
        RoleDto dto = mapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("ADMIN", dto.getName());
    }

    @Test
    @DisplayName("Should handle null RoleEntity")
    void toDto_NullEntity() {
        // Arrange
        RoleEntity entity = null;

        // Act
        RoleDto dto = mapper.toDto(entity);

        // Assert
        assertNull(dto);
    }

    @Test
    @DisplayName("Should map RoleDto to RoleEntity")
    void toEntity_Success() {
        // Arrange
        RoleDto dto = new RoleDto();
        dto.setId(1L);
        dto.setName("USER");

        // Act
        RoleEntity entity = mapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("USER", entity.getName());
        assertNull(entity.getCreatedAt());
        assertNull(entity.getUpdatedAt());
    }

    @Test
    @DisplayName("Should handle null RoleDto")
    void toEntity_NullDto() {
        // Arrange
        RoleDto dto = null;

        // Act
        RoleEntity entity = mapper.toEntity(dto);

        // Assert
        assertNull(entity);
    }

    @Test
    @DisplayName("Should map list of RoleEntity to list of RoleDto")
    void toDtoList_Success() {
        // Arrange
        RoleEntity entity1 = new RoleEntity();
        entity1.setId(1L);
        entity1.setName("ADMIN");

        RoleEntity entity2 = new RoleEntity();
        entity2.setId(2L);
        entity2.setName("USER");

        List<RoleEntity> entities = List.of(entity1, entity2);

        // Act
        List<RoleDto> dtos = mapper.toDtoList(entities);

        // Assert
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals("ADMIN", dtos.get(0).getName());
        assertEquals("USER", dtos.get(1).getName());
    }

    @Test
    @DisplayName("Should handle empty list")
    void toDtoList_EmptyList() {
        // Arrange
        List<RoleEntity> entities = List.of();

        // Act
        List<RoleDto> dtos = mapper.toDtoList(entities);

        // Assert
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    @Test
    @DisplayName("Should map RoleDto with null fields")
    void toEntity_NullFields() {
        // Arrange
        RoleDto dto = new RoleDto();
        dto.setId(null);
        dto.setName(null);

        // Act
        RoleEntity entity = mapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getName());
    }
}
