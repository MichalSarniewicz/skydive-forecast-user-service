package com.skydiveforecast.infrastructure.security.dto;

import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record AuthenticationResponse(String accessToken, String refreshToken, String email, Long userId, List<String> roles,
                                     Set<String> permissions, boolean isActive) {
}