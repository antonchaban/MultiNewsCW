package ua.kpi.fict.multinewscw.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.kpi.fict.multinewscw.entities.Customer;

public interface UserRepo extends CrudRepository<Customer, Long> {
}
