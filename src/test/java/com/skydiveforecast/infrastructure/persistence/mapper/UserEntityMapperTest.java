package com.skydiveforecast.infrastructure.persistence.mapper;

import com.skydiveforecast.domain.model.User;
import com.skydiveforecast.infrastructure.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityMapperTest {

    private UserEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(UserEntityMapper.class);
    }

    @Test
    void toDomain_ShouldMapEntityToDomain() {
        // Arrange
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setEmail("test@test.com");
        entity.setFirstName("John");
        entity.setLastName("Doe");
        entity.setPasswordHash("hash");
        entity.setPhoneNumber("123456789");
        entity.setActive(true);

        // Act
        User domain = mapper.toDomain(entity);

        // Assert
        assertEquals(1L, domain.id());
        assertEquals("test@test.com", domain.email());
        assertEquals("John", domain.firstName());
        assertEquals("Doe", domain.lastName());
        assertEquals("hash", domain.passwordHash());
        assertEquals("123456789", domain.phoneNumber());
        assertTrue(domain.isActive());
    }

    @Test
    void toEntity_ShouldMapDomainToEntity() {
        // Arrange
        User domain = User.builder()
                .id(1L)
                .email("test@test.com")
                .firstName("John")
                .lastName("Doe")
                .passwordHash("hash")
                .phoneNumber("123456789")
                .isActive(true)
                .build();

        // Act
        UserEntity entity = mapper.toEntity(domain);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("test@test.com", entity.getEmail());
        assertEquals("John", entity.getFirstName());
        assertEquals("Doe", entity.getLastName());
        assertEquals("hash", entity.getPasswordHash());
        assertEquals("123456789", entity.getPhoneNumber());
        assertTrue(entity.isActive());
    }

    @Test
    void toDomainList_ShouldMapEntityListToDomainList() {
        // Arrange
        UserEntity entity1 = new UserEntity();
        entity1.setId(1L);
        entity1.setEmail("test1@test.com");
        entity1.setFirstName("John");
        entity1.setLastName("Doe");
        entity1.setPasswordHash("hash");
        entity1.setActive(true);

        UserEntity entity2 = new UserEntity();
        entity2.setId(2L);
        entity2.setEmail("test2@test.com");
        entity2.setFirstName("Jane");
        entity2.setLastName("Smith");
        entity2.setPasswordHash("hash2");
        entity2.setActive(false);

        // Act
        List<User> domains = mapper.toDomainList(List.of(entity1, entity2));

        // Assert
        assertEquals(2, domains.size());
        assertEquals(1L, domains.get(0).id());
        assertEquals(2L, domains.get(1).id());
    }
}
