package hu.pte.hotelbooking.repository;

import hu.pte.hotelbooking.model.Booking;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // ( Yuka): all bookings of one customer, earliest check-in first
    @Query(value = "SELECT b FROM Booking b WHERE b.customer.id = :customerId ORDER BY b.checkInDate")
    List<Booking> findBookingsByCustomerId(@Param("customerId") Long customerId);
}
