USE hotel_booking;
DELIMITER //

DROP PROCEDURE IF EXISTS countCustomers //
CREATE PROCEDURE countCustomers(OUT countOUT INT)
BEGIN
    SELECT COUNT(*) INTO countOUT FROM customer;
END //
