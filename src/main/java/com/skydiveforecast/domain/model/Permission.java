package com.skydiveforecast.domain.model;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record Permission(Long id, String code, String description, OffsetDateTime createdAt, OffsetDateTime updatedAt) {

    public Permission withId(Long id) {
        return new Permission(id, this.code, this.description, this.createdAt, this.updatedAt);
    }

    public Permission withDescription(String description) {
        return new Permission(this.id, this.code, description, this.createdAt, this.updatedAt);
    }

    public Permission withUpdatedAt(OffsetDateTime updatedAt) {
        return new Permission(this.id, this.code, this.description, this.createdAt, updatedAt);
    }
}
