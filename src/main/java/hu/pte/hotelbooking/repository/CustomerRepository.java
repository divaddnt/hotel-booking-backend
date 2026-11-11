package hu.pte.hotelbooking.repository;

import hu.pte.hotelbooking.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Lead adds the custom query here (section 12)
}