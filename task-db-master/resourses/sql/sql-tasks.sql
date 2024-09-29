-- 1 Вывести к каждому самолету класс обслуживания и количество мест этого класса
SELECT a.model, s.fare_conditions, COUNT(s.seat_no)
FROM aircrafts a
JOIN seats s ON a.aircraft_code=s.aircraft_code
GROUP BY a.model, s.fare_conditions
ORDER BY a.model

-- 2 Найти 3 самых вместительных самолета (модель + кол-во мест)
SELECT a.model, COUNT(s.seat_no) AS seats_count
FROM aircrafts a
JOIN seats s ON a.aircraft_code=s.aircraft_code
GROUP BY a.model
ORDER BY seats_count DESC
LIMIT 3

-- 3 Найти все рейсы, которые задерживались более 2 часов
SELECT *
FROM flights f
WHERE DATE_PART('hour', f.actual_arrival::timestamp - f.scheduled_arrival::timestamp) > 2
ORDER BY f.flight_no

-- 4 Найти последние 10 билетов, купленные в бизнес-классе (fare_conditions = 'Business'), с указанием имени пассажира и контактных данных
SELECT b.book_date, t.ticket_no, t.passenger_name, t.contact_data
FROM bookings b
JOIN tickets t ON t.book_ref=b.book_ref
JOIN ticket_flights AS tf ON tf.ticket_no=t.ticket_no
WHERE tf.fare_conditions='Business'
ORDER BY b.book_date DESC
LIMIT 10

-- 5 Найти все рейсы, у которых нет забронированных мест в бизнес-классе (fare_conditions = 'Business')
SELECT *
FROM flights f
WHERE f.flight_no NOT IN (
	SELECT distinct f.flight_no
	FROM flights f
	JOIN ticket_flights tf ON f.flight_id=tf.flight_id
	JOIN tickets t ON t.ticket_no=tf.ticket_no
	WHERE tf.fare_conditions = 'Business'
)
ORDER BY f.flight_no

-- 6 Получить список аэропортов (airport_name) и городов (city), в которых есть рейсы с задержкой по вылету
SELECT a.airport_name, a.city
FROM airports a
WHERE a.airport_code IN (
	SELECT f.departure_airport from flights f
	WHERE DATE_PART('minute', f.actual_departure::timestamp - f.scheduled_departure::TIMESTAMP) > 0
)

-- 7 Получить список аэропортов (airport_name) и количество рейсов, вылетающих из каждого аэропорта, отсортированный по убыванию количества рейсов
SELECT a.airport_name, COUNT(f.scheduled_departure) AS sd
FROM airports a
JOIN flights f ON f.departure_airport=a.airport_code
GROUP BY a.airport_name
ORDER BY sd DESC

-- 8 Найти все рейсы, у которых запланированное время прибытия (scheduled_arrival) было изменено и новое время прибытия (actual_arrival) не совпадает с запланированным
SELECT *
FROM flights f
WHERE f.scheduled_arrival != f.actual_arrival

-- 9 Вывести код, модель самолета и места не эконом класса для самолета "Аэробус A321-200" с сортировкой по местам
SELECT a.aircraft_code, a.model, s.seat_no
FROM aircrafts a
JOIN seats s ON s.aircraft_code=a.aircraft_code
WHERE s.fare_conditions != 'Economy'
AND a.model = 'Аэробус A321-200'
ORDER BY s.seat_no

-- 10 Вывести города, в которых больше 1 аэропорта (код аэропорта, аэропорт, город)
SELECT a.city
FROM airports a
GROUP BY a.city
HAVING COUNT(a.airport_name) > 1

-- 11 Найти пассажиров, у которых суммарная стоимость бронирований превышает среднюю сумму всех бронирований
SELECT t.passenger_name, SUM(b.total_amount) AS total
FROM tickets t
JOIN bookings b ON b.book_ref=t.book_ref
GROUP BY t.passenger_name
HAVING SUM(b.total_amount) > (
	SELECT AVG(b.total_amount) AS avg
	FROM bookings b
)

