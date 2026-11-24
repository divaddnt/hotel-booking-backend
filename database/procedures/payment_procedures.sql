USE hotel_booking;
DELIMITER //

DROP PROCEDURE IF EXISTS getRemainingBalance //
CREATE PROCEDURE getRemainingBalance(IN bookingIdIN BIGINT, OUT remainingOUT DECIMAL(10,2))
BEGIN
    DECLARE totalPrice DECIMAL(10,2);
    DECLARE paidAmount DECIMAL(10,2);

    -- the total price of the booking
    SELECT total_price INTO totalPrice
    FROM booking
    WHERE id = bookingIdIN;
    -- how much was already paid (only PAID payments count)
    SELECT IFNULL(SUM(amount), 0) INTO paidAmount
    FROM payment
    WHERE booking_id = bookingIdIN AND status = 'PAID';
    SET remainingOUT = totalPrice - paidAmount;
END //

DELIMITER ;