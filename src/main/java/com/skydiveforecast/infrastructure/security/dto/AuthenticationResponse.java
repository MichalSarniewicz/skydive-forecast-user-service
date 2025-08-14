package com.skydiveforecast.infrastructure.security.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String accessToken, String refreshToken, String email) {}
