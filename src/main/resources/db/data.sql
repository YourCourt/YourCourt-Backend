-- USERS

INSERT INTO roles(id,role_type) VALUES(1,'ROLE_ADMIN');
INSERT INTO roles(id,role_type) VALUES(2,'ROLE_USER');

INSERT INTO users (email,password,username, birth_date,creation_date, membership_number,phone) VALUES
	 ('admin@admin.com','$2y$12$FLo5uFkodDS9NwcfvHH8qunOtZaWT4pg4fNJ2DS.B0B7ZQlCr3yrq','admin','1999-03-04','2021-01-01', '00001','666666666'),
	 ('test223@ewfwef.com','$2y$12$DtMUvLQrUSza72qTBmz1MOhSsSrGrBucXceeRnf4YFEO6v/skNUmC','test_username1','1999-03-04','2021-01-01', '11111','654987321'),
	 ('test224@ewfwef.com','$2y$12$ewuFroLzks6thFWxZVa7EuJ/OQy2ECyLO.kMTjWe4el7kBjlzLEYK','test_username2','1999-03-04','2021-01-01', '22222','654987331'),
	 ('test225@ewfwef.com','$2y$12$ycBsosY.gzUhlYwvEv1HcORck.zjAGalYOanA36x4wWHlqqn.u7ZW','test_username3','1999-03-04','2021-01-01', '33333','654987341');

INSERT INTO users_roles(user_id, role_id) VALUES
	(1,1),
	(1,2),
	(2,2),
	(3,2),
	(4,2);

-- COURTS
INSERT INTO courts (name,description,court_type) VALUES('Fast Court','This is a fast court.','FAST');

-- FACILITY TYPES
INSERT INTO facility_type (id,name) VALUES(1,'Parking');
INSERT INTO facility_type (id,name) VALUES(2,'Cafe');
INSERT INTO facility_type (id,name) VALUES(3,'Pool');
INSERT INTO facility_type (id,name) VALUES(4,'Other');


-- FACILITIES
INSERT INTO facilities (name,description,facility_type_id) VALUES('Pool','This is a private pool.',3);

-- NEWS
INSERT INTO news(name, description, creation_date, edition_date) VALUES('Noticia 1', 'Esto es una noticia', '2021-03-04', '2021-03-04');

-- PRODUCT TYPES
INSERT INTO product_type (id,name) VALUES(1,'Racket');
INSERT INTO product_type (id,name) VALUES(2,'Textil');
INSERT INTO product_type (id,name) VALUES(3,'Accesories');


-- PRODUCTS
INSERT INTO products (name,description,product_type_id) VALUES('Grip','This is an accesory.',3);


