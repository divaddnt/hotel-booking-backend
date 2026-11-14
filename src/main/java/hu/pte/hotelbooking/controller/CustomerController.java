<<<<<<< HEAD
package hu.pte.hotelbooking.controller;

import hu.pte.hotelbooking.model.Customer;
import hu.pte.hotelbooking.service.CustomerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService service;

    // ===================== CRUD (JPA) =====================

    @GetMapping(value = "/customer/jpa")
    public List<Customer> getAllCustomersJPA() {
        return service.getAllCustomersJPA();
    }

    @GetMapping(value = "/customer/jpa/{id}")
    public Customer getCustomerByIdJPA(@PathVariable Long id) {
        return service.getCustomerByIdJPA(id);
    }

    @PostMapping(value = "/customer/jpa/add")
    public Customer addCustomerJPA(@RequestBody Customer customer) {
        return service.addCustomerJPA(customer);
    }

    @PutMapping(value = "/customer/jpa/edit/{id}")
    public Customer updateCustomerJPA(@PathVariable Long id, @RequestBody Customer customer) {
        return service.updateCustomerJPA(id, customer);
    }

    @DeleteMapping(value = "/customer/jpa/delete/{id}")
    public String deleteCustomerJPA(@PathVariable Long id) {
        service.deleteCustomerJPA(id);
        return "Customer with id: " + id + " deleted";
    }

    // ===================== Stored procedure =====================

    @GetMapping(value = "/customer/spq/count")
    public Integer countCustomersSPQ() {
        return service.countCustomersSPQ();
    }

    // ===================== Custom query =====================

    @GetMapping(value = "/customer/query/searchByName/{keyword}")
    public List<Customer> searchCustomersByName(@PathVariable String keyword) {
        return service.searchCustomersByName(keyword);
    }
=======

package hu.pte.hotelbooking.controller;

/**
 *
 * @author somalinban
 */
public class CustomerController {
    
>>>>>>> f2bbf344d39310d707f66e8318dfd142c7929726
}
