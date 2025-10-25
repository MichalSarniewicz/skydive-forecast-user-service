-- Liquibase formatted SQL
-- changeset 2025-10-25-insert-role-permissions-mappings splitStatements:false

-- USER role permissions
INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'USER'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_PASSWORD_CHANGE'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'USER')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_PASSWORD_CHANGE')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT (SELECT id FROM skydive_forecast_user.roles WHERE name = 'USER'),
       (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'DROPZONE_VIEW'),
       CURRENT_TIMESTAMP WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'USER')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'DROPZONE_VIEW')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT (SELECT id FROM skydive_forecast_user.roles WHERE name = 'USER'),
       (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'REPORT_CREATE'),
       CURRENT_TIMESTAMP WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'USER')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'REPORT_CREATE')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT (SELECT id FROM skydive_forecast_user.roles WHERE name = 'USER'),
       (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'REPORT_VIEW'),
       CURRENT_TIMESTAMP WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'USER')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'REPORT_VIEW')
);

-- ADMIN role permissions
INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_PASSWORD_CHANGE'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_PASSWORD_CHANGE')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'ROLE_VIEW'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'ROLE_VIEW')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'ROLE_CREATE'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'ROLE_CREATE')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'ROLE_DELETE'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'ROLE_DELETE')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'PERMISSION_VIEW'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'PERMISSION_VIEW')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'PERMISSION_CREATE'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'PERMISSION_CREATE')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'PERMISSION_UPDATE'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'PERMISSION_UPDATE')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'PERMISSION_DELETE'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'PERMISSION_DELETE')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_ROLE_VIEW_ALL'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_ROLE_VIEW_ALL')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_ROLE_VIEW'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_ROLE_VIEW')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_ROLE_ASSIGN'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_ROLE_ASSIGN')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_ROLE_REMOVE'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_ROLE_REMOVE')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'ROLE_PERMISSION_VIEW'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'ROLE_PERMISSION_VIEW')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'ROLE_PERMISSION_CREATE'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'ROLE_PERMISSION_CREATE')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'ROLE_PERMISSION_ASSIGN'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'ROLE_PERMISSION_ASSIGN')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'ROLE_PERMISSION_DELETE'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'ROLE_PERMISSION_DELETE')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'ROLE_PERMISSION_DELETE_ALL'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'ROLE_PERMISSION_DELETE_ALL')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_VIEW'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_VIEW')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_CREATE'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_CREATE')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_STATUS_UPDATE'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_STATUS_UPDATE')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT
    (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
    (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_EDIT'),
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'USER_EDIT')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
       (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'DROPZONE_CREATE'),
       CURRENT_TIMESTAMP WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'DROPZONE_CREATE')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
       (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'DROPZONE_DELETE'),
       CURRENT_TIMESTAMP WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'DROPZONE_DELETE')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
       (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'DROPZONE_UPDATE'),
       CURRENT_TIMESTAMP WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'DROPZONE_UPDATE')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
       (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'DROPZONE_VIEW'),
       CURRENT_TIMESTAMP WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'DROPZONE_VIEW')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
       (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'REPORT_CREATE'),
       CURRENT_TIMESTAMP WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'REPORT_CREATE')
);

INSERT INTO skydive_forecast_user.role_permissions (role_id, permission_id, created_at)
SELECT (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN'),
       (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'REPORT_VIEW'),
       CURRENT_TIMESTAMP WHERE NOT EXISTS (
    SELECT 1 FROM skydive_forecast_user.role_permissions
    WHERE role_id = (SELECT id FROM skydive_forecast_user.roles WHERE name = 'ADMIN')
      AND permission_id = (SELECT id FROM skydive_forecast_user.permissions WHERE code = 'REPORT_VIEW')
);

-- rollback DELETE FROM skydive_forecast_user.role_permissions WHERE role_id IN (SELECT id FROM skydive_forecast_user.roles WHERE name IN ('USER', 'ADMIN'));