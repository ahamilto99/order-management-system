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
	customer_id UUID NOT NULL REFERENCES customers(id),
	address_type CHAR(1) NOT NULL,
	shipping_billing_same BOOLEAN NOT NULL,
	street_address VARCHAR(150) NOT NULL,
	po_box INTEGER,
	city VARCHAR(50) NOT NULL,
	province VARCHAR(25) NOT NULL,
	postal_code VARCHAR(7) NOT NULL,
	version SMALLINT NOT NULL,
	PRIMARY KEY (customer_id, address_type)
);

CREATE TABLE IF NOT EXISTS orders (
	id UUID NOT NULL PRIMARY KEY,
	order_timestamp TIMESTAMP,
	notes VARCHAR(255),
	subtotal MONEY NOT NULL,
	discount DECIMAL(2,2) CHECK(discount > 0 AND discount < 1),
	tax MONEY NOT NULL,
	total MONEY NOT NULL,
	customer_id UUID NOT NULL REFERENCES customers(id)
);

CREATE TABLE IF NOT EXISTS products (
	id BIGINT NOT NULL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	description VARCHAR(255) NOT NULL,
	mrsp MONEY NOT NULL,
	inventory_count INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS order_lines (
	order_id UUID NOT NULL REFERENCES orders(id),
	line_number SMALLINT NOT NULL,
	quantity INTEGER NOT NULL,
	mrsp MONEY NOT NULL,
	product_id BIGINT NOT NULL REFERENCES products(id),
	PRIMARY KEY (order_id, line_number)
);

CREATE TABLE IF NOT EXISTS products_audit (
	id BIGINT NOT NULL PRIMARY KEY,
	product_id BIGINT NOT NULL,
	name VARCHAR(100) NOT NULL,
	mrsp MONEY NOT NULL,
	inventory_count INTEGER NOT NULL,
	inventory_change INTEGER NOT NULL,
	revision_type CHAR(3),
	revision_timestamp TIMESTAMP
);
