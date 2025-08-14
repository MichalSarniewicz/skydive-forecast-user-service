package com.skydiveforecast.infrastructure.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    String extractUsername(String token);
    boolean validateToken(String token, UserDetails userDetails);
    String generateToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    boolean validateRefreshToken(String token);
    String getUsernameFromRefreshToken(String token);
}
