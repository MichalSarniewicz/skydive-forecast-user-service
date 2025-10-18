--liquibase formatted sql
--changeset 2025-10-18-add-permissions-schema splitStatements:false

-- Constants for table and column names
-- Schema: skydive_forecast_user
-- Tables: permissions, role_permissions

-- Create a permissions table
CREATE TABLE IF NOT EXISTS skydive_forecast_user.permissions
(
    id          BIGSERIAL PRIMARY KEY,
    code        VARCHAR(100) UNIQUE NOT NULL CHECK (LENGTH(code) > 0),
    description VARCHAR(255),
    created_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMPTZ
);

-- Create role permissions junction table
CREATE TABLE IF NOT EXISTS skydive_forecast_user.role_permissions
(
    id            BIGSERIAL PRIMARY KEY,
    role_id       BIGINT NOT NULL REFERENCES skydive_forecast_user.roles (id) ON DELETE CASCADE,
    permission_id BIGINT NOT NULL REFERENCES skydive_forecast_user.permissions (id) ON DELETE CASCADE,
    created_at    TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMPTZ,
    CONSTRAINT uq_role_permissions_role_permission UNIQUE (role_id, permission_id)
);

-- Create performance indexes
CREATE INDEX IF NOT EXISTS idx_role_permissions_role_id ON skydive_forecast_user.role_permissions (role_id);
CREATE INDEX IF NOT EXISTS idx_role_permissions_permission_id ON skydive_forecast_user.role_permissions (permission_id);

-- Add automated timestamp update triggers
CREATE TRIGGER trg_permissions_update_timestamp
    BEFORE UPDATE
    ON skydive_forecast_user.permissions
    FOR EACH ROW
EXECUTE FUNCTION skydive_forecast_user.update_timestamp();

CREATE TRIGGER trg_role_permissions_update_timestamp
    BEFORE UPDATE
    ON skydive_forecast_user.role_permissions
    FOR EACH ROW
EXECUTE FUNCTION skydive_forecast_user.update_timestamp();