SELECT a.model, s.fare_conditions, COUNT(s.seat_no)
FROM aircrafts AS a
JOIN seats AS s ON a.aircraft_code=s.aircraft_code
GROUP BY a.model, s.fare_conditions
ORDER BY a.model

SELECT a.model, COUNT(s.seat_no) AS seats_count
FROM aircrafts AS a
JOIN seats AS s ON a.aircraft_code=s.aircraft_code
GROUP BY a.model
ORDER BY seats_count DESC
LIMIT 3

SELECT b.book_date, t.ticket_no, t.passenger_name, t.contact_data
FROM bookings AS b
JOIN tickets AS t ON t.book_ref=b.book_ref
JOIN ticket_flights AS tf ON tf.ticket_no=t.ticket_no
WHERE tf.fare_conditions='Business'
ORDER BY b.book_date DESC
LIMIT 10

