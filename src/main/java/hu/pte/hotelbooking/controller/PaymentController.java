package hu.pte.hotelbooking.controller;

import hu.pte.hotelbooking.model.Payment;
import hu.pte.hotelbooking.service.PaymentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotationSS.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @GetMapping("/jpa")
    public List<Payment> getAllPaymentsJPA() {
        return service.getAllPaymentsJPA();
    }

    @GetMapping("/jpa/{id}")
    public Payment getPaymentByIdJPA(@PathVariable Long id) {
        return service.getPaymentByIdJPA(id);
    }

    @PostMapping("/jpa/add")
    public Payment addPaymentJPA(@RequestBody Payment payment) {
        return service.addPaymentJPA(payment);
    }

    @PutMapping("/jpa/edit/{id}")
    public Payment updatePaymentJPA(@PathVariable Long id, @RequestBody Payment payment) {
        return service.updatePaymentJPA(id, payment);
    }

    @DeleteMapping("/jpa/delete/{id}")
    public void deletePaymentJPA(@PathVariable Long id) {
        service.deletePaymentJPA(id);
    }
}