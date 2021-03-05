-- LOGINS
INSERT INTO yourcourtdb.logins (username,enabled,password) VALUES('juanogtir',1,'juanogtir');


-- USERS
INSERT INTO yourcourtdb.users (id,name,birth_date,creation_date, membership_number,email,phone,username) VALUES(1,'juan','1999-03-04','2021-01-01', '11111','juanogtir@alum.us.es','634964979','juanogtir');

	 
-- AUTHORITIES
INSERT INTO yourcourtdb.authorities (username,authority) VALUES('juanogtir','admin');


-- COURT TYPES
INSERT INTO yourcourtdb.court_type (id,name) VALUES(1,'Fast');
INSERT INTO yourcourtdb.court_type (id,name) VALUES(2,'Clay');


-- COURTS
INSERT INTO yourcourtdb.courts (id,name,description,court_type_id) VALUES(1,'Fast Court','This is a fast court.',1);
