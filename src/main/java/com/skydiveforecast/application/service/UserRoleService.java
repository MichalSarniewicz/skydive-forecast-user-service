package com.skydiveforecast.application.service;

import com.skydiveforecast.domain.model.Role;
import com.skydiveforecast.domain.model.User;
import com.skydiveforecast.infrastructure.persistence.entity.UserRoleEntity;
import com.skydiveforecast.infrastructure.adapter.out.persistence.UserRoleRepositoryAdapter;
import com.skydiveforecast.domain.port.in.AssignRoleToUserUseCase;
import com.skydiveforecast.domain.port.in.GetAllUserRolesUseCase;
import com.skydiveforecast.domain.port.in.GetUserRolesUseCase;
import com.skydiveforecast.domain.port.in.RemoveRoleFromUserUseCase;
import com.skydiveforecast.domain.port.out.RoleRepositoryPort;
import com.skydiveforecast.domain.port.out.UserRepositoryPort;
import com.skydiveforecast.domain.port.out.UserRoleRepositoryPort;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreateUserRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserRolesDto;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.UserRoleMapper;
import com.skydiveforecast.infrastructure.persistence.mapper.RoleEntityMapper;
import com.skydiveforecast.infrastructure.persistence.mapper.UserEntityMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.skydiveforecast.infrastructure.config.CacheConfig.PERMISSION_CODES_CACHE;
import static com.skydiveforecast.infrastructure.config.CacheConfig.USER_ROLES_CACHE;

@Service
@RequiredArgsConstructor
public class UserRoleService implements GetAllUserRolesUseCase, GetUserRolesUseCase,
        AssignRoleToUserUseCase, RemoveRoleFromUserUseCase {

    private final UserRoleRepositoryPort userRoleRepository;
    private final UserRoleRepositoryAdapter userRoleRepositoryAdapter;
    private final UserRoleMapper userRoleMapper;
    private final UserRepositoryPort userRepository;
    private final RoleRepositoryPort roleRepository;
    private final RoleEntityMapper roleEntityMapper;
    private final UserEntityMapper userEntityMapper;

    @Override
    @Cacheable(value = USER_ROLES_CACHE, key = "'all'")
    public UserRolesDto getAllUserRoles() {
        List<UserRoleEntity> entities = userRoleRepositoryAdapter.findAllEntities();
        return new UserRolesDto(userRoleMapper.toDtoList(entities));
    }

    @Override
    @Cacheable(value = USER_ROLES_CACHE, key = "'user:' + #userId")
    public UserRolesDto getUserRoles(Long userId) {
        List<UserRoleEntity> userRoleEntities = userRoleRepositoryAdapter.findEntitiesByUserId(userId);
        return new UserRolesDto(userRoleMapper.toDtoList(userRoleEntities));
    }

    @Override
    @Transactional
    @CacheEvict(value = {USER_ROLES_CACHE, PERMISSION_CODES_CACHE}, allEntries = true)
    public UserRoleDto assignRoleToUser(CreateUserRoleDto createUserRoleDto) {
        Long userId = createUserRoleDto.getUserId();
        Long roleId = createUserRoleDto.getRoleId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + roleId));

        boolean roleAlreadyAssigned = userRoleRepository.findByUserId(userId).stream()
                .anyMatch(userRole -> userRole.roleId().equals(roleId));
        if (roleAlreadyAssigned) {
            throw new IllegalArgumentException("User with ID: " + userId + " already has role with ID: " + roleId);
        }
        UserRoleEntity entity = UserRoleEntity.builder()
                .user(userEntityMapper.toEntity(user))
                .role(roleEntityMapper.toEntity(role))
                .build();

        return userRoleMapper.toDto(userRoleRepositoryAdapter.saveEntity(entity));
    }

    @Override
    @Transactional
    @CacheEvict(value = {USER_ROLES_CACHE, PERMISSION_CODES_CACHE}, allEntries = true)
    public void removeRoleFromUser(Long userId, Long roleId) {
        boolean roleAssigned = userRoleRepository.findByUserId(userId).stream()
                .anyMatch(userRole -> userRole.roleId().equals(roleId));

        if (!roleAssigned) {
            throw new IllegalArgumentException("User with ID: " + userId + " does not have role with ID: " + roleId);
        }

        userRoleRepository.deleteByUserIdAndRoleId(userId, roleId);
    }
}
