-- 02_seed_data.sql | owner: lead | run after 01
USE hotel_booking;

INSERT INTO customer (id, full_name, email, phone, address, registration_date) VALUES
(1, 'Bat-Erdene Ganbold', 'bat.erdene@example.com', '+976 9911 2233', 'Ulaanbaatar, Sukhbaatar district', '2026-09-01'),
(2, 'Anna Kovacs',        'anna.kovacs@example.com', '+36 30 123 4567', 'Pecs, Rakoczi ut 10',             '2026-09-15'),
(3, 'Tamas Nagy',         'tamas.nagy@example.com',  '+36 20 765 4321', 'Budapest, Andrassy ut 5',         '2026-10-02'),
(4, 'Saruul Bold',        'saruul.bold@example.com', '+976 8800 1122',  'Darkhan',                         '2026-10-20'),
(5, 'Maria Rossi',        'maria.rossi@example.com', '+39 333 444 5555','Milan, Via Roma 3',               '2026-11-01');

INSERT INTO room (id, room_number, room_type, price_per_night, capacity, floor, status) VALUES
(1, '101', 'SINGLE',  45.00, 1, 1, 'AVAILABLE'),
(2, '102', 'DOUBLE',  70.00, 2, 1, 'AVAILABLE'),
(3, '201', 'DOUBLE',  75.00, 2, 2, 'AVAILABLE'),
(4, '202', 'SUITE',  150.00, 4, 2, 'AVAILABLE'),
(5, '301', 'SINGLE',  50.00, 1, 3, 'MAINTENANCE'),
(6, '302', 'SUITE',  160.00, 4, 3, 'AVAILABLE');

INSERT INTO booking (id, customer_id, room_id, check_in_date, check_out_date, total_price, status, booking_date) VALUES
(1, 1, 2, '2026-12-01', '2026-12-04', 210.00, 'CONFIRMED', '2026-11-05'),
(2, 2, 4, '2026-12-10', '2026-12-12', 300.00, 'CONFIRMED', '2026-11-06'),
(3, 3, 1, '2026-10-28', '2026-10-31', 135.00, 'COMPLETED', '2026-10-20'),
(4, 4, 3, '2026-12-20', '2026-12-27', 525.00, 'CONFIRMED', '2026-11-08'),
(5, 5, 6, '2026-12-24', '2026-12-26', 320.00, 'CANCELLED', '2026-11-09'),
(6, 1, 1, '2027-01-05', '2027-01-07',  90.00, 'CONFIRMED', '2026-11-10');

INSERT INTO payment (id, booking_id, amount, payment_method, status, payment_date) VALUES
(1, 1, 210.00, 'CARD',     'PAID',     '2026-11-05 14:30:00'),
(2, 2, 150.00, 'TRANSFER', 'PENDING',  '2026-11-06 10:00:00'),
(3, 3, 135.00, 'CASH',     'PAID',     '2026-10-31 11:00:00'),
(4, 4, 525.00, 'CARD',     'PAID',     '2026-11-08 18:45:00'),
(5, 5, 320.00, 'CARD',     'REFUNDED', '2026-11-09 09:15:00');
