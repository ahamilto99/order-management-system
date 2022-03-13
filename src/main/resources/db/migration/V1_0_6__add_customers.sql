INSERT INTO customers (
	id, 
	first_name, 
	last_name, 
	email, 
	phone, 
	employee_id
) VALUES 
	(NEXTVAL('seq_customer_id'), 'Elysee', 'Geraghty', 'egeraghty0@ycombinator.com', '206-599-2982', 1),
	(NEXTVAL('seq_customer_id'), 'Federica', 'Clayal', 'fclayal1@alexa.com', '560-480-7903', 1),
	(NEXTVAL('seq_customer_id'), 'Dodi', 'Clash', 'dclash2@chron.com', '512-570-5244', 1),
	(NEXTVAL('seq_customer_id'), 'Tomasine', 'Grandham', 'tgrandham3@google.ca', '317-364-5085', 1),
	(NEXTVAL('seq_customer_id'), 'Bevon', 'Maddrah', 'bmaddrah4@sourceforge.net', '410-307-4807', 1),
	(NEXTVAL('seq_customer_id'), 'Kip', 'Cudde', 'kcudde5@w3.org', '532-722-8873', 2),
	(NEXTVAL('seq_customer_id'), 'Roxane', 'Gildersleeve', 'rgildersleeve6@ow.ly', '749-934-0290', 2),
	(NEXTVAL('seq_customer_id'), 'Halette', 'Laffan', 'hlaffan7@theguardian.com', '113-152-7998', 2),
	(NEXTVAL('seq_customer_id'), 'Garey', 'Sutter', 'gsutter8@miitbeian.gov.cn', '195-943-1929', 2),
	(NEXTVAL('seq_customer_id'), 'Bat', 'Bowring', 'bbowring9@vinaora.com', '339-194-6079', 2)
;
