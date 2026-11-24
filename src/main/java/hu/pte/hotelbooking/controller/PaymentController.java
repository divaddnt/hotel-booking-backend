package hu.pte.hotelbooking.controller;

import hu.pte.hotelbooking.model.Payment;
import hu.pte.hotelbooking.service.PaymentService;
<<<<<<< HEAD
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
=======
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
>>>>>>> origin/main

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService service;

<<<<<<< HEAD
    // ---------- CRUD (JPA) ----------

=======
>>>>>>> origin/main
    @GetMapping("/jpa")
    public List<Payment> getAllPaymentsJPA() {
        return service.getAllPaymentsJPA();
    }

    @GetMapping("/jpa/{id}")
    public Payment getPaymentByIdJPA(@PathVariable Long id) {
        return service.getPaymentByIdJPA(id);
    }

<<<<<<< HEAD
    @PostMapping(value = "/payment/jpa/add")
    public ResponseEntity<Payment> addPaymentJPA(@RequestBody Payment payment) {
        Payment saved = service.addPaymentJPA(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
=======
    @PostMapping("/jpa/add")
    public Payment addPaymentJPA(@RequestBody Payment payment) {
        return service.addPaymentJPA(payment);
>>>>>>> origin/main
    }

    @PutMapping("/jpa/edit/{id}")
    public Payment updatePaymentJPA(@PathVariable Long id, @RequestBody Payment payment) {
        return service.updatePaymentJPA(id, payment);
    }

    @DeleteMapping("/jpa/delete/{id}")
<<<<<<< HEAD
    public String deletePaymentJPA(@PathVariable Long id) {
        service.deletePaymentJPA(id);
        return "Payment " + id + " deleted";
    }

    // ---------- Stored procedure ----------

    @GetMapping(value = "/payment/spq/remainingBalance/{bookingId}")
    public BigDecimal getRemainingBalanceSPQ(@PathVariable Long bookingId) {
        return service.getRemainingBalanceSPQ(bookingId);
    }

    // ---------- Custom query ----------

    @GetMapping("/query/byStatus/{status}")
    public List<Payment> findPaymentsByStatus(@PathVariable String status) {
        return service.findPaymentsByStatus(status);
=======
    public void deletePaymentJPA(@PathVariable Long id) {
        service.deletePaymentJPA(id);
>>>>>>> origin/main
    }
}