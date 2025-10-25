-- Liquibase formatted SQL
-- changeset 2025-10-24-insert-permissions-codes splitStatements:false

INSERT INTO skydive_forecast_user.permissions (code, description)
SELECT t.code, t.description
FROM (VALUES
          ('USER_PASSWORD_CHANGE', 'Allows changing user password'),
          ('REPORT_CREATE', 'Create new reports'),
          ('REPORT_VIEW', 'View existing reports'),
          ('ROLE_VIEW', 'View roles'),
          ('ROLE_CREATE', 'Create new roles'),
          ('ROLE_DELETE', 'Delete existing roles'),
          ('PERMISSION_VIEW', 'View permissions'),
          ('PERMISSION_CREATE', 'Create new permissions'),
          ('PERMISSION_UPDATE', 'Update existing permissions'),
          ('PERMISSION_DELETE', 'Delete permissions'),
          ('USER_ROLE_VIEW_ALL', 'View all user roles'),
          ('USER_ROLE_VIEW', 'View user roles'),
          ('USER_ROLE_ASSIGN', 'Assign roles to users'),
          ('USER_ROLE_REMOVE', 'Remove roles from users'),
          ('ROLE_PERMISSION_VIEW', 'View role permissions'),
          ('ROLE_PERMISSION_CREATE', 'Create role permissions'),
          ('ROLE_PERMISSION_ASSIGN', 'Assign permissions to roles'),
          ('ROLE_PERMISSION_DELETE', 'Delete role permissions'),
          ('ROLE_PERMISSION_DELETE_ALL', 'Delete all permissions from a role'),
          ('USER_VIEW', 'View user information'),
          ('USER_CREATE', 'Create new users'),
          ('USER_STATUS_UPDATE', 'Update user status'),
          ('USER_EDIT', 'Edit user details'),
          ('DROPZONE_VIEW', 'View dropzone information'),
          ('DROPZONE_CREATE', 'Create new dropzones'),
          ('DROPZONE_DELETE', 'Delete dropzones'),
          ('DROPZONE_UPDATE', 'Update dropzone information')
     ) AS t(code, description)
WHERE NOT EXISTS (
    SELECT 1
    FROM skydive_forecast_user.permissions p
    WHERE p.code = t.code
);
--rollback DELETE FROM skydive_forecast_user.role_permissions;