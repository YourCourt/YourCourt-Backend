-- LOGINS
INSERT INTO yourcourtdb.login_users (username,password) VALUES('juanogtir','juanogtir');


-- USERS
INSERT INTO yourcourtdb.users (id,name,birth_date,creation_date, membership_number,email,phone,username) VALUES(1,'juan','1999-03-04','2021-01-01', '11111','juanogtir@alum.us.es','634964979','juanogtir');

-- COURT TYPES
INSERT INTO yourcourtdb.court_type (id,name) VALUES(1,'Fast');
INSERT INTO yourcourtdb.court_type (id,name) VALUES(2,'Clay');


-- COURTS
INSERT INTO yourcourtdb.courts (id,name,description,court_type_id) VALUES(1,'Fast Court','This is a fast court.',1);

-- FACILITY TYPES
INSERT INTO yourcourtdb.facility_type (id,name) VALUES(1,'Parking');
INSERT INTO yourcourtdb.facility_type (id,name) VALUES(2,'Cafe');
INSERT INTO yourcourtdb.facility_type (id,name) VALUES(3,'Pool');
INSERT INTO yourcourtdb.facility_type (id,name) VALUES(4,'Other');


-- FACILITIES
INSERT INTO yourcourtdb.facilities (id,name,description,facility_type_id) VALUES(1,'Pool','This is a private pool.',3);

-- PRODUCT TYPES
INSERT INTO yourcourtdb.product_type (id,name) VALUES(1,'Racket');
INSERT INTO yourcourtdb.product_type (id,name) VALUES(2,'Textil');
INSERT INTO yourcourtdb.product_type (id,name) VALUES(3,'Accesories');


-- PRODUCTS
INSERT INTO yourcourtdb.products (id,name,description,product_type_id) VALUES(1,'Grip','This is an accesory.',3);