-- 12 Найти ближайший вылетающий рейс из Екатеринбурга в Москву, на который еще не завершилась регистрация
SELECT *
FROM flights f
JOIN airports ad ON ad.airport_code=f.departure_airport
JOIN airports aa ON aa.airport_code=f.arrival_airport
WHERE f."status" IN ('On Time', 'Delayed')
AND ad.city = 'Екатеринбург'
AND aa.city = 'Москва'
ORDER BY f.scheduled_departure DESC
LIMIT 1

-- 13 Вывести самый дешевый и дорогой билет и стоимость (в одном результирующем ответе)
SELECT t.ticket_no, tf.amount
FROM ticket_flights tf
JOIN tickets t ON t.ticket_no=tf.ticket_no
WHERE tf.amount IN (
SELECT MAX(tf.amount) FROM ticket_flights tf
UNION
SELECT MIN(tf.amount) FROM ticket_flights tf
)

-- 14 Написать DDL таблицы Customers, должны быть поля id, firstName, LastName, email, phone. Добавить ограничения на поля (constraints)
CREATE TABLE bookings.customers
(customer_id VARCHAR(20) CONSTRAINT customer_pk PRIMARY KEY,
fist_name text NOT NULL,
last_name text NOT NULL,
email text,
phone text NOT NULL,
CONSTRAINT email_check CHECK(email ~ '[a-zA-Z0-9.-]+(.[a-zA-Z]{2,})+'),
CONSTRAINT phone_check CHECK(phone ~ '^[+][0-9]{0,15}$'))

-- 15 Написать DDL таблицы Orders, должен быть id, customerId, quantity. Должен быть внешний ключ на таблицу customers + constraints
CREATE TABLE bookings.orders
(id serial CONSTRAINT order_pk PRIMARY KEY,
customer_id VARCHAR(20) CONSTRAINT customer_fk REFERENCES customers(customer_id) ON DELETE CASCADE NOT NULL,
quantity INTEGER)

-- 16 Написать 5 insert в эти таблицы
INSERT INTO customers (customer_id, fist_name, last_name, email, phone)
SELECT t.passenger_id,
	split_part(t.passenger_name, ' ', 1),
	split_part(t.passenger_name, ' ', 2),
	t.contact_data::json->'email',
	regexp_replace((t.contact_data::json->'phone')::TEXT, '"', '', 'g')
	FROM tickets t
	WHERE t.passenger_id = '0002 556058'
	 or t.passenger_id = '0002 895341'
	 or t.passenger_id = '0003 797170'
	 or t.passenger_id = '0004 079767'
	 OR t.passenger_id = '0025 516345'
	 OR t.passenger_id = '0025 685904'

INSERT INTO customers (customer_id, fist_name, last_name, email, phone)
VALUES ('0025 874974', 'NINA', 'EGOROVA', NULL, '+70880080035')

INSERT INTO customers (customer_id, fist_name, last_name, email, phone)
SELECT distinct t.passenger_id,
	split_part(t.passenger_name, ' ', 1),
	split_part(t.passenger_name, ' ', 2),
	(t.contact_data::json->'email')::text,
	regexp_replace((t.contact_data::json->'phone')::TEXT, '"', '', 'g')
	FROM tickets t
	WHERE t.passenger_name = 'TAMARA MIRONOVA'
	 or t.passenger_name = 'ALEKSEY ISAEV'
	 or t.passenger_name = 'ALISA KUZMINA'
	 or t.passenger_name = 'MARGARITA EGOROVA'

INSERT INTO orders (customer_id, quantity)
    SELECT c.customer_id, COUNT(*) AS amount
    FROM customers c
    LEFT JOIN tickets t ON t.passenger_id=c.customer_id
    GROUP BY c.customer_id

INSERT INTO orders (customer_id, quantity)
VALUES
	('0003 797170', 0),
	('0131 684155', 0)

-- 17 Удалить таблицы
DROP TABLE bookings.customers CASCADE;
DROP TABLE bookings.orders;


