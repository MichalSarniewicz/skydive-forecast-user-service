package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.infrastructure.persistance.entity.RoleEntity;
import com.skydiveforecast.infrastructure.persistance.entity.UserRoleEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserRoleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleMapperTest {

    private UserRoleMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(UserRoleMapper.class);
    }

    @Test
    @DisplayName("Should map UserRoleEntity to UserRoleDto")
    void toDto_Success() {
        // Arrange
        RoleEntity role = new RoleEntity();
        role.setId(1L);
        role.setName("ADMIN");

        UserRoleEntity entity = new UserRoleEntity();
        entity.setId(10L);
        entity.setRole(role);

        // Act
        UserRoleDto dto = mapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("ADMIN", dto.getName());
    }

    @Test
    @DisplayName("Should handle null UserRoleEntity")
    void toDto_NullEntity() {
        // Arrange
        UserRoleEntity entity = null;

        // Act
        UserRoleDto dto = mapper.toDto(entity);

        // Assert
        assertNull(dto);
    }

    @Test
    @DisplayName("Should map list of UserRoleEntity to list of UserRoleDto")
    void toDtoList_Success() {
        // Arrange
        RoleEntity role1 = new RoleEntity();
        role1.setId(1L);
        role1.setName("ADMIN");

        RoleEntity role2 = new RoleEntity();
        role2.setId(2L);
        role2.setName("USER");

        UserRoleEntity entity1 = new UserRoleEntity();
        entity1.setId(10L);
        entity1.setRole(role1);

        UserRoleEntity entity2 = new UserRoleEntity();
        entity2.setId(20L);
        entity2.setRole(role2);

        List<UserRoleEntity> entities = List.of(entity1, entity2);

        // Act
        List<UserRoleDto> dtos = mapper.toDtoList(entities);

        // Assert
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(1L, dtos.get(0).getId());
        assertEquals("ADMIN", dtos.get(0).getName());
        assertEquals(2L, dtos.get(1).getId());
        assertEquals("USER", dtos.get(1).getName());
    }

    @Test
    @DisplayName("Should handle empty list")
    void toDtoList_EmptyList() {
        // Arrange
        List<UserRoleEntity> entities = List.of();

        // Act
        List<UserRoleDto> dtos = mapper.toDtoList(entities);

        // Assert
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    @Test
    @DisplayName("Should handle UserRoleEntity with null role")
    void toDto_NullRole() {
        // Arrange
        UserRoleEntity entity = new UserRoleEntity();
        entity.setId(10L);
        entity.setRole(null);

        // Act
        UserRoleDto dto = mapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getName());
    }
}
