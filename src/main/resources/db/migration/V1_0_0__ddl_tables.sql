CREATE TABLE IF NOT EXISTS employees (
	id BIGINT NOT NULL PRIMARY KEY,
	first_name VARCHAR(30) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	email VARCHAR(150) NOT NULL UNIQUE,
	phone VARCHAR(12) NOT NULL UNIQUE,
	jwt_sub UUID UNIQUE
);

CREATE INDEX IF NOT EXISTS idx_employee_jwt_sub ON employees(jwt_sub);
CREATE INDEX IF NOT EXISTS idx_employee_name ON employees(last_name, first_name);

CREATE TABLE IF NOT EXISTS customers (
	id BIGINT NOT NULL PRIMARY KEY,
	first_name VARCHAR(30) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	email VARCHAR(150) NOT NULL UNIQUE,
	phone VARCHAR(12) NOT NULL UNIQUE,
	employee_id BIGINT NOT NULL REFERENCES employees(id)
);

CREATE TABLE IF NOT EXISTS addresses (
	id BIGINT NOT NULL PRIMARY KEY,
	address_type VARCHAR(8) NOT NULL,
	is_also_shipping BOOLEAN NOT NULL,
	street_address VARCHAR(150) NOT NULL,
	po_box INTEGER,
	city VARCHAR(50) NOT NULL,
	province VARCHAR(2) NOT NULL,
	postal_code VARCHAR(7) NOT NULL,
	customer_id BIGINT NOT NULL REFERENCES customers(id)
);

-- STORED PROC performs all DELETEs on the orders table
CREATE TABLE IF NOT EXISTS orders (
	id BIGINT NOT NULL PRIMARY KEY,
	order_timestamp TIMESTAMP,
	notes VARCHAR(255),
	total NUMERIC(11,2) DEFAULT 0.00,	-- STORED PROCs manage this column
	customer_id BIGINT NOT NULL REFERENCES customers(id)
);

CREATE TABLE IF NOT EXISTS products (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),	-- version 4 UUID
	name VARCHAR(100) NOT NULL,
	description VARCHAR(255) NOT NULL,
	msrp NUMERIC(11,2) NOT NULL,
	inventory_count INTEGER NOT NULL	-- STORED PROCs manage this column
);

-- STORED PROCs perform all writes to the order_lines table
CREATE TABLE IF NOT EXISTS order_lines (
	order_id BIGINT NOT NULL REFERENCES orders(id),
	line_number SMALLINT NOT NULL,
	quantity INTEGER NOT NULL,
	msrp NUMERIC(11,2) NOT NULL,
	product_id UUID NOT NULL REFERENCES products(id),
	PRIMARY KEY(order_id, line_number),
	UNIQUE (order_id, product_id)
);

-- auditing TRIGGER populates the products_audit table
CREATE TABLE IF NOT EXISTS products_audit (
	id BIGINT NOT NULL PRIMARY KEY,
	product_id UUID NOT NULL,
	--adding or removing cols in products won't affect this table nor the auditing trigger b/c of JSONB type
	old_row JSONB,
	new_row JSONB,
	inventory_change INTEGER NOT NULL,
	revision_type VARCHAR(3),
	revision_timestamp TIMESTAMP
);
