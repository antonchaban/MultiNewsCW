package ua.kpi.fict.multinewscw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ua.kpi.fict.multinewscw.services.implementation.CustomerServiceImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @Autowired
    CustomerServiceImpl customerServiceImpl;

    @GetMapping(value = "/")
    public String redirectToArticles() {
        return "redirect:/articles";
    }


    @GetMapping(value = "/setEng")
    public String setEng(HttpServletResponse response) {
        Cookie cookie = new Cookie("language", "eng");
        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        response.addCookie(cookie);
        return "redirect:/articles";
    }

    @GetMapping(value = "/setUkr")
    public String setUkr(HttpServletResponse response) {
        Cookie cookie = new Cookie("language", "ukr");
        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        response.addCookie(cookie);
        return "redirect:/articles";
    }

}
