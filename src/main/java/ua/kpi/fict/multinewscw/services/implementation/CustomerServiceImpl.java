package ua.kpi.fict.multinewscw.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.kpi.fict.multinewscw.entities.Customer;
import ua.kpi.fict.multinewscw.entities.enums.Role;
import ua.kpi.fict.multinewscw.repositories.CustomerRepo;

import javax.naming.NameAlreadyBoundException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl {
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        return customerRepo.findAll();
    }

    public Customer getCustomerByPrincipal(Principal principal) {
        if (principal == null) return new Customer();
        return customerRepo.findCustomerByUsername(principal.getName());
    }

    public void createCustomer(Customer customer) throws NameAlreadyBoundException {
        if (customerRepo.findCustomerByUsername(customer.getUsername()) != null) {
            throw new NameAlreadyBoundException("Username already taken");
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.getRoles().add(Role.ROLE_EDITOR);
        customerRepo.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepo.findById(id).orElse(null);
        if (customer != null) {
            customerRepo.delete(customer);
        }

    }

    public void changeRoles(Customer customer, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        customer.getRoles().clear();
        for (String value : form.values()) {
            if (roles.contains(value)) {
                customer.getRoles().add(Role.valueOf(value));
            }
        }
        customerRepo.save(customer);
    }
}
