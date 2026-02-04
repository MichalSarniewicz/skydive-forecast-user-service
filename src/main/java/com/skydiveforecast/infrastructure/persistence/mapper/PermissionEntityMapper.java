package com.skydiveforecast.infrastructure.persistence.mapper;

import com.skydiveforecast.domain.model.Permission;
import com.skydiveforecast.infrastructure.persistence.entity.PermissionEntity;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class PermissionEntityMapper {

    public Permission toDomain(PermissionEntity entity) {
        if (entity == null) {
            return null;
        }
        return Permission.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public PermissionEntity toEntity(Permission domain) {
        if (domain == null) {
            return null;
        }
        return PermissionEntity.builder()
                .id(domain.id())
                .code(domain.code())
                .description(domain.description())
                .createdAt(domain.createdAt())
                .updatedAt(domain.updatedAt())
                .build();
    }

    public List<Permission> toDomainList(List<PermissionEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .toList();
    }

    public PermissionEntity updateEntity(Permission domain, PermissionEntity entity) {
        entity.setDescription(domain.description());
        entity.setUpdatedAt(OffsetDateTime.now());
        return entity;
    }
}
