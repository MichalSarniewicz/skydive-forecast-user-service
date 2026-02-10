package com.skydiveforecast.infrastructure.persistence.mapper;

import com.skydiveforecast.domain.model.RolePermission;
import com.skydiveforecast.infrastructure.persistence.entity.PermissionEntity;
import com.skydiveforecast.infrastructure.persistence.entity.RoleEntity;
import com.skydiveforecast.infrastructure.persistence.entity.RolePermissionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RolePermissionEntityMapperTest {

    private RolePermissionEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(RolePermissionEntityMapper.class);
    }

    @Test
    void toDomain_ShouldMapEntityToDomain() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setId(2L);
        
        RolePermissionEntity entity = new RolePermissionEntity();
        entity.setId(10L);
        entity.setRole(roleEntity);
        entity.setPermission(permissionEntity);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        // Act
        RolePermission domain = mapper.toDomain(entity);

        // Assert
        assertEquals(10L, domain.id());
        assertEquals(1L, domain.roleId());
        assertEquals(2L, domain.permissionId());
        assertEquals(now, domain.createdAt());
        assertEquals(now, domain.updatedAt());
    }

    @Test
    void toEntity_ShouldMapDomainToEntity() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();
        RolePermission domain = RolePermission.builder()
                .id(10L)
                .roleId(1L)
                .permissionId(2L)
                .createdAt(now)
                .updatedAt(now)
                .build();

        // Act
        RolePermissionEntity entity = mapper.toEntity(domain);

        // Assert
        assertEquals(10L, entity.getId());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
    }

    @Test
    void toDomainList_ShouldMapEntityListToDomainList() {
        // Arrange
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setId(2L);
        
        RolePermissionEntity entity1 = new RolePermissionEntity();
        entity1.setId(10L);
        entity1.setRole(roleEntity);
        entity1.setPermission(permissionEntity);

        RolePermissionEntity entity2 = new RolePermissionEntity();
        entity2.setId(11L);
        entity2.setRole(roleEntity);
        entity2.setPermission(permissionEntity);

        // Act
        List<RolePermission> domains = mapper.toDomainList(List.of(entity1, entity2));

        // Assert
        assertEquals(2, domains.size());
        assertEquals(10L, domains.get(0).id());
        assertEquals(11L, domains.get(1).id());
    }
}
