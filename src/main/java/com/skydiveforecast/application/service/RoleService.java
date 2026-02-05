package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.exception.BusinessRuleException;
import com.skydiveforecast.domain.model.Role;
import com.skydiveforecast.domain.port.in.AddRoleUseCase;
import com.skydiveforecast.domain.port.in.DeleteRoleUseCase;
import com.skydiveforecast.domain.port.in.GetAllRolesUseCase;
import com.skydiveforecast.domain.port.out.RoleRepositoryPort;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolesDto;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.RoleMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

import static com.skydiveforecast.infrastructure.config.CacheConfig.ROLES_CACHE;

@Service
@RequiredArgsConstructor
public class RoleService implements GetAllRolesUseCase, AddRoleUseCase, DeleteRoleUseCase {

    private final RoleRepositoryPort roleRepository;
    private final RoleMapper roleMapper;
    private static final String ADMIN_ROLE = "ADMIN";

    @Override
    @Cacheable(value = ROLES_CACHE, key = "'all'")
    public RolesDto getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return new RolesDto(roleMapper.toDtoList(roles));
    }

    @Override
    @CacheEvict(value = ROLES_CACHE, allEntries = true)
    public RoleDto addRole(String roleName) {
        Role role = Role.builder()
                .name(roleName)
                .createdAt(OffsetDateTime.now())
                .build();

        Role savedRole = roleRepository.save(role);
        return roleMapper.toDto(savedRole);
    }

    @Override
    @CacheEvict(value = ROLES_CACHE, allEntries = true)
    public void deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role with ID " + roleId + " does not exist"));

        if (ADMIN_ROLE.equals(role.name())) {
            throw new BusinessRuleException("The ADMIN role cannot be deleted");
        }
        roleRepository.deleteById(role.id());
    }
}
