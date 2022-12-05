package ua.kpi.fict.multinewscw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.kpi.fict.multinewscw.entities.Customer;
import ua.kpi.fict.multinewscw.services.CustomerService;

import javax.naming.NameAlreadyBoundException;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost() {
        return "redirect:/articles";
    }


    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(Customer customer, Model model) throws NameAlreadyBoundException { // TODO
        customerService.createCustomer(customer);
        return "redirect:/login";
    }

    @GetMapping("/customer/{customer}")
    public String customerInfo(@PathVariable("customer") Customer customer, Model model){
        model.addAttribute("customer", customer);
        model.addAttribute("articles", customer.getArticles());
        return "customer-info";
    }
}
