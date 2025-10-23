-- Create a schema for the application
CREATE SCHEMA IF NOT EXISTS skydive_forecast_user;

-- Grant privileges to the application user
GRANT ALL PRIVILEGES ON SCHEMA skydive_forecast_user TO skydive_forecast_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA skydive_forecast_user TO skydive_forecast_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA skydive_forecast_user TO skydive_forecast_user;

-- Set default privileges for future tables
ALTER DEFAULT PRIVILEGES IN SCHEMA skydive_forecast_user GRANT ALL PRIVILEGES ON TABLES TO skydive_forecast_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA skydive_forecast_user GRANT ALL PRIVILEGES ON SEQUENCES TO skydive_forecast_user;