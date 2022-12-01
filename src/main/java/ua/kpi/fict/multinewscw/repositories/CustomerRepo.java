package ua.kpi.fict.multinewscw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.fict.multinewscw.entities.Customer;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Customer findCustomerByUsername(String cusName);
}
