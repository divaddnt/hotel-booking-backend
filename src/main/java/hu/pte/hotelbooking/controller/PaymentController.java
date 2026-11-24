package hu.pte.hotelbooking.controller;

import hu.pte.hotelbooking.model.Payment;
import hu.pte.hotelbooking.service.PaymentService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService service;

    // crud

    @GetMapping(value = "/payment/jpa")
    public List<Payment> getAllPaymentsJPA() {
        return service.getAllPaymentsJPA();
    }

    @GetMapping(value = "/payment/jpa/{id}")
    public Payment getPaymentByIdJPA(@PathVariable Long id) {
        return service.getPaymentByIdJPA(id);
    }
    
    @PostMapping(value = "/payment/jpa/add")
    public ResponseEntity<Payment> addPaymentJPA(@RequestBody Payment payment) {
        Payment saved = service.addPaymentJPA(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping(value = "/payment/jpa/edit/{id}")
    public Payment updatePaymentJPA(@PathVariable Long id, @RequestBody Payment payment) {
        return service.updatePaymentJPA(id, payment);
    }

    @DeleteMapping(value = "/payment/jpa/delete/{id}")
    public String deletePaymentJPA(@PathVariable Long id) {
        service.deletePaymentJPA(id);
        return "Payment with id: " + id + " deleted";
    }

    // Stored procedure 
    @GetMapping(value = "/payment/spq/remainingBalance/{bookingId}")
    public BigDecimal getRemainingBalanceSPQ(@PathVariable Long bookingId) {
        return service.getRemainingBalanceSPQ(bookingId);
    }

    // Custom query

    @GetMapping(value = "/payment/query/byStatus/{status}")
    public List<Payment> findPaymentsByStatus(@PathVariable String status) {
        return service.findPaymentsByStatus(status);
    }
}
