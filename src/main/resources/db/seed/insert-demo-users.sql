--liquibase formatted sql
--changeset insert-demo-users context:seed splitStatements:false

-- Insert demo admin user
-- Email: admin@skydive.com
-- Password: Admin123!
INSERT INTO skydive_forecast_user.users (email, password_hash, first_name, last_name, phone_number, is_active)
VALUES ('admin@skydive.com', '$2a$10$pwWaT11cdphE.IrHQKs5SeuTJeMQf.UH9E9tsref8UIZibtA0y2NO', 'Admin', 'User', '+1234567890', true);

-- Insert demo regular user
-- Email: user@skydive.com
-- Password: User123!
INSERT INTO skydive_forecast_user.users (email, password_hash, first_name, last_name, phone_number, is_active)
VALUES ('user@skydive.com', '$2a$10$WWG7ey17ApEaec/MpWH40OkhzIlGjJQ1GDPj5NBtrxLQ.PiwbPqNK', 'Regular', 'User', '+0987654321', true);

-- Assign ADMIN role to the admin user
INSERT INTO skydive_forecast_user.user_roles (user_id, role_id)
SELECT u.id, r.id
FROM skydive_forecast_user.users u,
     skydive_forecast_user.roles r
WHERE u.email = 'admin@skydive.com'
  AND r.name = 'ADMIN';

-- Assign USER role to regular user
INSERT INTO skydive_forecast_user.user_roles (user_id, role_id)
SELECT u.id, r.id
FROM skydive_forecast_user.users u,
     skydive_forecast_user.roles r
WHERE u.email = 'user@skydive.com'
  AND r.name = 'USER';
