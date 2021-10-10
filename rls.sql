ALTER TABLE student ENABLE ROW LEVEL SECURITY;
DROP POLICY IF EXISTS student_tenant_isolation_policy ON student;
CREATE POLICY student_tenant_isolation_policy ON student USING (tenant_id = current_setting('app.tenant_id')::VARCHAR);


CREATE USER app_user WITH PASSWORD 'secret';
GRANT CONNECT ON DATABASE springvault TO app_user;
GRANT USAGE ON SCHEMA public TO app_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO app_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO app_user;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public TO app_user;
