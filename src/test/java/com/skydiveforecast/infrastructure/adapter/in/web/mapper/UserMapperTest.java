package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.infrastructure.persistence.entity.RoleEntity;
import com.skydiveforecast.infrastructure.persistence.entity.UserEntity;
import com.skydiveforecast.infrastructure.persistence.entity.UserRoleEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    @DisplayName("Should map UserEntity to UserDto")
    void toDto_Success() {
        // Arrange
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setEmail("test@example.com");
        entity.setFirstName("John");
        entity.setLastName("Doe");
        entity.setActive(true);

        RoleEntity role = new RoleEntity();
        role.setId(1L);
        role.setName("USER");

        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setRole(role);

        Set<UserRoleEntity> userRoles = new HashSet<>();
        userRoles.add(userRole);
        entity.setRoles(userRoles);

        // Act
        UserDto dto = mapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertTrue(dto.isActive());
        assertNotNull(dto.getRoles());
        assertEquals(1, dto.getRoles().size());
    }

    @Test
    @DisplayName("Should handle null UserEntity")
    void toDto_NullEntity() {
        // Arrange
        UserEntity entity = null;

        // Act
        UserDto dto = mapper.toDto(entity);

        // Assert
        assertNull(dto);
    }

    @Test
    @DisplayName("Should map UserEntity with null roles")
    void toDto_NullRoles() {
        // Arrange
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setEmail("test@example.com");
        entity.setFirstName("John");
        entity.setLastName("Doe");
        entity.setRoles(null);

        // Act
        UserDto dto = mapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertNull(dto.getRoles());
    }

    @Test
    @DisplayName("Should map list of UserEntity to list of UserDto")
    void toDtoList_Success() {
        // Arrange
        UserEntity entity1 = new UserEntity();
        entity1.setId(1L);
        entity1.setEmail("test1@example.com");
        entity1.setFirstName("John");

        UserEntity entity2 = new UserEntity();
        entity2.setId(2L);
        entity2.setEmail("test2@example.com");
        entity2.setFirstName("Jane");

        List<UserEntity> entities = List.of(entity1, entity2);

        // Act
        List<UserDto> dtos = mapper.toDtoList(entities);

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
        List<UserEntity> entities = List.of();

        // Act
        List<UserDto> dtos = mapper.toDtoList(entities);

        // Assert
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    @Test
    @DisplayName("Should extract roles from UserRoleEntity")
    void extractRoles_Success() {
        // Arrange
        RoleEntity role1 = new RoleEntity();
        role1.setId(1L);
        role1.setName("USER");

        RoleEntity role2 = new RoleEntity();
        role2.setId(2L);
        role2.setName("ADMIN");

        UserRoleEntity userRole1 = new UserRoleEntity();
        userRole1.setRole(role1);

        UserRoleEntity userRole2 = new UserRoleEntity();
        userRole2.setRole(role2);

        Set<UserRoleEntity> userRoles = new HashSet<>();
        userRoles.add(userRole1);
        userRoles.add(userRole2);

        // Act
        Set<RoleDto> roleDtos = mapper.extractRoles(userRoles);

        // Assert
        assertNotNull(roleDtos);
        assertEquals(2, roleDtos.size());
    }

    @Test
    @DisplayName("Should return null when extracting roles from null")
    void extractRoles_Null() {
        // Arrange
        Set<UserRoleEntity> userRoles = null;

        // Act
        Set<RoleDto> roleDtos = mapper.extractRoles(userRoles);

        // Assert
        assertNull(roleDtos);
    }
}
