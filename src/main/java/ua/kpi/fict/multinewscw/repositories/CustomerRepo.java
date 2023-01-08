package ua.kpi.fict.multinewscw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.fict.multinewscw.entities.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Customer findCustomerByUsername(String cusName);

    Customer findCustomerByCustomerId(Long id);
}
