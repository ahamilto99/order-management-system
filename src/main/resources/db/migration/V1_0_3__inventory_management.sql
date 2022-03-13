CREATE OR REPLACE FUNCTION func_calc_order_total
(
	corresponding_order BIGINT
) 
RETURNS NUMERIC(11, 2)
LANGUAGE plpgsql
AS $$
BEGIN
	IF (SELECT EXISTS(SELECT 1 FROM orders WHERE id = corresponding_order) = FALSE) THEN
		RAISE EXCEPTION 'Order does not exist';
	END IF;

	RETURN (SELECT SUM(quantity * msrp) FROM order_lines WHERE order_id = corresponding_order);
END;
$$;

CREATE OR REPLACE PROCEDURE usp_add_order_line
(
	corresponding_order BIGINT, 
	product_ordered UUID,
	qty_ordered INTEGER
) 
LANGUAGE plpgsql
AS $$
DECLARE 
	unit_price NUMERIC(11, 2) := (SELECT msrp FROM products WHERE id = product_ordered); 
BEGIN
	IF (unit_price IS NULL) THEN
		RAISE EXCEPTION 'Product does not exist';
	END IF;
	
	IF (SELECT EXISTS(SELECT 1 FROM orders WHERE id = corresponding_order) = FALSE) THEN
		RAISE EXCEPTION 'Order does not exist';
	END IF;

	IF (qty_ordered > (SELECT inventory_count FROM products WHERE id = product_ordered)) THEN
		RAISE EXCEPTION 'Not enough product in stock';
	END IF;
	
	INSERT INTO order_lines (
		order_id,
		line_number,
		quantity,
		msrp,
		product_id
	)
	VALUES (
		corresponding_order,
		(SELECT COUNT(order_id) + 1 FROM order_lines WHERE order_id = corresponding_order),
		qty_ordered,
		unit_price,
		product_ordered
	);
	
	UPDATE products SET inventory_count = (inventory_count - qty_ordered) WHERE id = product_ordered; 
	
	UPDATE orders SET total = calc_order_total(corresponding_order) WHERE id = corresponding_order;
END;
$$;

CREATE OR REPLACE PROCEDURE usp_update_order_line
(
	corresponding_order BIGINT,
	product UUID,
	new_qty INTEGER
)  
LANGUAGE plpgsql
AS $$
DECLARE 
	unit_price NUMERIC(11, 2) := (SELECT msrp FROM products WHERE id = product);
	qty_delta INTEGER := new_qty - (SELECT quantity FROM order_lines WHERE order_id = corresponding_order AND product_id = product);  	
BEGIN
	IF (unit_price IS NULL) THEN	
		RAISE EXCEPTION 'Product does not exist';
	END IF;
		
	IF (qty_delta IS NULL) THEN 
		RAISE EXCEPTION 'Order line does not exist';
	END IF;
	
	IF (qty_delta > (SELECT inventory_count FROM products WHERE id = product)) THEN 
		RAISE EXCEPTION 'Not enough product in stock';
	END IF;
	
	UPDATE order_lines SET quantity = new_qty, msrp = unit_price WHERE order_id = corresponding_order AND product_id = product;
	
	UPDATE products SET inventory_count = (inventory_count + qty_delta) WHERE id = product;
	
	UPDATE orders SET total = calc_order_total(corresponding_order) FROM order_lines WHERE id = corresponding_order;
END;
$$;

CREATE OR REPLACE PROCEDURE usp_delete_order_line
(
	corresponding_order BIGINT,
	product UUID
)
LANGUAGE plpgsql
AS $$
DECLARE
	qty INTEGER := (SELECT quantity FROM order_lines WHERE order_id = corresponding_order AND product_id = product);
	line INTEGER := (SELECT line_number FROM order_lines WHERE order_id = corresponding_order AND product_id = product); 
BEGIN
	IF (qty IS NULL OR line IS NULL) THEN
		RAISE EXCEPTION 'Order line does not exist';
	END IF;

	UPDATE products SET inventory_count = (inventory_count + qty) WHERE id = product;
	
	DELETE FROM order_lines WHERE order_id = corresponding_order AND product_id = product;
	
	COMMIT;
	
	UPDATE order_lines SET line_number = (line_number - 1) WHERE line_number > line;
	
	UPDATE orders SET total = calc_order_total(corresponding_order) WHERE id = corresponding_order;
END;
$$;

CREATE OR REPLACE PROCEDURE usp_delete_order
(
	corresponding_order BIGINT
)
LANGUAGE plpgsql
AS $$
DECLARE
	order_line RECORD;
BEGIN
	IF (SELECT EXISTS(SELECT 1 FROM orders WHERE id = corresponding_order) = FALSE) THEN
		RAISE EXCEPTION 'Order does not exist';
	END IF;

	FOR order_line IN 
		(SELECT quantity, product_id FROM order_lines WHERE order_id = corresponding_order)
	LOOP
		UPDATE products SET inventory_count = (inventory_count + order_line.quantity) WHERE id = order_line.product_id;
	END LOOP;

	DELETE FROM order_lines WHERE order_id = corresponding_order;
	
	DELETE FROM orders WHERE id = corresponding_order;
END;
$$; 
