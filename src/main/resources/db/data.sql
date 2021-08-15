
-- IMAGES
 INSERT
	INTO
	images(name, cloudinary_id, image_url)
VALUES ('imagenPorDefecto', NULL, 'https://static.wikia.nocookie.net/mitologa/images/a/a3/Imagen_por_defecto.png/revision/latest?cb=20150824230838&path-prefix=es'),
('ClayCourt', NULL, 'https://cdn.pixabay.com/photo/2015/11/14/07/32/tennis-1042860_960_720.jpg'), -- 2
('FastCourt', NULL, 'https://cdn.pixabay.com/photo/2020/06/29/20/37/asphalt-tennis-court-5354328_960_720.jpg'), -- 3
('Pepe Dominguez campeón provincial 2021', NULL, 'http://itt-sport.com/wp-content/uploads/2013/07/ADRIAN-MEJIAS-Y-JAVIER-VAZQUEZ.jpg;'), -- 4
('Grip tecnifibre', NULL, 'https://www.tennispro.es/media/catalog/product/cache/7/thumbnail/1200x/9df78eab33525d08d6e5fb8d27136e95/5/1/51atpwaf_blanc_1.jpg'), -- 5
('Bote de bolas tecnifibre champion', NULL, 'https://kingame.es/wp-content/uploads/2021/03/Bote-Tecnifibre-Champion.jpg'), -- 6
('Raquetero tecnifibre', NULL, 'https://www.tennispro.es/media/catalog/product/cache/7/thumbnail/1200x/9df78eab33525d08d6e5fb8d27136e95/2/4/241400073_1_1.jpg'), -- 7
('Raqueta tecnifibre T-Fight 300', NULL, 'https://www.tennis-point.es/dw/image/v2/BBDP_PRD/on/demandware.static/-/Sites-master-catalog/default/dw4ade6bf8/images/012/051/01501000_000.jpg?q=80&sw=640'), -- 8
('Zapatillas Lacoste', NULL, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRF0ADzb5RQgNEevQWvrNTFLjLiWJTiqL4ebeQYK1pDcF3NvdBaJAAliM8h7w&usqp=CAc'), -- 9
('Piscina municipal', NULL, 'https://www.doshermanas.net/wp-content/uploads/2020/07/IMG_20200707_124324-e1594299195138.jpg'), -- 10
('Cafe-Bar', NULL, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYuEZuOXxXqsDQV5AvGmYWq00eeOJ76BTOaQ&usqp=CAU'), -- 11
('Fronton', NULL, 'https://www.caspe.es/wp-content/uploads/2020/05/frontenis.jpg'), -- 12
('Parking', NULL, 'https://www.metropoliabierta.com/uploads/s1/15/50/1/IMG_0354_9_1200x480.JPG'), -- 13
('Pista de fútbol', NULL, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSPcqCFGQQjY_ve9II30bIAwFkkW0fDnNdaTw&usqp=CAU'), -- 14
('Batido de proteinas', NULL, 'https://actualnutrition.es/192-pdt_540/proteina-whey-basic.jpg') -- 14


;
-- USERS
 INSERT
	INTO
	roles(id, role_type)
VALUES(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

INSERT
	INTO
	users (email, password, username, birth_date, creation_date, membership_number, phone, image_id)
VALUES ('admin@admin.com', '$2y$12$FLo5uFkodDS9NwcfvHH8qunOtZaWT4pg4fNJ2DS.B0B7ZQlCr3yrq', 'admin', '1999-03-04', '2021-01-01', '00001', '666666666', 1), --1
('test223@ewfwef.com', '$2y$12$DtMUvLQrUSza72qTBmz1MOhSsSrGrBucXceeRnf4YFEO6v/skNUmC', 'test_username1', '1999-03-04', '2021-01-01', '11111', '654987321', 1),--2
('test224@ewfwef.com', '$2y$12$ewuFroLzks6thFWxZVa7EuJ/OQy2ECyLO.kMTjWe4el7kBjlzLEYK', 'test_username2', '1999-03-04', '2021-01-01', '22222', '654987331', 1),--3
('test225@ewfwef.com', '$2y$12$ycBsosY.gzUhlYwvEv1HcORck.zjAGalYOanA36x4wWHlqqn.u7ZW', 'test_username3', '1999-03-04', '2021-01-01', '33333', '654987341', 1), --4
('pepdomaro@gmail.com', 'password001', 'pepdomaro01', '1999-03-04', '2021-01-01', '33333', '611234112', 1), --5
('jacbelgar@gmail.com', 'password002', 'jacbelgar02', '2000-01-09', '2021-01-01', '12333', '692182345', 1), --6
('bragaldom@gmail.com', 'password003', 'jacbelgar03', '2001-06-04', '2021-01-01', '56333', '619543215', 1), --7
('edubotdom@gmail.com', 'password004', 'edubotdom04', '1998-11-01', '2021-01-01', '35433', '687234567', 1), --8
('danaremar@gmail.com', 'password005', 'danaremar05', '1999-02-12', '2021-01-01', '33137', '673928345', 1), --9
('josmarsan@gmail.com', 'password006', 'josmarsan06', '1999-04-11', '2021-01-01', '13437', '645217850', 1), --10
('jossanrui@gmail.com', 'password007', 'jossanrui07', '1999-01-22', '2021-01-01', '13437', '645217850', 1), --11
('erntirvaz@gmail.com', 'password008', 'erntirvaz08', '2002-06-04', '2021-01-01', '67451', '694357185', 1), --12
('rodvelsan@gmail.com', 'password009', 'rodvelsan09', '2003-10-01', '2021-01-01', '43451', '617459735', 1), --13
('jesparfue@gmail.com', 'password010', 'jesparfue10', '2002-09-30', '2021-01-01', '01928', '681757154', 1), --14
('juareadel@gmail.com', 'password011', 'juareadel11', '2004-11-03', '2021-01-01', '81090', '681757154', 1), --15
('javrodher@gmail.com', 'password012', 'javrodher12', '1998-03-06', '2021-01-01', '00123', '604954355', 1), --16
('marlopsal@gmail.com', 'password013', 'marlopsal13', '2000-07-24', '2021-01-01', '10157', '640094012', 1) --17
;

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
VALUES('Pista 1 (rapida)', 'Esta es nuestra primera pista rapida.', 'HARD', 3),
('Pista 3 (tierra)', 'Esta es nuestra primera pista de tierra batida.', 'CLAY', 2),
('Pista 2 (rapida)', 'Esta es nuestra segunda pista rapida.', 'HARD', 3),
('Pista 4 (tierra)', 'Esta es nuestra segunda pista de tierra batida.', 'CLAY', 2),
('Pista 5 (tierra)', 'Esta es nuestra tercera pista de tierra batida.', 'CLAY', 2),
('Pista 6 (tierra)', 'Esta es nuestra cuarta pista de tierra batida.', 'CLAY', 2);
-- FACILITIES
 INSERT
	INTO
	facility_type (type_name)
VALUES ('Parking'),
('Cafe'),
('Piscina'),
('Otro');

INSERT
	INTO
	facilities (name, description, facility_type_id, image_id)
VALUES('Piscina Municipal', 'Piscina municipal del club', 3, 10),
('Cafe-Bar Sánchez', 'Este es un establecimiento publico.', 2, 11),
('Frontón', 'Frontón de tenis para practicar individualmente', 4, 12),
('Parking', 'Parking del club. Entrtada sólo para socios.', 1, 13),
('Pista de fútbol', 'Pista de fútbol no reservable y disponible para todo el que quiera jugar', 4, 14);
-- NEWS
 INSERT
	INTO
	news(name, description, creation_date, edition_date, image_id)
VALUES('Pepe Dominguez, Campeón provincial de Sevilla 2021',
'Pepe Dominguez, jugador de nuestra fantástica escuela de tenis, se ha proclamado este fin de semana, campeón provincial de Sevilla
ganando a Ramón Fuentes en una disputada final 7-5 6-4. Ahora Pepe tiene la mirada puesta en el campeonato regional para el cuál
se ha clasificado.', '2021-03-04', '2021-03-04', 4);

-- COMMENTS
 INSERT
 	INTO
 	comments(content, creation_date, user_id, news_id)
 VALUES ('¡Enhorabuene Pepe, sigue así!', '2021-03-05', 8, 1),
 ('Merecido esfuerzo, no cabe duda ;)', '2021-03-05', 9, 1),
 ('¡Muchas gracias!', '2021-03-05', 5, 1);
-- PRODUCTS
 INSERT
	INTO
	product_type (type_name)
VALUES ('Accesorios'),
('Raqueta'),
('Textil'),
('Otro');

INSERT
	INTO
	products (name, description, stock, tax, price, book_price, product_type_id, image_id)
VALUES('Grip Tecnifibre', 'Grip oficiales de tecnifibre.', 100, 10, 1.00, 3, 1, 5), 
('Bote de bolas Tecnifibre Champion', 'Pelotas de tenis de nivel de competición.', 100, 10, 3.50, 1.00, 1, 6), 
('Raquetero tecnifibre', 'Raquetero tecnifibre de fibra elastica, muy comodo para llevar a los torneos.', 100, 10, 74.99, 0, 3, 7),
('Raqueta Tecnifibre T-Fight 300', 'Raqueta oficial de tecnifibre utilizada por el número 2 del mundo, Daniil Medvedev.', 5, 10, 155.00, 9.00, 2, 8),
('Zapatillas Lacoste', 'Zapatillas hechas gracias a la colaboración con Lacoste.', 30, 10, 120.00, 0, 3, 9),
('Batido de proteinas', 'Bote de proteinas para tener una ganancia extra de fuerza.', 20, 10, 17.55, 0, 4, 15);

-- BOOKINGS
 INSERT
	INTO
	bookings (creation_date, start_date, end_date, user_id, court_id)
VALUES ('1999-03-04', '2022-12-31 20:00','2022-12-31 21:00', 2, 1), --1
('2021-03-04', '2022-02-25 10:00','2023-02-25 11:00', 3, 2), --2
('2021-03-04', '2022-02-25 19:00','2023-02-25 20:00', 6, 1), --3
('2021-03-04', '2023-02-25 12:00','2023-02-25 13:00', 7, 3), --4
('2021-03-04', '2023-02-25 15:00','2023-02-25 16:00', 3, 1), --5
('2021-03-04', '2023-02-25 16:00','2023-02-25 17:00', 4, 4), --6
('2021-03-04', '2023-02-25 18:00','2023-02-25 19:00', 5, 2), --7
('2021-03-04', '2023-02-25 18:00','2023-02-25 19:00', 8, 1) --8
;
-- PRODUCT BOOKINGS
 INSERT
	INTO
	product_booking (booking_id)
VALUES(1), (4), (7);

-- PRODUCT BOOKING LINES
 INSERT
	INTO
	product_booking_lines (quantity, discount, product_booking_id, product_id)

VALUES(1, 2.5, 1, 2),
(1, 2.5, 2, 2),
(2, 2.5, 2, 4),
(1, 2.5, 3, 1)
;

-- PRODUCT PURCHASE
 INSERT
	INTO
	product_purchase (creation_date, user_id)
VALUES ('2000-07-15', 2), --1
('2002-01-20', 3), --2
('2015-11-25', 5), --3
('2020-07-17', 8), --4
('2017-03-01', 7), --5
('2021-2-27', 6), --6
('2014-06-22', 2) --7
;

-- PRODUCT PURCHASE LINES
 INSERT
	INTO
	product_purchase_lines (quantity, discount, product_purchase_id, product_id)
VALUES(1, 2.5, 1, 2),
(1, 2.5, 2, 2),
(2, 2.5, 2, 4),
(1, 2.5, 3, 1),
(3, 12.5, 4, 2),
(5, 0.5, 5, 6),
(2, 0.0, 6, 3),
(1, 1.5, 7, 1),
(1, 0.0, 7, 2)
;

-- COURSES
 INSERT
	INTO
	courses (title, description, start_date, end_date)
VALUES('Campus de verano', 'Inscripciones abiertas para el campus de vernao de nuestra escuela técnica. Sin duda una gran oportunidad
para todos aquellos que se quieran iniciar en el tenis y/o pádel de la forma más divertida. ¡Inscríbete ya! ', '2021-07-01', '2021-07-30'),
('Curso intensivo de verano', 'De nuevo nuestra escuela técnica inicia un intensivo para los jugadores de comepteción en el mes de Julio para que 
puedan seguir entrenando. ¡El esfuerzo y la dedicación es lo más imporante!', '2022-07-01', '2022-07-30');


-- INSCRIPTIONS
 INSERT
	INTO
	inscriptions (name, surnames, email, phone, observations, user_id, course_id)
VALUES('Juan', 'Noguerol Tirado', 'ejemplo@gmail.com', '612934234', "Ninguna", 1, 2),
('Pepe', 'Dominguez Aro ', 'pepdomaro@gmail.com', '611234112', 'No puedo ir los miércoles', 5, 1),
('Jacinto', 'Belindo Garrido', 'jacbelgar@gmail.com', '692182345', "Ninguna",  6, 2),
('Brayan', 'Galán Domingo', 'bragaldom@gmail.com', '619543215', "Ninguna",  7, 2),
('Eduardo', 'Botía Domingo', 'edubotdom@gmail.com', '687234567', "Ninguna",  8, 2),
('Daniel', 'Arellano Martinez', 'danaremar@gmail.com', '673928345', 'No tengo raqueta de tenis',  9, 2),
('Jose', 'Martin Sánchez', 'josmarsan@gmail.com', '645217850', "Ninguna",  10, 2),
('Jose Manuel', 'Sánchez Ruiz', 'jossanrui@gmail.com', '685043928', "Ninguna",  11, 2),
('Ernesto', 'Tirado Vázquez', 'erntirvaz@gmail.com', '694357185', "Ninguna",  12, 2),
('Rodrigo', 'Velázquez Sánchez', 'rodvelsan@gmail.com', '617459735', 'No tengo nadie que me pueda recoger una vez finalizada la jornada',  13, 2),
('Jesús', 'Parrilla de la Fuente', 'jesparfue@gmail.com', '681757154', "Ninguna",  14, 2),
('Juan', 'real de la Casa', 'juareadel@gmail.com', '687149514', "Ninguna",  15, 2),
('Javier', 'Rodríguez Hernández', 'javrodher@gmail.com', '604954355', "Ninguna",  16, 2),
('Mario', 'López Salas', 'marlopsal@gmail.com', '640094012', "Ninguna",  17, 2);

