CREATE USER app_user WITH PASSWORD 'etVZi41Bipds';

GRANT USAGE ON SCHEMA public TO app_user;

GRANT CONNECT ON DATABASE order_management_system TO app_user;

GRANT SELECT, INSERT, UPDATE, DELETE ON employees TO app_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON customers TO app_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON addresses TO app_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON orders TO app_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON order_lines TO app_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON products TO app_user;
GRANT SELECT, INSERT on products_audit TO app_user;

GRANT USAGE ON seq_employees_id TO app_user;
GRANT USAGE ON seq_products_id TO app_user;
GRANT USAGE ON seq_products_audit_id TO app_user;