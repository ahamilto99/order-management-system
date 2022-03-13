INSERT INTO employees ( 
	id,
	jwt_sub, 
	first_name, 
	last_name,
	email, 
	phone 
) VALUES 
	(NEXTVAL('seq_employee_id'), '19fce851-e677-42e5-807b-356eb48a4329', 'Alexander', 'Hamilton', 'alexander.hamilton@oms.com', '613-555-4106'),
	(NEXTVAL('seq_employee_id'), '473f2595-c009-4529-91e7-735180174f14', 'Keith', 'Coady', 'keith.coady@oms.com', '613-555-1016'),
	(NEXTVAL('seq_employee_id'), 'd73af0e7-d2e9-477a-9035-782bf7e18619', 'Cathy', 'Coady', 'cathy.coady@oms.com', '613-555-7278')
;
