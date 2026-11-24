package hu.pte.hotelbooking.repository;

import hu.pte.hotelbooking.model.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // customers whose name contains the keyword (not case sensitive)
    @Query(value = "SELECT c FROM Customer c WHERE LOWER(c.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Customer> searchCustomersByName(@Param("keyword") String keyword);
}
