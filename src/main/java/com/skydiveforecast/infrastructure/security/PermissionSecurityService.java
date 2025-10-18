package com.skydiveforecast.infrastructure.security;

import com.skydiveforecast.infrastructure.adapter.out.persistance.RolePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PermissionSecurityService {

    private final RolePermissionRepository rolePermissionRepository;

    public boolean hasPermission(String permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserPrincipal customUserPrincipal) {
            Long userId = customUserPrincipal.getUserId();
            Set<String> userPermissions = rolePermissionRepository.findPermissionCodesByUserId(userId);
            return userPermissions.contains(permission);
        }

        return false;
    }
}