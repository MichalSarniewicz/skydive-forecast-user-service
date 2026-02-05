package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.domain.model.Role;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RoleDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleMapperTest {

    private final RoleMapper mapper = Mappers.getMapper(RoleMapper.class);

    @Test
    @DisplayName("Should map domain to DTO successfully")
    void toDto_WhenDomainProvided_ReturnsDto() {
        // Arrange
        Role domain = Role.builder()
                .id(1L)
                .name("ADMIN")
                .createdAt(OffsetDateTime.now())
                .build();

        // Act
        RoleDto result = mapper.toDto(domain);

        // Assert
        assertEquals(domain.id(), result.getId());
        assertEquals(domain.name(), result.getName());
    }

    @Test
    @DisplayName("Should map list of domains to list of DTOs")
    void toDtoList_WhenDomainsProvided_ReturnsDtoList() {
        // Arrange
        Role domain = Role.builder()
                .id(1L)
                .name("ADMIN")
                .build();

        // Act
        List<RoleDto> result = mapper.toDtoList(List.of(domain));

        // Assert
        assertEquals(1, result.size());
        assertEquals(domain.id(), result.get(0).getId());
    }
}
