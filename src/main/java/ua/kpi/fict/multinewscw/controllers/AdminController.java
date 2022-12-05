package ua.kpi.fict.multinewscw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kpi.fict.multinewscw.entities.Customer;
import ua.kpi.fict.multinewscw.entities.enums.Role;
import ua.kpi.fict.multinewscw.services.CustomerService;

import java.util.Map;

@Controller
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class AdminController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "admin";
    }

    @PostMapping("/admin/customer/delete/{id}")
    public String deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/customer/edit/{customer}")
    public String editCustomer(@PathVariable("customer") Customer customer, Model model){
        model.addAttribute("customer", customer);
        model.addAttribute("roles", Role.values());
        return "customer-edit";
    }

    @PostMapping("/admin/customer/edit")
    public String editCustomer(@RequestParam("customerId") Customer customer, @RequestParam Map<String, String> form){
        customerService.changeRoles(customer, form);
        return "redirect:/admin";
    }

}
