CREATE OR REPLACE FUNCTION func_products_audit_trg()
RETURNS TRIGGER 
AS $body$
BEGIN
	IF (TG_OP = 'INSERT') THEN
		INSERT INTO products_audit
		VALUES (
			nextval('seq_product_audit_id'),
			NEW.id,
			NULL,
			to_jsonb(NEW),
			NEW.inventory_count,
			'ADD',
			CURRENT_TIMESTAMP
		);
		
		RETURN NEW;
	ELSIF (TG_OP = 'UPDATE') THEN
		INSERT INTO products_audit
		VALUES (
			nextval('seq_product_audit_id'),
			NEW.id,
			to_jsonb(OLD),
			to_jsonb(NEW),
			NEW.inventory_count - OLD.inventory_count,
			'MOD',
			CURRENT_TIMESTAMP
		);	
		
		RETURN NEW;
	ELSIF (TG_OP = 'DELETE') THEN
		INSERT INTO products_audit
		VALUES (
			nextval('seq_product_audit_id'),
			OLD.id,
			to_jsonb(OLD),
			NULL,
			0,
			'DEL',
			CURRENT_TIMESTAMP
		);
		
		RETURN OLD;
	END IF;
END;
$body$
language plpgsql;

CREATE TRIGGER trg_populate_products_audit
	AFTER
	INSERT OR UPDATE OR DELETE
	ON products
	FOR EACH ROW
	EXECUTE PROCEDURE func_products_audit_trg();
