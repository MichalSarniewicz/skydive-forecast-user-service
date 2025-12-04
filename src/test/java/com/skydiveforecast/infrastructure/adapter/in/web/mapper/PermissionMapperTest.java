package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.domain.model.PermissionEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreatePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UpdatePermissionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PermissionMapperTest {

    private PermissionMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(PermissionMapper.class);
    }

    @Test
    @DisplayName("Should map PermissionEntity to PermissionDto")
    void toDto_Success() {
        // Arrange
        PermissionEntity entity = new PermissionEntity();
        entity.setId(1L);
        entity.setCode("READ_USER");
        entity.setDescription("Permission to read users");

        // Act
        PermissionDto dto = mapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("READ_USER", dto.getCode());
        assertEquals("Permission to read users", dto.getDescription());
    }

    @Test
    @DisplayName("Should handle null PermissionEntity")
    void toDto_NullEntity() {
        // Arrange
        PermissionEntity entity = null;

        // Act
        PermissionDto dto = mapper.toDto(entity);

        // Assert
        assertNull(dto);
    }

    @Test
    @DisplayName("Should map list of PermissionEntity to list of PermissionDto")
    void toDtoList_Success() {
        // Arrange
        PermissionEntity entity1 = new PermissionEntity();
        entity1.setId(1L);
        entity1.setCode("READ_USER");

        PermissionEntity entity2 = new PermissionEntity();
        entity2.setId(2L);
        entity2.setCode("WRITE_USER");

        List<PermissionEntity> entities = List.of(entity1, entity2);

        // Act
        List<PermissionDto> dtos = mapper.toDtoList(entities);

        // Assert
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals("READ_USER", dtos.get(0).getCode());
        assertEquals("WRITE_USER", dtos.get(1).getCode());
    }

    @Test
    @DisplayName("Should map CreatePermissionDto to PermissionEntity")
    void toEntity_Success() {
        // Arrange
        CreatePermissionDto dto = new CreatePermissionDto();
        dto.setCode("DELETE_USER");
        dto.setDescription("Permission to delete users");

        // Act
        PermissionEntity entity = mapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals("DELETE_USER", entity.getCode());
        assertEquals("Permission to delete users", entity.getDescription());
        assertNull(entity.getId());
        assertNull(entity.getCreatedAt());
        assertNull(entity.getUpdatedAt());
    }

    @Test
    @DisplayName("Should handle null CreatePermissionDto")
    void toEntity_NullDto() {
        // Arrange
        CreatePermissionDto dto = null;

        // Act
        PermissionEntity entity = mapper.toEntity(dto);

        // Assert
        assertNull(entity);
    }

    @Test
    @DisplayName("Should update entity from UpdatePermissionDto")
    void updateEntityFromDto_Success() {
        // Arrange
        UpdatePermissionDto dto = new UpdatePermissionDto();
        dto.setCode("UPDATED_PERMISSION");
        dto.setDescription("Updated description");

        PermissionEntity entity = new PermissionEntity();
        entity.setId(1L);
        entity.setCode("OLD_PERMISSION");
        entity.setDescription("Old description");

        // Act
        mapper.updateEntityFromDto(dto, entity);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("UPDATED_PERMISSION", entity.getCode());
        assertEquals("Updated description", entity.getDescription());
        assertNull(entity.getCreatedAt());
        assertNull(entity.getUpdatedAt());
    }

    @Test
    @DisplayName("Should handle null fields in UpdatePermissionDto")
    void updateEntityFromDto_NullFields() {
        // Arrange
        UpdatePermissionDto dto = new UpdatePermissionDto();
        dto.setCode(null);
        dto.setDescription(null);

        PermissionEntity entity = new PermissionEntity();
        entity.setId(1L);
        entity.setCode("PERMISSION");
        entity.setDescription("Description");

        // Act
        mapper.updateEntityFromDto(dto, entity);

        // Assert
        assertEquals(1L, entity.getId());
        assertNull(entity.getCode());
        assertNull(entity.getDescription());
    }

    @Test
    @DisplayName("Should handle empty list")
    void toDtoList_EmptyList() {
        // Arrange
        List<PermissionEntity> entities = List.of();

        // Act
        List<PermissionDto> dtos = mapper.toDtoList(entities);

        // Assert
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }
}
