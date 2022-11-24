package ua.kpi.fict.multinewscw.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

//@Controller
@RestController
public class HomeController {
    @GetMapping(value = "/")
    public String redirectToHome() {

        return "TODO";
    }


}
