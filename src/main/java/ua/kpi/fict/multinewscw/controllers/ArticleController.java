package ua.kpi.fict.multinewscw.controllers;


import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.kpi.fict.multinewscw.entities.Article;
import ua.kpi.fict.multinewscw.entities.Customer;
import ua.kpi.fict.multinewscw.services.ArticleService;
import ua.kpi.fict.multinewscw.services.CustomerService;

import java.security.Principal;

@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/articles")
    public String viewAllArticles(Model model, Principal principal, @RequestParam(name = "searchWord", required = false) String searchWord,
                                  @CookieValue(name = "language", defaultValue = "eng") String language) {
        model.addAttribute("articles", articleService.listArticles(searchWord));
        model.addAttribute("customer", articleService.getCustomerByPrincipal(principal));
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("language", language);
        return "newshome";
    }

    @PostMapping("/articles/create")
    public String createArticle(Article article, Principal principal) {
        articleService.createArticle(article, principal);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String articleInfo(@PathVariable Long id, Model model, Principal principal,
                              @CookieValue(name = "language", defaultValue = "eng") String language) {
        model.addAttribute("customer", articleService.getCustomerByPrincipal(principal));
        model.addAttribute("article", articleService.findById(id));
        model.addAttribute("language", language);
        return "article-info";
    }

    @GetMapping("/my/articles")
    public String userProducts(Principal principal, Model model,
                               @CookieValue(name = "language", defaultValue = "eng") String language) {
        Customer customer = articleService.getCustomerByPrincipal(principal);
        model.addAttribute("customer", customer);
        model.addAttribute("articles", customer.getArticles());
        model.addAttribute("language", language);
        return "my-articles";
    }

    @PostMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable Long id, Principal principal) {
        articleService.deleteArticle(articleService.getCustomerByPrincipal(principal), id);
        return "redirect:/my/articles";
    }

    @GetMapping("/edit/articles/{id}")
    public String editArticle(@PathVariable Long id, Model model, Principal principal,
                              @CookieValue(name = "language", defaultValue = "eng") String language) {
        Customer customer = articleService.getCustomerByPrincipal(principal);
        Article article = articleService.findById(id);
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
    public String confirmEditArticle(Model model, Principal principal, Article updArticle, @PathVariable Long id) {
        articleService.editArticle(updArticle, id);
        model.addAttribute("customer", articleService.getCustomerByPrincipal(principal));
        model.addAttribute("article", articleService.findById(id));
        return "redirect:/articles/" + id;
    }
}
