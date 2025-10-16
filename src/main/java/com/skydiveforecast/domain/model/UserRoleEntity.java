package com.skydiveforecast.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_roles", schema = "skydive_forecast_user")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;
}