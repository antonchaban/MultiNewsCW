package ua.kpi.fict.multinewscw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.kpi.fict.multinewscw.entities.Customer;
import ua.kpi.fict.multinewscw.services.implementation.CustomerServiceImpl;

import javax.naming.NameAlreadyBoundException;
import java.security.Principal;

@Controller
public class CustomerController {
    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @GetMapping("/login")
    public String login(Principal principal, Model model,
                        @CookieValue(name = "language", defaultValue = "en") String language) {
        model.addAttribute("customer", customerServiceImpl.getCustomerByPrincipal(principal));
        model.addAttribute("language", language);
        return "login";
    }

    @PostMapping("/login")
    public String loginPost() {
        return "redirect:/articles";
    }


    @GetMapping("/signup")
    public String signUp(Principal principal, Model model,
                         @CookieValue(name = "language", defaultValue = "en") String language) {
        model.addAttribute("customer", customerServiceImpl.getCustomerByPrincipal(principal));
        model.addAttribute("language", language);
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(Customer customer) throws NameAlreadyBoundException {
        customerServiceImpl.createCustomer(customer);
        return "redirect:/login";
    }

    @GetMapping("/customer/{customer}")
    public String customerInfo(@PathVariable("customer") Customer customer, Model model, Principal principal,
                               @CookieValue(name = "language", defaultValue = "en") String language) {
        model.addAttribute("customer", customer);
        model.addAttribute("mycustomer", customerServiceImpl.getCustomerByPrincipal(principal));
        model.addAttribute("articles", customer.getArticles());
        model.addAttribute("language", language);
        return "customer-info";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model,
                          @CookieValue(name = "language", defaultValue = "en") String language) {
        Customer customer = customerServiceImpl.getCustomerByPrincipal(principal);
        model.addAttribute("customer", customer);
        model.addAttribute("language", language);
        return "profile";
    }
}
