package hu.pte.hotelbooking.repository;

import hu.pte.hotelbooking.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Member 4 adds the custom query here
}