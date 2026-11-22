-- booking_procedures.sql | owner: Yuka
USE hotel_booking;
DELIMITER //

DROP PROCEDURE IF EXISTS calculateBookingTotal //
CREATE PROCEDURE calculateBookingTotal(IN bookingIdIN BIGINT, OUT totalOUT DECIMAL(10,2))
BEGIN
    SELECT DATEDIFF(b.check_out_date, b.check_in_date) * r.price_per_night
    INTO totalOUT
    FROM booking b
    JOIN room r ON r.id = b.room_id
    WHERE b.id = bookingIdIN;

    UPDATE booking SET total_price = totalOUT WHERE id = bookingIdIN;
END //

DELIMITER ;
