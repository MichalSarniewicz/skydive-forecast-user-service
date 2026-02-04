package com.skydiveforecast.infrastructure.security;

import com.skydiveforecast.infrastructure.persistance.entity.RoleEntity;
import com.skydiveforecast.infrastructure.persistance.entity.UserEntity;
import com.skydiveforecast.infrastructure.persistance.entity.UserRoleEntity;
import com.skydiveforecast.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositoryPort userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(UserRoleEntity::getRole)
                .map(RoleEntity::getName)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        return new CustomUserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPasswordHash(),
                user.isActive(),
                authorities
        );
    }
}
