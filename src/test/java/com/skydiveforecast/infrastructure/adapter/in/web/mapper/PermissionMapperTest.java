package com.skydiveforecast.infrastructure.adapter.in.web.mapper;

import com.skydiveforecast.domain.model.Permission;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreatePermissionDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PermissionMapperTest {

    private final PermissionMapper mapper = Mappers.getMapper(PermissionMapper.class);

    @Test
    @DisplayName("Should map domain to DTO successfully")
    void toDto_WhenDomainProvided_ReturnsDto() {
        // Arrange
        Permission domain = Permission.builder()
                .id(1L)
                .code("USER_READ")
                .description("Read users")
                .createdAt(OffsetDateTime.now())
                .build();

        // Act
        PermissionDto result = mapper.toDto(domain);

        // Assert
        assertEquals(domain.id(), result.getId());
        assertEquals(domain.code(), result.getCode());
        assertEquals(domain.description(), result.getDescription());
    }

    @Test
    @DisplayName("Should map list of domains to list of DTOs")
    void toDtoList_WhenDomainsProvided_ReturnsDtoList() {
        // Arrange
        Permission domain = Permission.builder()
                .id(1L)
                .code("USER_READ")
                .build();

        // Act
        List<PermissionDto> result = mapper.toDtoList(List.of(domain));

        // Assert
        assertEquals(1, result.size());
        assertEquals(domain.id(), result.getFirst().getId());
    }

    @Test
    @DisplayName("Should map create DTO to domain successfully")
    void toDomain_WhenCreateDtoProvided_ReturnsDomain() {
        // Arrange
        CreatePermissionDto dto = new CreatePermissionDto();
        dto.setCode("USER_WRITE");
        dto.setDescription("Write users");

        // Act
        Permission result = mapper.toDomain(dto);

        // Assert
        assertNull(result.id());
        assertEquals(dto.getCode(), result.code());
        assertEquals(dto.getDescription(), result.description());
    }
}
