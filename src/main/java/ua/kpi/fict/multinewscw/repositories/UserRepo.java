package ua.kpi.fict.multinewscw.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.kpi.fict.multinewscw.entities.User;

public interface UserRepo extends CrudRepository<User, Long> {
}
