package com.skydiveforecast.infrastructure.security;

import com.skydiveforecast.infrastructure.security.dto.AuthenticationRequest;
import com.skydiveforecast.infrastructure.security.dto.AuthenticationResponse;
import com.skydiveforecast.infrastructure.security.dto.RefreshTokenRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for authentication and token management")
public class AuthController {

    private final AuthBusinessService authBusinessService;

    @PostMapping("/token")
    @Operation(summary = "Generate JWT token", description = "Generate a new JWT token after successful authentication.")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse response = authBusinessService.authenticate(authenticationRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh JWT token", description = "Generate a new JWT token using a valid refresh token.")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthenticationResponse response = authBusinessService.refreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok(response);
    }
}
