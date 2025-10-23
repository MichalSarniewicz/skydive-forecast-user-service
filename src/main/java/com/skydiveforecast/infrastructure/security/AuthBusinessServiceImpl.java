package com.skydiveforecast.infrastructure.security;

import com.skydiveforecast.domain.port.in.GetPermissionCodesByUserIdUseCase;
import com.skydiveforecast.infrastructure.security.dto.AuthenticationRequest;
import com.skydiveforecast.infrastructure.security.dto.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthBusinessServiceImpl implements AuthBusinessService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final AuthService authService;
    private final GetPermissionCodesByUserIdUseCase getPermissionCodesByUserIdUseCase;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String username = request.getUsername();

        try {
            // Authentication
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            return buildAuthenticationResponse(userDetails);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public AuthenticationResponse refreshToken(String refreshToken) {
        if (!authService.validateRefreshToken(refreshToken)) {
            throw new BadCredentialsException("Invalid refresh token");
        }
        String email = authService.getUsernameFromRefreshToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String accessToken = authService.generateToken(userDetails);
        String newRefreshToken = authService.generateRefreshToken(userDetails);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .email(email)
                .build();
    }

    private AuthenticationResponse buildAuthenticationResponse(UserDetails userDetails) {
        // Generate tokens using existing AuthService
        String accessToken = authService.generateToken(userDetails);
        String refreshToken = authService.generateRefreshToken(userDetails);

        // Extract roles
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Extract additional information from CustomUserPrincipal
        boolean isActive = false;
        Long userId = null;
        Set<String> permissions = new HashSet<>();

        if (userDetails instanceof CustomUserPrincipal userPrincipal) {
            isActive = userPrincipal.isActive();
            userId = userPrincipal.getUserId();
        }

        // Get user permissions
        if (userId != null) {
            permissions = getPermissionCodesByUserIdUseCase.getPermissionCodesByUserId(userId);
        }

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(userDetails.getUsername())
                .userId(userId)
                .roles(roles)
                .permissions(permissions)
                .isActive(isActive)
                .build();
    }
}
