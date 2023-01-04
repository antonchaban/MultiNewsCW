package ua.kpi.fict.multinewscw.controllers;


import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.kpi.fict.multinewscw.entities.Article;
import ua.kpi.fict.multinewscw.entities.Customer;
import ua.kpi.fict.multinewscw.services.implementation.ArticleServiceImpl;
import ua.kpi.fict.multinewscw.services.implementation.CustomerServiceImpl;

import java.io.IOException;
import java.security.Principal;

@Controller
public class ArticleController {
    @Autowired
    private ArticleServiceImpl articleServiceImpl;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @GetMapping("/articles")
    public String viewAllArticles(Model model, Principal principal, @RequestParam(name = "searchWord", required = false) String searchWord,
                                  @CookieValue(name = "language", defaultValue = "en") String language) {
        model.addAttribute("articles", articleServiceImpl.listArticles(searchWord));
        model.addAttribute("customer", customerServiceImpl.getCustomerByPrincipal(principal));
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("language", language);
        return "newshome";
    }

    @PostMapping("/articles/create")
    public String createArticle(Article article, Principal principal,
                                @CookieValue(name = "language", defaultValue = "en") String language) throws IOException, ParseException {
        articleServiceImpl.createArticle(article, principal, language);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String articleInfo(@PathVariable Long id, Model model, Principal principal,
                              @CookieValue(name = "language", defaultValue = "en") String language) {
        model.addAttribute("customer", customerServiceImpl.getCustomerByPrincipal(principal));
        model.addAttribute("article", articleServiceImpl.findById(id));
        model.addAttribute("language", language);
        return "article-info";
    }

    @GetMapping("/my/articles")
    public String userProducts(Principal principal, Model model,
                               @CookieValue(name = "language", defaultValue = "en") String language) {
        Customer customer = customerServiceImpl.getCustomerByPrincipal(principal);
        model.addAttribute("customer", customer);
        model.addAttribute("articles", customer.getArticles());
        model.addAttribute("language", language);
        return "my-articles";
    }

    @PostMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable Long id, Principal principal) {
        articleServiceImpl.deleteArticle(customerServiceImpl.getCustomerByPrincipal(principal), id);
        return "redirect:/my/articles";
    }

    @GetMapping("/edit/articles/{id}")
    public String editArticle(@PathVariable Long id, Model model, Principal principal,
                              @CookieValue(name = "language", defaultValue = "en") String language) {
        Customer customer = customerServiceImpl.getCustomerByPrincipal(principal);
        Article article = articleServiceImpl.findById(id);
        model.addAttribute("language", language);
        if (customer.isAdmin() || customer == article.getCustomer()) {
            model.addAttribute("customer", customer);
            model.addAttribute("article", article);
            return "article-edit";
        } else {
            throw new ResponseStatusException(
                    HttpStatus.METHOD_NOT_ALLOWED, "It is not allowed for you"
            );
        }
    }

    @PostMapping("/edit/articles/{id}")
    public String confirmEditArticle(Model model, Principal principal, Article updArticle, @PathVariable Long id,
                                     @CookieValue(name = "language", defaultValue = "en") String language)
            throws IOException, ParseException {
        articleServiceImpl.editArticle(updArticle, id, language);
        model.addAttribute("customer", customerServiceImpl.getCustomerByPrincipal(principal));
        model.addAttribute("article", articleServiceImpl.findById(id));
        return "redirect:/articles/" + id;
    }
}
