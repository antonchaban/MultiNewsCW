package ua.kpi.fict.multinewscw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.kpi.fict.multinewscw.entities.Customer;
import ua.kpi.fict.multinewscw.entities.enums.Role;
import ua.kpi.fict.multinewscw.services.implementation.CustomerServiceImpl;

import java.security.Principal;
import java.util.Map;

@Controller
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {
    private final CustomerServiceImpl customerServiceImpl;

    @GetMapping("/admin")
    public String admin(Model model, Principal principal,
                        @CookieValue(name = "language", defaultValue = "en") String language) {
        model.addAttribute("customers", customerServiceImpl.findAll());
        model.addAttribute("customer", customerServiceImpl.getCustomerByPrincipal(principal));
        model.addAttribute("language", language);
        return "admin";
    }

    @PostMapping("/admin/customer/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerServiceImpl.deleteCustomer(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/customer/edit/{customer}")
    public String editCustomer(@PathVariable("customer") Customer customer, Model model, Principal principal,
                               @CookieValue(name = "language", defaultValue = "en") String language) {
        model.addAttribute("customer", customer);
        model.addAttribute("mycustomer", customerServiceImpl.getCustomerByPrincipal(principal));
        model.addAttribute("roles", Role.values());
        model.addAttribute("language", language);
        return "customer-edit";
    }

    @PostMapping("/admin/customer/edit")
    public String editCustomer(@RequestParam("customerId") Long customerId, @RequestParam Map<String, String> form) {
        customerServiceImpl.changeRoles(customerId, form);
        return "redirect:/admin";
    }

}
