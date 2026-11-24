package hu.pte.hotelbooking.repository;

import hu.pte.hotelbooking.model.Payment;
<<<<<<< HEAD
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Custom query: all payments with a given status (PENDING, PAID, REFUNDED)
    @Query("SELECT p FROM Payment p WHERE p.status = :status ORDER BY p.paymentDate DESC")
    List<Payment> findPaymentsByStatus(@Param("status") String status);
=======
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Member 4 adds the custom query here
>>>>>>> origin/main
}