package ua.kpi.fict.multinewscw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.fict.multinewscw.entities.Customer;
import ua.kpi.fict.multinewscw.repositories.CustomerRepo;

import javax.naming.NameAlreadyBoundException;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepo customerRepo;

    public void save(Customer customer) {
        customerRepo.save(customer);
    }

    public void saveById(Long customerId) {
        Customer customer = customerRepo.findById(customerId).get();
        customerRepo.save(customer);
    }

    public Customer findById(long id) {
        return customerRepo.findById(id).get();
    }

    public List<Customer> findAll() {
        return (List<Customer>) customerRepo.findAll();
    }

    public List<Customer> findAllEditors() {
        return customerRepo.findByRoleEquals("ROLE_EDITOR");
    }

    public void signUp(Customer customer) throws NameAlreadyBoundException {
        if (customerRepo.findCustomerByUserName(customer.getUserName()) != null){
            throw new NameAlreadyBoundException("Username already taken");
        }
        customerRepo.save(customer);
    }

}
