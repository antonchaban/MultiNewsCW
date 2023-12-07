package ua.kpi.fict.multinewscw.services.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.kpi.fict.multinewscw.entities.Customer;
import ua.kpi.fict.multinewscw.entities.enums.Role;
import ua.kpi.fict.multinewscw.repositories.CustomerRepo;
import ua.kpi.fict.multinewscw.services.CustomerService;

import javax.naming.NameAlreadyBoundException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;

    private final PasswordEncoder passwordEncoder;

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
        customerRepo.findById(id).ifPresent(customerRepo::delete);

    }

    @Override
    public void changeRoles(Long customerId, Map<String, String> form) {
        Customer customer = customerRepo.findById(customerId).orElseThrow(IllegalArgumentException::new);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        customer.getRoles().clear();
        form.values().stream().filter(roles::contains).forEach(value -> customer.getRoles().add(Role.valueOf(value)));
        customerRepo.save(customer);
    }
}
