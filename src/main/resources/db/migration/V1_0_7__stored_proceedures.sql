CREATE PROCEDURE add_order_line_usp(
	corresponding_order BIGINT, 
	product_ordered UUID,
	qty_ordered INT
) 
LANGUAGE plpgsql
AS $$
DECLARE 
	unit_price NUMERIC(11, 2); 
BEGIN
	IF (quantity_ordred > (SELECT inventory_count FROM products WHERE id = product_ordered)) THEN
		RAISE EXCEPTION 'Not enough product in stock';
	END IF;
	
	unit_price = (SELECT msrp FROM products WHERE id = product_ordered);
	
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
	
	UPDATE products SET inventory_count = (inventory_count - quantity_ordered) WHERE id = product_ordered; 
	
	UPDATE orders SET total = (total + (unit_price * qty_ordered)) WHERE id = corresponding_order;
		
	COMMIT;
END;
$$;

CREATE PROCEDURE update_order_line_usp(
	corresponding_order BIGINT,
	line_num INT, 
	product UUID,
	new_qty INT
)  
LANGUAGE plpgsql
AS $$
DECLARE 
	qty_delta INT := (new_qty - (SELECT qantity FROM order_lines WHERE order_id = corresponding_order AND line_number = line_num)); 	
	unit_price NUMERIC(11, 2) := (SELECT msrp FROM products WHERE id = product);
BEGIN
	IF (qty_delta > (SELECT qantity FROM order_lines WHERE order_id = corresponding_order AND line_number = line_num)) THEN 
		RAISE EXCEPTION 'Not enough product in stock';
	END IF;
	
	UPDATE order_lines SET quantity = new_qty, msrp = unit_price WHERE order_id = corresponding_order AND line_number = line_num;
	
	UPDATE products SET inventory_count = (inventory_count + qty_delta) WHERE id = product;
	
	UPDATE orders SET total = (SELECT SUM(quantity * msrp) FROM order_lines WHERE id = corresponding_order);

	COMMIT;
END;
$$;

CREATE PROCEDURE delete_order_line_usp(
	corresponding_order BIGINT,
	line INT
)
LANGUAGE plpgsql
AS $$
DECLARE
	product BIGINT;
	qty INT;
BEGIN
	product := (SELECT product_id FROM order_lines WHERE order_id = corresponding_order AND line_number = line);
	qty := (SELECT quantity FROM order_lines WHERE order_id = corresponding_order AND line_number = line);
	
	UPDATE products SET inventory_count = (invetory_count + qty) WHERE id = product;
	
	DELETE FROM order_lines WHERE order_id = corresponding_order AND line_number = line;
	
	UPDATE orders SET total = (SELECT SUM(quantity * msrp) FROM orders WHERE id = corresponding_order);

	COMMIT;
END;
$$;

-- TODO: move to separate file

CREATE FUNCTION calc_order_total(order_num BIGINT) RETURNS NUMERIC(11, 2)
AS $$
BEGIN
	RETURN (SELECT SUM(quantity * msrp) FROM order_lines WHERE order_id = order_num);
END;
$$ LANGUAGE plpgsql;

------------------------------

-- TODO: DELETE order USP
--CREATE PROCEDURE delete_order_usp()

