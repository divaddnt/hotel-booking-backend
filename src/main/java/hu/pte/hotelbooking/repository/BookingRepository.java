package hu.pte.hotelbooking.repository;

import hu.pte.hotelbooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Member 3 adds the custom query here
}