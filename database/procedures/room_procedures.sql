USE hotel_booking;
DELIMITER //

DROP PROCEDURE IF EXISTS getAvailableRooms //
CREATE PROCEDURE getAvailableRooms(IN checkInIN DATE, IN checkOutIN DATE)
BEGIN
    SELECT r.*
    FROM room r
    WHERE r.status = 'AVAILABLE'
      AND r.id NOT IN (
          SELECT b.room_id
          FROM booking b
          WHERE b.status <> 'CANCELLED'
            AND b.check_in_date < checkOutIN
            AND b.check_out_date > checkInIN
      );
END //

DELIMITER ;
