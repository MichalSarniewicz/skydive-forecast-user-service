package com.skydiveforecast.domain.service;

import com.skydiveforecast.domain.model.RoleEntity;
import com.skydiveforecast.domain.model.mapper.RoleMapper;
import com.skydiveforecast.domain.port.in.AddRoleUseCase;
import com.skydiveforecast.domain.port.in.DeleteRoleUseCase;
import com.skydiveforecast.domain.port.in.GetAllRolesUseCase;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.RolesDto;
import com.skydiveforecast.infrastructure.adapter.out.persistance.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements GetAllRolesUseCase, AddRoleUseCase, DeleteRoleUseCase {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private static final String ADMIN_ROLE = "ADMIN";

    @Override
    public RolesDto getAllRoles() {
        List<RoleEntity> entities = roleRepository.findAll();
        return new RolesDto(roleMapper.toDtoList(entities));
    }

    @Override
    public RoleDto addRole(String roleName) {
        RoleEntity roleEntity = RoleEntity.builder()
                .name(roleName)
                .createdAt(OffsetDateTime.now())
                .build();

        RoleEntity savedRole = roleRepository.save(roleEntity);
        return roleMapper.toDto(savedRole);
    }

    @Override
    public void deleteRole(Long roleId) {
        RoleEntity roleEntity = roleRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role with ID " + roleId + " does not exist"));

        if (ADMIN_ROLE.equals(roleEntity.getName())) {
            throw new EntityNotFoundException("The ADMIN role cannot be deleted");
        }
        roleRepository.delete(roleEntity);
    }
}
