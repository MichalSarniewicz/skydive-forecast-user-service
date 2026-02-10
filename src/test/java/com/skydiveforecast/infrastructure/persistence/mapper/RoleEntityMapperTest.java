package com.skydiveforecast.infrastructure.persistence.mapper;

import com.skydiveforecast.domain.model.Role;
import com.skydiveforecast.infrastructure.persistence.entity.RoleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleEntityMapperTest {

    private RoleEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(RoleEntityMapper.class);
    }

    @Test
    void toDomain_ShouldMapEntityToDomain() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();
        RoleEntity entity = new RoleEntity();
        entity.setId(1L);
        entity.setName("ADMIN");
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        // Act
        Role domain = mapper.toDomain(entity);

        // Assert
        assertEquals(1L, domain.id());
        assertEquals("ADMIN", domain.name());
        assertEquals(now, domain.createdAt());
        assertEquals(now, domain.updatedAt());
    }

    @Test
    void toEntity_ShouldMapDomainToEntity() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();
        Role domain = Role.builder()
                .id(1L)
                .name("USER")
                .createdAt(now)
                .updatedAt(now)
                .build();

        // Act
        RoleEntity entity = mapper.toEntity(domain);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("USER", entity.getName());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
    }

    @Test
    void toDomainList_ShouldMapEntityListToDomainList() {
        // Arrange
        RoleEntity entity1 = new RoleEntity();
        entity1.setId(1L);
        entity1.setName("ADMIN");

        RoleEntity entity2 = new RoleEntity();
        entity2.setId(2L);
        entity2.setName("USER");

        // Act
        List<Role> domains = mapper.toDomainList(List.of(entity1, entity2));

        // Assert
        assertEquals(2, domains.size());
        assertEquals("ADMIN", domains.get(0).name());
        assertEquals("USER", domains.get(1).name());
    }
}
