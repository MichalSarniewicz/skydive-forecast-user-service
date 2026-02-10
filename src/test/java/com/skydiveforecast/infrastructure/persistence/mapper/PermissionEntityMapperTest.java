package com.skydiveforecast.infrastructure.persistence.mapper;

import com.skydiveforecast.domain.model.Permission;
import com.skydiveforecast.infrastructure.persistence.entity.PermissionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PermissionEntityMapperTest {

    private PermissionEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(PermissionEntityMapper.class);
    }

    @Test
    void toDomain_ShouldMapEntityToDomain() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();
        PermissionEntity entity = new PermissionEntity();
        entity.setId(1L);
        entity.setCode("USER_READ");
        entity.setDescription("Read users");
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        // Act
        Permission domain = mapper.toDomain(entity);

        // Assert
        assertEquals(1L, domain.id());
        assertEquals("USER_READ", domain.code());
        assertEquals("Read users", domain.description());
        assertEquals(now, domain.createdAt());
        assertEquals(now, domain.updatedAt());
    }

    @Test
    void toEntity_ShouldMapDomainToEntity() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();
        Permission domain = Permission.builder()
                .id(1L)
                .code("USER_WRITE")
                .description("Write users")
                .createdAt(now)
                .updatedAt(now)
                .build();

        // Act
        PermissionEntity entity = mapper.toEntity(domain);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("USER_WRITE", entity.getCode());
        assertEquals("Write users", entity.getDescription());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
    }

    @Test
    void toDomainList_ShouldMapEntityListToDomainList() {
        // Arrange
        PermissionEntity entity1 = new PermissionEntity();
        entity1.setId(1L);
        entity1.setCode("USER_READ");

        PermissionEntity entity2 = new PermissionEntity();
        entity2.setId(2L);
        entity2.setCode("USER_WRITE");

        // Act
        List<Permission> domains = mapper.toDomainList(List.of(entity1, entity2));

        // Assert
        assertEquals(2, domains.size());
        assertEquals("USER_READ", domains.get(0).code());
        assertEquals("USER_WRITE", domains.get(1).code());
    }
}
