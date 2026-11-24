package hu.pte.hotelbooking.service;

import hu.pte.hotelbooking.exception.ResourceNotFoundException;
import hu.pte.hotelbooking.model.Customer;
import hu.pte.hotelbooking.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository repo;

    @PersistenceContext
    private EntityManager em;

    // crud

    public List<Customer> getAllCustomersJPA() {
        return repo.findAll();
    }

    public Customer getCustomerByIdJPA(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id: " + id + " not found!"));
    }

    public Customer addCustomerJPA(Customer customer) {
        if (customer.getRegistrationDate() == null) {
            customer.setRegistrationDate(LocalDate.now());
        }
        return repo.save(customer);
    }

    public Customer updateCustomerJPA(Long id, Customer customer) {
        Customer existing = getCustomerByIdJPA(id);   //  if missing ->404
        existing.setFullName(customer.getFullName());
        existing.setEmail(customer.getEmail());
        existing.setPhone(customer.getPhone());
        existing.setAddress(customer.getAddress());
        if (customer.getRegistrationDate() != null) {
            existing.setRegistrationDate(customer.getRegistrationDate());
        }
        return repo.save(existing);
    }

    public void deleteCustomerJPA(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Customer with id: " + id + " not found!");
        }
        repo.deleteById(id);
        repo.flush();  
    }

    // Stored procedure

    public Integer countCustomersSPQ() {
        StoredProcedureQuery spq = em.createStoredProcedureQuery("countCustomers");
        spq.registerStoredProcedureParameter("countOUT", Integer.class, ParameterMode.OUT);
        spq.execute();
        return (Integer) spq.getOutputParameterValue("countOUT");
    }

    //  Custom query 

    public List<Customer> searchCustomersByName(String keyword) {
        List<Customer> result = repo.searchCustomersByName(keyword);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("No customer name contains: " + keyword);
        }
        return result;
    }
}
