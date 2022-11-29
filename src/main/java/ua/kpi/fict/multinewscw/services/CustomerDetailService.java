package ua.kpi.fict.multinewscw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.kpi.fict.multinewscw.repositories.CustomerRepo;

@Service
public class CustomerDetailService implements UserDetailsService {

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepo.findCustomerByUserName(username);
    }
}
