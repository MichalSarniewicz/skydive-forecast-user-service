package com.skydiveforecast.domain.model;

import lombok.Builder;

@Builder
public record User(
        Long id,
        String email,
        String passwordHash,
        String firstName,
        String lastName,
        String phoneNumber,
        boolean isActive
) {
    public User withId(Long id) {
        return new User(id, this.email, this.passwordHash, this.firstName, this.lastName, this.phoneNumber, this.isActive);
    }

    public User withPasswordHash(String passwordHash) {
        return new User(this.id, this.email, passwordHash, this.firstName, this.lastName, this.phoneNumber, this.isActive);
    }

    public User withIsActive(boolean isActive) {
        return new User(this.id, this.email, this.passwordHash, this.firstName, this.lastName, this.phoneNumber, isActive);
    }
}
