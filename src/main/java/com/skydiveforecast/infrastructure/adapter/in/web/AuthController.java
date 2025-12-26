package com.skydiveforecast.infrastructure.adapter.in.web;

import com.skydiveforecast.infrastructure.security.AuthBusinessService;
import com.skydiveforecast.infrastructure.security.dto.AuthenticationRequest;
import com.skydiveforecast.infrastructure.security.dto.AuthenticationResponse;
import com.skydiveforecast.infrastructure.security.dto.RefreshTokenRequest;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for authentication and token management")
public class AuthController {

    private final AuthBusinessService authBusinessService;
    private final Counter loginSuccessCounter;
    private final Counter loginFailureCounter;
    private final Counter tokenRefreshCounter;
    private final Timer authenticationTimer;

    @PostMapping("/token")
    @Operation(summary = "Generate JWT token", description = "Generate a new JWT token after successful authentication.")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationTimer.record(() -> {
            try {
                AuthenticationResponse response = authBusinessService.authenticate(authenticationRequest);
                loginSuccessCounter.increment();
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                loginFailureCounter.increment();
                throw e;
            }
        });
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh JWT token", description = "Generate a new JWT token using a valid refresh token.")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        tokenRefreshCounter.increment();
        AuthenticationResponse response = authBusinessService.refreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok(response);
    }
}
