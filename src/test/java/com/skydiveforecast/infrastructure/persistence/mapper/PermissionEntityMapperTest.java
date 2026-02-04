package com.skydiveforecast.infrastructure.persistence.mapper;

import com.skydiveforecast.domain.model.Permission;
import com.skydiveforecast.infrastructure.persistence.entity.PermissionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PermissionEntityMapperTest {

    private final PermissionEntityMapper mapper = new PermissionEntityMapper();

    @Test
    @DisplayName("Should map entity to domain successfully")
    void toDomain_WhenEntityProvided_ReturnsDomainModel() {
        // Arrange
        PermissionEntity entity = PermissionEntity.builder()
                .id(1L)
                .code("USER_READ")
                .description("Read users")
                .createdAt(OffsetDateTime.now())
                .build();

        // Act
        Permission result = mapper.toDomain(entity);

        // Assert
        assertEquals(entity.getId(), result.id());
        assertEquals(entity.getCode(), result.code());
        assertEquals(entity.getDescription(), result.description());
    }

    @Test
    @DisplayName("Should return null when entity is null")
    void toDomain_WhenEntityIsNull_ReturnsNull() {
        // Act & Assert
        assertNull(mapper.toDomain(null));
    }

    @Test
    @DisplayName("Should map domain to entity successfully")
    void toEntity_WhenDomainProvided_ReturnsEntity() {
        // Arrange
        Permission domain = Permission.builder()
                .id(1L)
                .code("USER_WRITE")
                .description("Write users")
                .createdAt(OffsetDateTime.now())
                .build();

        // Act
        PermissionEntity result = mapper.toEntity(domain);

        // Assert
        assertEquals(domain.id(), result.getId());
        assertEquals(domain.code(), result.getCode());
        assertEquals(domain.description(), result.getDescription());
    }

    @Test
    @DisplayName("Should return null when domain is null")
    void toEntity_WhenDomainIsNull_ReturnsNull() {
        // Act & Assert
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("Should map list of entities to list of domains")
    void toDomainList_WhenEntitiesProvided_ReturnsDomainList() {
        // Arrange
        PermissionEntity entity = PermissionEntity.builder()
                .id(1L)
                .code("USER_READ")
                .build();

        // Act
        List<Permission> result = mapper.toDomainList(List.of(entity));

        // Assert
        assertEquals(1, result.size());
        assertEquals(entity.getId(), result.get(0).id());
    }

    @Test
    @DisplayName("Should update entity with domain values")
    void updateEntity_WhenDomainProvided_UpdatesEntitySuccessfully() {
        // Arrange
        Permission domain = Permission.builder()
                .id(1L)
                .code("USER_READ")
                .description("New description")
                .build();

        PermissionEntity entity = new PermissionEntity();
        entity.setId(1L);
        entity.setDescription("Old description");

        // Act
        PermissionEntity result = mapper.updateEntity(domain, entity);

        // Assert
        assertEquals("New description", result.getDescription());
        assertNotNull(result.getUpdatedAt());
    }
}
