package ua.kpi.fict.multinewscw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.fict.multinewscw.services.ArticleService;

import java.util.ArrayList;
import java.util.Collection;

@Controller
//@RestController
public class HomeController {
    @Autowired
    private ArticleService articleService;

//    @GetMapping(value = "/")
//    public String homePage(Model model) {
//        model.addAttribute("articles", articleService.viewAllArticles());
//        return "newshome";
//    }

    @GetMapping(value = "/")
    public String redirectToArticles() {
        return "redirect:/articles";
    }

}
