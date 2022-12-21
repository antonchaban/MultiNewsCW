package ua.kpi.fict.multinewscw.controllers;

import com.sun.syndication.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import ua.kpi.fict.multinewscw.services.RssParser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Controller
public class HomeController {

    @Autowired
    RssParser rssParser;

    @GetMapping(value = "/")
    public String redirectToArticles() throws FeedException, IOException {
        System.out.println(rssParser.doParse("https://moxie.foxnews.com/google-publisher/world.xml")); //todo
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
