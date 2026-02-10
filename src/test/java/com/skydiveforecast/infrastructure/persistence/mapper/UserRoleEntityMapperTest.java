package com.skydiveforecast.infrastructure.persistence.mapper;

import com.skydiveforecast.domain.model.UserRole;
import com.skydiveforecast.infrastructure.persistence.entity.RoleEntity;
import com.skydiveforecast.infrastructure.persistence.entity.UserEntity;
import com.skydiveforecast.infrastructure.persistence.entity.UserRoleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleEntityMapperTest {

    private UserRoleEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(UserRoleEntityMapper.class);
    }

    @Test
    void toDomain_ShouldMapEntityToDomain() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(2L);
        
        UserRoleEntity entity = new UserRoleEntity();
        entity.setId(10L);
        entity.setUser(userEntity);
        entity.setRole(roleEntity);

        // Act
        UserRole domain = mapper.toDomain(entity);

        // Assert
        assertEquals(10L, domain.id());
        assertEquals(1L, domain.userId());
        assertEquals(2L, domain.roleId());
    }

    @Test
    void toEntity_ShouldMapDomainToEntity() {
        // Arrange
        UserRole domain = UserRole.builder()
                .id(10L)
                .userId(1L)
                .roleId(2L)
                .build();

        // Act
        UserRoleEntity entity = mapper.toEntity(domain);

        // Assert
        assertEquals(10L, entity.getId());
    }

    @Test
    void toDomainList_ShouldMapEntityListToDomainList() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(2L);
        
        UserRoleEntity entity1 = new UserRoleEntity();
        entity1.setId(10L);
        entity1.setUser(userEntity);
        entity1.setRole(roleEntity);

        UserRoleEntity entity2 = new UserRoleEntity();
        entity2.setId(11L);
        entity2.setUser(userEntity);
        entity2.setRole(roleEntity);

        // Act
        List<UserRole> domains = mapper.toDomainList(List.of(entity1, entity2));

        // Assert
        assertEquals(2, domains.size());
        assertEquals(10L, domains.get(0).id());
        assertEquals(11L, domains.get(1).id());
    }
}
