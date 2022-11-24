package ua.kpi.fict.multinewscw.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.fict.multinewscw.entities.Customer;
import ua.kpi.fict.multinewscw.services.CustomerService;

import javax.naming.NameAlreadyBoundException;

//@Controller
@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    // TODO

    @PostMapping("/signup")
    public ResponseEntity signUpCustomer(@RequestBody Customer customer) {
        try {
            customerService.signUp(customer);
            return ResponseEntity.ok("User Created");
        } catch (NameAlreadyBoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/admin/editors")
    public ResponseEntity getAllEditors() {
        return ResponseEntity.ok(customerService.findAllEditors());
    }

}
