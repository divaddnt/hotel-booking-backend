package hu.pte.hotelbooking.service;

<<<<<<< HEAD
import hu.pte.hotelbooking.exception.InvalidRequestException;
import hu.pte.hotelbooking.exception.ResourceNotFoundException;
import hu.pte.hotelbooking.model.Booking;
import hu.pte.hotelbooking.model.Payment;
import hu.pte.hotelbooking.repository.BookingRepository;
import hu.pte.hotelbooking.repository.PaymentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentService {

    private static final List<String> STATUSES = List.of("PENDING", "PAID", "REFUNDED");

    @Autowired
    private PaymentRepository repo;

    @Autowired
    private BookingRepository bookingRepo;

    @PersistenceContext
    private EntityManager em;

    // ---------- CRUD (JPA) ----------

=======
import hu.pte.hotelbooking.exception.ResourceNotFoundException;
import hu.pte.hotelbooking.model.Payment;
import hu.pte.hotelbooking.repository.PaymentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repo;

>>>>>>> origin/main
    public List<Payment> getAllPaymentsJPA() {
        return repo.findAll();
    }

    public Payment getPaymentByIdJPA(Long id) {
        return repo.findById(id)
<<<<<<< HEAD
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
    }

    public Payment addPaymentJPA(Payment payment) {
        payment.setBooking(findBooking(payment));
        checkAmount(payment.getAmount());
        if (payment.getPaymentMethod() == null) {
            throw new InvalidRequestException("paymentMethod is required (CARD, CASH, TRANSFER)");
        }
        if (payment.getStatus() == null) {
            payment.setStatus("PENDING");
        }
        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDateTime.now());
        }
=======
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id " + id));
    }

    public Payment addPaymentJPA(Payment payment) {
>>>>>>> origin/main
        return repo.save(payment);
    }

    public Payment updatePaymentJPA(Long id, Payment payment) {
        Payment existing = getPaymentByIdJPA(id);
<<<<<<< HEAD
        checkAmount(payment.getAmount());
        existing.setBooking(findBooking(payment));
        existing.setAmount(payment.getAmount());
        if (payment.getPaymentMethod() != null) existing.setPaymentMethod(payment.getPaymentMethod());
        if (payment.getStatus() != null)        existing.setStatus(payment.getStatus());
        if (payment.getPaymentDate() != null)   existing.setPaymentDate(payment.getPaymentDate());
=======
        existing.setBooking(payment.getBooking());
        existing.setAmount(payment.getAmount());
        existing.setPaymentMethod(payment.getPaymentMethod());
        existing.setStatus(payment.getStatus());
        existing.setPaymentDate(payment.getPaymentDate());
>>>>>>> origin/main
        return repo.save(existing);
    }

    public void deletePaymentJPA(Long id) {
<<<<<<< HEAD
        Payment payment = getPaymentByIdJPA(id);
        repo.delete(payment);
    }

    // Stored procedure

    public BigDecimal getRemainingBalanceSPQ(Long bookingId) {
        if (!bookingRepo.existsById(bookingId)) {
            throw new ResourceNotFoundException("Booking with id: " + bookingId + " not found!");
        }
        StoredProcedureQuery spq = em.createStoredProcedureQuery("getRemainingBalance");
        spq.registerStoredProcedureParameter("bookingIdIN", Long.class, ParameterMode.IN);
        spq.registerStoredProcedureParameter("remainingOUT", BigDecimal.class, ParameterMode.OUT);
        spq.setParameter("bookingIdIN", bookingId);
        spq.execute();
        return (BigDecimal) spq.getOutputParameterValue("remainingOUT");
    }

    // Custom query

    public List<Payment> findPaymentsByStatus(String status) {
        String upper = status.toUpperCase();
        if (!STATUSES.contains(upper)) {
            throw new InvalidRequestException("Status must be one of: " + STATUSES);
        }
        List<Payment> result = repo.findPaymentsByStatus(upper);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("No payments with status: " + status);
        }
        return result;
    }

    // Helper
    private void checkAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidRequestException("Amount must be greater than 0!");
        }
    }

    // The JSON body only needs "booking": { "id": 1 }; this loads the full booking
    private Booking findBooking(Payment payment) {
        if (payment.getBooking() == null || payment.getBooking().getId() == null) {
            throw new InvalidRequestException("Payment must have a booking id");
        }
        Long bookingId = payment.getBooking().getId();
        return bookingRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
=======
        Payment existing = getPaymentByIdJPA(id);
        repo.delete(existing);
>>>>>>> origin/main
    }
}