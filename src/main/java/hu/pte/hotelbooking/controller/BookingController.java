package hu.pte.hotelbooking.controller;

import hu.pte.hotelbooking.model.Booking;
import hu.pte.hotelbooking.service.BookingService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingController {

    @Autowired
    private BookingService service;

    // CRUD 

    @GetMapping(value = "/booking/jpa")
    public List<Booking> getAllBookingsJPA() {
        return service.getAllBookingsJPA();
    }

    @GetMapping(value = "/booking/jpa/{id}")
    public Booking getBookingByIdJPA(@PathVariable Long id) {
        return service.getBookingByIdJPA(id);
    }

    @PostMapping(value = "/booking/jpa/add")
    public Booking addBookingJPA(@RequestBody Booking booking) {
        return service.addBookingJPA(booking);
    }

    @PutMapping(value = "/booking/jpa/edit/{id}")
    public Booking updateBookingJPA(@PathVariable Long id,
                                    @RequestBody Booking booking) {
        return service.updateBookingJPA(id, booking);
    }

    @DeleteMapping(value = "/booking/jpa/delete/{id}")
    public String deleteBookingJPA(@PathVariable Long id) {
        service.deleteBookingJPA(id);
        return "Booking with id: " + id + " deleted";
    }

    // Stored procedure 

    @GetMapping(value = "/booking/spq/total/{id}")
    public BigDecimal calculateBookingTotalSPQ(@PathVariable Long id) {
        return service.calculateBookingTotalSPQ(id);
    }

    // ===================== Custom query =====================

    @GetMapping(value = "/booking/query/findByCustomerId/{customerId}")
    public List<Booking> findBookingsByCustomerId(@PathVariable Long customerId) {
        return service.findBookingsByCustomerId(customerId);
    }
}
