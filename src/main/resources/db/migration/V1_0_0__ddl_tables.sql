CREATE TABLE IF NOT EXISTS employees (
	id BIGINT NOT NULL PRIMARY KEY,
	username VARCHAR(10) NOT NULL,
	first_name VARCHAR(30) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	email VARCHAR(150) NOT NULL,
	phone VARCHAR(12) NOT NULL,
	version SMALLINT NOT NULL
);

CREATE TABLE IF NOT EXISTS customers (
	id UUID NOT NULL PRIMARY KEY,
	first_name VARCHAR(30) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	email VARCHAR(150) NOT NULL,
	phone VARCHAR(12) NOT NULL,
	version SMALLINT NOT NULL,
	employee_id BIGINT NOT NULL REFERENCES employees(id)
);

CREATE TABLE IF NOT EXISTS addresses (
	id BIGINT NOT NULL PRIMARY KEY,
	address_type CHAR(1) NOT NULL,
	is_also_shipping BOOLEAN NOT NULL,
	street_address VARCHAR(150) NOT NULL,
	po_box INTEGER,
	city VARCHAR(50) NOT NULL,
	province INTEGER NOT NULL,
	postal_code VARCHAR(7) NOT NULL,
	version SMALLINT NOT NULL,
	customer_id UUID NOT NULL REFERENCES customers(id)
);

CREATE TABLE IF NOT EXISTS orders (
	id UUID NOT NULL PRIMARY KEY,
	order_timestamp TIMESTAMP,
	notes VARCHAR(255),
	subtotal NUMERIC(11,2) NOT NULL,
	discount NUMERIC(2,2) CHECK(discount > 0 AND discount < 1),
	tax NUMERIC(11,2) NOT NULL,
	total NUMERIC(11,2) NOT NULL,
	customer_id UUID NOT NULL REFERENCES customers(id)
);

CREATE TABLE IF NOT EXISTS products (
	id BIGINT NOT NULL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	description VARCHAR(255) NOT NULL,
	msrp NUMERIC(11,2) NOT NULL,
	inventory_count INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS order_lines (
	order_id UUID NOT NULL REFERENCES orders(id),
	line_number SMALLINT NOT NULL,
	quantity INTEGER NOT NULL,
	msrp NUMERIC(11,2) NOT NULL,
	product_id BIGINT NOT NULL REFERENCES products(id),
	PRIMARY KEY (order_id, line_number)
);

CREATE TABLE IF NOT EXISTS products_audit (
	id BIGINT NOT NULL PRIMARY KEY,
	product_id BIGINT NOT NULL,
	--adding or removing cols in products won't affect this table nor the audit trigger b/c of JSONB type
	old_row JSONB,
	new_row JSONB,
	inventory_change INTEGER NOT NULL,
	revision_type VARCHAR(3),
	revision_timestamp TIMESTAMP
);
