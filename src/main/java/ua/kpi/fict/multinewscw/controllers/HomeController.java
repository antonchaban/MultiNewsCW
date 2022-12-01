package ua.kpi.fict.multinewscw.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String redirectToArticles() {
        return "redirect:/articles";
    }

}
