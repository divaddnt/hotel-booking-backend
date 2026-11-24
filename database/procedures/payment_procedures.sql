USE hotel_booking;
DELIMITER //

DROP PROCEDURE IF EXISTS getRemainingBalance //
CREATE PROCEDURE getRemainingBalance(IN bookingIdIN BIGINT, OUT remainingOUT DECIMAL(10,2))
BEGIN
    SELECT b.total_price - COALESCE(SUM(p.amount), 0)
    INTO remainingOUT
    FROM booking b
    LEFT JOIN payment p ON p.booking_id = b.id AND p.status = 'PAID'
    WHERE b.id = bookingIdIN
    GROUP BY b.id, b.total_price;
END //

DELIMITER ;
