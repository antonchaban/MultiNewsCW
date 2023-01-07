package ua.kpi.fict.multinewscw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ua.kpi.fict.multinewscw.entities.Article;
import ua.kpi.fict.multinewscw.services.implementation.ArticleServiceImpl;
import ua.kpi.fict.multinewscw.services.implementation.CustomerServiceImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @Autowired
    private ArticleServiceImpl articleService;

    @GetMapping(value = "/")
    public String redirectToArticles() {
//        articleService.esSave();
//        for (Article article : articleService.esFindAll()) {
//            System.out.println(article.toString());
//        }

//        System.out.println(articleService.esFindByTitle("війна"));
        return "redirect:/articles";
    }


    @GetMapping(value = "/setEng")
    public String setEng(HttpServletResponse response) {
        Cookie cookie = new Cookie("language", "en");
        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        response.addCookie(cookie);
        return "redirect:/articles";
    }

    @GetMapping(value = "/setUkr")
    public String setUkr(HttpServletResponse response) {
        Cookie cookie = new Cookie("language", "uk");
        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        response.addCookie(cookie);
        return "redirect:/articles";
    }

}
