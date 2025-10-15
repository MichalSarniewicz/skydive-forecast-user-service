--liquibase formatted sql
--changeset 2025-10-15-create-initial-schema splitStatements:false

-- Create a schema for organizing skydive_forecast_user's database-related tables
CREATE SCHEMA IF NOT EXISTS skydive_forecast_user;

-- Table to store user information
CREATE TABLE skydive_forecast_user.users
(
    id            BIGSERIAL PRIMARY KEY,                                                             -- Unique identifier for each user
    email         VARCHAR(320) UNIQUE NOT NULL CHECK (email ~* '^[\w.%+-]+@[\w.-]+\.[a-zA-Z]{2,}$'), -- Unique email address constraint
    password_hash VARCHAR(255)        NOT NULL,                                                      -- Hashed password for user authentication
    first_name    VARCHAR(255)        NOT NULL CHECK (LENGTH(first_name) > 0),                       -- User's first name
    last_name     VARCHAR(255)        NOT NULL CHECK (LENGTH(last_name) > 0),                        -- User's last name
    phone_number  VARCHAR(20) CHECK (phone_number ~* '^[+]?[0-9]{1,20}$'),                           -- Optional phone number validation
    is_active     BOOLEAN             NOT NULL DEFAULT FALSE,                                        -- Whether the user account is active
    created_at    TIMESTAMPTZ         NOT NULL DEFAULT CURRENT_TIMESTAMP,                            -- Record creation timestamp
    updated_at    TIMESTAMPTZ                                                                        -- Last update timestamp
);

-- Table to store roles
CREATE TABLE skydive_forecast_user.roles
(
    id         BIGSERIAL PRIMARY KEY,                                 -- Unique identifier for each role
    name       VARCHAR(50) UNIQUE NOT NULL CHECK (LENGTH(name) > 0),  -- Unique name of the role
    created_at TIMESTAMPTZ        NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Record creation timestamp
    updated_at TIMESTAMPTZ                                            -- Last update timestamp
);

-- Table to map users to roles
CREATE TABLE skydive_forecast_user.user_roles
(
    id         BIGSERIAL PRIMARY KEY,                                                              -- Unique identifier for each user-role relation
    user_id    BIGINT      NOT NULL REFERENCES skydive_forecast_user.users (id) ON DELETE CASCADE, -- Foreign key to users
    role_id    BIGINT      NOT NULL REFERENCES skydive_forecast_user.roles (id) ON DELETE CASCADE, -- Foreign key to roles
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,                                     -- Record creation timestamp
    updated_at TIMESTAMPTZ,                                                                        -- Last update timestamp
    CONSTRAINT unique_user_role UNIQUE (user_id, role_id)                                          -- Ensure uniqueness
);

CREATE INDEX idx_user_roles_user_id ON skydive_forecast_user.user_roles (user_id);
CREATE INDEX idx_user_roles_role_id ON skydive_forecast_user.user_roles (role_id);

-- Trigger functions for updated_at
CREATE OR REPLACE FUNCTION skydive_forecast_user.update_timestamp()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;
--rollback DROP FUNCTION skydive_forecast_user.update_timestamp();

-- Triggers
CREATE TRIGGER trg_update_timestamp_users
    BEFORE UPDATE
    ON skydive_forecast_user.users
    FOR EACH ROW
EXECUTE FUNCTION skydive_forecast_user.update_timestamp();

CREATE TRIGGER trg_update_timestamp_roles
    BEFORE UPDATE
    ON skydive_forecast_user.roles
    FOR EACH ROW
EXECUTE FUNCTION skydive_forecast_user.update_timestamp();

CREATE TRIGGER trg_update_timestamp_user_roles
    BEFORE UPDATE
    ON skydive_forecast_user.user_roles
    FOR EACH ROW
EXECUTE FUNCTION skydive_forecast_user.update_timestamp();

-- Insert roles into the role table
INSERT INTO skydive_forecast_user.roles (name)
VALUES ('USER'),
       ('ADMIN');