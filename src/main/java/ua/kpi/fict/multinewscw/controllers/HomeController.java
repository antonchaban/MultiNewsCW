package ua.kpi.fict.multinewscw.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;

@Controller
public class HomeController {
    @GetMapping(value = "/")
    public String redirectToHome() {

        return "TODO";
    }
}
