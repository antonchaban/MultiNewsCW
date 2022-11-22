package ua.kpi.fict.multinewscw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.fict.multinewscw.entities.Customer;
import ua.kpi.fict.multinewscw.repositories.UserRepo;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public void save(Customer customer) {
        userRepo.save(customer);
    }

    public void saveById(Long customerId) {
        Customer customer = userRepo.findById(customerId).get();
        userRepo.save(customer);
    }

    public Customer findById(long id) {
        return userRepo.findById(id).get();
    }

    public List<Customer> findAll() {
        return (List<Customer>) userRepo.findAll();
    }


}
