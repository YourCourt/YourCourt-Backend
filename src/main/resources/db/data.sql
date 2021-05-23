-- IMAGES
 INSERT
	INTO
	images(name, cloudinary_id, image_url)
VALUES ('imagenPorDefecto', NULL, 'https://static.wikia.nocookie.net/mitologa/images/a/a3/Imagen_por_defecto.png/revision/latest?cb=20150824230838&path-prefix=es'),
('ClayCourt', NULL, 'https://cdn.pixabay.com/photo/2015/11/14/07/32/tennis-1042860_960_720.jpg'),
('FastCourt', NULL, 'https://cdn.pixabay.com/photo/2020/06/29/20/37/asphalt-tennis-court-5354328_960_720.jpg');
-- USERS
 INSERT
	INTO
	roles(id, role_type)
VALUES(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

INSERT
	INTO
	users (email, password, username, birth_date, creation_date, membership_number, phone, image_id)
VALUES ('admin@admin.com', '$2y$12$FLo5uFkodDS9NwcfvHH8qunOtZaWT4pg4fNJ2DS.B0B7ZQlCr3yrq', 'admin', '1999-03-04', '2021-01-01', '00001', '666666666', 1),
('test223@ewfwef.com', '$2y$12$DtMUvLQrUSza72qTBmz1MOhSsSrGrBucXceeRnf4YFEO6v/skNUmC', 'test_username1', '1999-03-04', '2021-01-01', '11111', '654987321', 1),
('test224@ewfwef.com', '$2y$12$ewuFroLzks6thFWxZVa7EuJ/OQy2ECyLO.kMTjWe4el7kBjlzLEYK', 'test_username2', '1999-03-04', '2021-01-01', '22222', '654987331', 1),
('test225@ewfwef.com', '$2y$12$ycBsosY.gzUhlYwvEv1HcORck.zjAGalYOanA36x4wWHlqqn.u7ZW', 'test_username3', '1999-03-04', '2021-01-01', '33333', '654987341', 1);

INSERT
	INTO
	users_roles(user_id, role_id)
VALUES (1, 1),
(1, 2),
(2, 2),
(3, 2),
(4, 2);
-- COURTS
 INSERT
	INTO
	courts (name, description, court_type, image_id)
VALUES('Primera pista rapida', 'Esta es nuestra primera pista rapida.', 'FAST', 1),
('Primera pista de tierra batida', 'Esta es nuestra primera pista de tierra batida.', 'CLAY', 2),
('Segunda pista rapida', 'Esta es nuestra segunda pista rapida.', 'FAST', 1),
('Segunda pista de tierra batida', 'Esta es nuestra segunda pista de tierra batida.', 'CLAY', 2);
-- FACILITIES
 INSERT
	INTO
	facilities (name, description, facility_type, image_id)
VALUES('Pool', 'This is a private pool.', 'POOL',1);

-- NEWS
 INSERT
	INTO
	news(name, description, creation_date, edition_date, image_id)
VALUES('Noticia 1', 'Esto es una noticia', '2021-03-04', '2021-03-04',1);

-- PRODUCTS
 INSERT
	INTO
	products (name, description, product_type, image_id)
VALUES('Grip', 'Esto es un accesorio.', 'ACCESORIES',1);

-- COURSES
 INSERT
	INTO
	courses (title, description, start_date, end_date)
VALUES('Campus de verano', 'Inscripción para el campus de vernao de nuestra escuela técnica', '2021-07-01', '2021-07-30'); --1

-- INSCRIPTIONS
 INSERT
	INTO
	inscriptions (name, surnames, email, phone, user_id, course_id)
VALUES('Juan', 'Noguerol Tirado', 'ejemplo@gmail.com', '612934234', 1, 1); --1
