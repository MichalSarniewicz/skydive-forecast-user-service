package com.skydiveforecast.domain.model;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record Role(Long id, String name, OffsetDateTime createdAt, OffsetDateTime updatedAt) {

    public Role withId(Long id) {
        return new Role(id, this.name, this.createdAt, this.updatedAt);
    }

    public Role withName(String name) {
        return new Role(this.id, name, this.createdAt, this.updatedAt);
    }

    public Role withUpdatedAt(OffsetDateTime updatedAt) {
        return new Role(this.id, this.name, this.createdAt, updatedAt);
    }
}
