-- USERS

INSERT INTO roles(id, role_type) VALUES(1, 'ROLE_ADMIN');
INSERT INTO roles(id, role_type) VALUES(2, 'ROLE_USER');

INSERT INTO users (id,email,password,username, birth_date,creation_date, membership_number,phone) VALUES
	 (1,'admin@admin.com','admin','admin','1999-03-04','2021-01-01', '00001','666666666'),
	 (2,'test223@ewfwef.com','test_password1','test_username1','1999-03-04','2021-01-01', '11111','654987321'),
	 (3,'test224@ewfwef.com','test_password2','test_username2','1999-03-04','2021-01-01', '22222','654987331'),
	 (4,'test225@ewfwef.com','test_password3','test_username3','1999-03-04','2021-01-01', '22222','654987341');

INSERT INTO users_roles(user_id, role_id) VALUES
	(1,1),
	(1,2),
	(2,2),
	(3,2),
	(4,2);

-- COURTS
INSERT INTO courts (id,name,description,court_type) VALUES(1,'Fast Court','This is a fast court.','FAST');

-- FACILITY TYPES
INSERT INTO facility_type (id,name) VALUES(1,'Parking');
INSERT INTO facility_type (id,name) VALUES(2,'Cafe');
INSERT INTO facility_type (id,name) VALUES(3,'Pool');
INSERT INTO facility_type (id,name) VALUES(4,'Other');


-- FACILITIES
INSERT INTO facilities (id,name,description,facility_type_id) VALUES(1,'Pool','This is a private pool.',3);

-- PRODUCT TYPES
INSERT INTO product_type (id,name) VALUES(1,'Racket');
INSERT INTO product_type (id,name) VALUES(2,'Textil');
INSERT INTO product_type (id,name) VALUES(3,'Accesories');


-- PRODUCTS
INSERT INTO products (id,name,description,product_type_id) VALUES(1,'Grip','This is an accesory.',3);


