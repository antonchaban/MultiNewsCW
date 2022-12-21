package ua.kpi.fict.multinewscw.services;

import ua.kpi.fict.multinewscw.entities.Customer;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface CustomerService {
    void save(Customer customer);

    void saveById(Long customerId);

    Customer findById(long id);

    List<Customer> findAll();

    Customer getCustomerByPrincipal(Principal principal);

    void createCustomer(Customer customer);

    void deleteCustomer(Long id);

    void changeRoles(Customer customer, Map<String, String> form);

}
