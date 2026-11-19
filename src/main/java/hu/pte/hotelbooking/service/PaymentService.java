package hu.pte.hotelbooking.service;

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

    public List<Payment> getAllPaymentsJPA() {
        return repo.findAll();
    }

    public Payment getPaymentByIdJPA(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id " + id));
    }

    public Payment addPaymentJPA(Payment payment) {
        return repo.save(payment);
    }

    public Payment updatePaymentJPA(Long id, Payment payment) {
        Payment existing = getPaymentByIdJPA(id);
        existing.setBooking(payment.getBooking());
        existing.setAmount(payment.getAmount());
        existing.setPaymentMethod(payment.getPaymentMethod());
        existing.setStatus(payment.getStatus());
        existing.setPaymentDate(payment.getPaymentDate());
        return repo.save(existing);
    }

    public void deletePaymentJPA(Long id) {
        Payment existing = getPaymentByIdJPA(id);
        repo.delete(existing);
    }
}