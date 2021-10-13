CREATE OR REPLACE FUNCTION func_inventory_management_trg()
RETURNS TRIGGER
AS $body$
DECLARE
	stock INTEGER;
	price MONEY;
BEGIN
	CASE
		WHEN TG_OP = 'INSERT' THEN
			stock := (SELECT inventory_count FROM products WHERE id = NEW.product_id);
			price := (SELECT mrsp FROM products WHERE id = NEW.product_id);
			
			IF (stock < NEW.quantity) THEN
				RAISE EXCEPTION 'Not enough product in stock';
			END IF;
			
			UPDATE products 
				SET inventory_count = inventory_count - NEW.quantity
			WHERE id = NEW.product_id;
		WHEN TG_OP = 'UPDATE' THEN
			stock := (SELECT inventory_count FROM products WHERE id = OLD.product_id);
			price := (SELECT mrsp FROM products WHERE id = OLD.product_id);
			
			IF (stock < NEW.quantity - OLD.quantity) THEN
				RAISE EXCEPTION 'Not enough product in stock';
			END IF;
			
			UPDATE products
				SET inventory_count = inventory_count - (NEW.quantity - OLD.quantity)
			WHERE id = OLD.product_id;
		WHEN TG_OP = 'DELETE' THEN
			UPDATE products
				SET inventory_count = inventory_count + OLD.quantity
			WHERE id = OLD.product_id;
	END CASE;
END;
$body$
language plpgsql;

CREATE TRIGGER trg_inventory_management
	BEFORE 
	INSERT OR UPDATE OR DELETE 
	ON order_lines
	FOR EACH ROW
	EXECUTE PROCEDURE func_inventory_management_trg();
