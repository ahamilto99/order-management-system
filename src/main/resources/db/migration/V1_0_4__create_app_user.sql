CREATE USER app_user WITH PASSWORD 'etVZi41Bipds';

GRANT USAGE ON SCHEMA public TO app_user;

GRANT CONNECT ON DATABASE order_management_system TO app_user;

GRANT SELECT, INSERT, UPDATE, DELETE ON employees TO app_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON customers TO app_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON addresses TO app_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON orders TO app_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON order_lines TO app_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON products TO app_user;
GRANT SELECT, INSERT ON products_audit TO app_user;

GRANT USAGE ON seq_address_id TO app_user;
GRANT USAGE ON seq_customer_id TO app_user;
GRANT USAGE ON seq_employee_id TO app_user;
GRANT USAGE ON seq_order_id TO app_user;
GRANT USAGE ON seq_product_audit_id TO app_user;

GRANT EXECUTE ON FUNCTION func_calc_order_total TO app_user;

GRANT EXECUTE ON PROCEDURE usp_add_order_line TO app_user;
GRANT EXECUTE ON PROCEDURE usp_update_order_line TO app_user;
GRANT EXECUTE ON PROCEDURE usp_delete_order_line TO app_user;
GRANT EXECUTE ON PROCEDURE usp_delete_order TO app_user;
