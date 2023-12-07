package ua.kpi.fict.multinewscw.services;

import ua.kpi.fict.multinewscw.entities.Customer;

import javax.naming.NameAlreadyBoundException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface CustomerService {

    List<Customer> findAll();

    Customer getCustomerByPrincipal(Principal principal);

    void createCustomer(Customer customer) throws NameAlreadyBoundException;

    void deleteCustomer(Long id);

    void changeRoles(Long customerId, Map<String, String> form);

}
