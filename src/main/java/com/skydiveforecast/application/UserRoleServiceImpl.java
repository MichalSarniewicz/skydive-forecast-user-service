package com.skydiveforecast.application;

import com.skydiveforecast.domain.model.RoleEntity;
import com.skydiveforecast.domain.model.UserEntity;
import com.skydiveforecast.domain.model.UserRoleEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.UserRoleMapper;
import com.skydiveforecast.domain.port.in.AssignRoleToUserUseCase;
import com.skydiveforecast.domain.port.in.GetAllUserRolesUseCase;
import com.skydiveforecast.domain.port.in.GetUserRolesUseCase;
import com.skydiveforecast.domain.port.in.RemoveRoleFromUserUseCase;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.CreateUserRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserRoleDto;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.UserRolesDto;
import com.skydiveforecast.infrastructure.adapter.out.persistance.RoleRepository;
import com.skydiveforecast.infrastructure.adapter.out.persistance.UserRepository;
import com.skydiveforecast.infrastructure.adapter.out.persistance.UserRoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements GetAllUserRolesUseCase, GetUserRolesUseCase,
        AssignRoleToUserUseCase, RemoveRoleFromUserUseCase {

    private final UserRoleRepository userRoleRepository;
    private final UserRoleMapper userRoleMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserRolesDto getAllUserRoles() {
        List<UserRoleEntity> entities = userRoleRepository.findAll();
        return new UserRolesDto(userRoleMapper.toDtoList(entities));
    }

    @Override
    public UserRolesDto getUserRoles(Long userId) {
        List<UserRoleEntity> userRoleEntities = userRoleRepository.findByUserId(userId);
        return new UserRolesDto(userRoleMapper.toDtoList(userRoleEntities));
    }

    @Override
    @Transactional
    public UserRoleDto assignRoleToUser(CreateUserRoleDto createUserRoleDto) {
        Long userId = createUserRoleDto.getUserId();
        Long roleId = createUserRoleDto.getRoleId();

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + roleId));

        boolean roleAlreadyAssigned = userRoleRepository.findByUserId(userId).stream()
                .anyMatch(userRole -> userRole.getRole().getId().equals(roleId));
        if (roleAlreadyAssigned) {
            throw new IllegalArgumentException("User with ID: " + userId + " already has role with ID: " + roleId);
        }
        UserRoleEntity entity = UserRoleEntity.builder()
                .user(user)
                .role(role)
                .build();

        return userRoleMapper.toDto(userRoleRepository.save(entity));
    }

    @Override
    @Transactional
    public void removeRoleFromUser(Long userId, Long roleId) {
        boolean roleAssigned = userRoleRepository.findByUserId(userId).stream()
                .anyMatch(userRole -> userRole.getRole().getId().equals(roleId));

        if (!roleAssigned) {
            throw new IllegalArgumentException("User with ID: " + userId + " does not have role with ID: " + roleId);
        }

        userRoleRepository.deleteByUserIdAndRoleId(userId, roleId);
    }
}
