package ua.kpi.fict.multinewscw.controllers;


import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.kpi.fict.multinewscw.entities.Article;
import ua.kpi.fict.multinewscw.entities.Customer;
import ua.kpi.fict.multinewscw.entities.enums.Category;
import ua.kpi.fict.multinewscw.services.implementation.ArticleServiceImpl;
import ua.kpi.fict.multinewscw.services.implementation.CustomerServiceImpl;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleController {
    private final ArticleServiceImpl articleServiceImpl;

    private final CustomerServiceImpl customerServiceImpl;

    private final String customerAtr = "customer";
    private final String languageAtr = "language";
    private final String articlesAtr = "articles";
    private final String articleAtr = "article";


    @GetMapping("/articles")
    public String viewAllArticles(Model model, Principal principal,
                                  @RequestParam(name = "searchCategory", defaultValue = "") String searchCategory,
                                  @RequestParam(name = "searchWord", defaultValue = "") String searchWord,
                                  @CookieValue(name = "language", defaultValue = "en") String language,
                                  @RequestParam(name = "searchSource", defaultValue = "") String searchSource,
                                  @RequestParam(name = "newsDate", defaultValue = "") String newsDate) {
        model
                .addAttribute(articlesAtr, articleServiceImpl
                        .listArticles(searchWord, searchSource, language, newsDate, searchCategory));
        model.addAttribute(customerAtr, customerServiceImpl.getCustomerByPrincipal(principal));
        model.addAttribute(languageAtr, language);
        return "newshome";
    }

    @PostMapping("/articles/create")
    public String createArticle(Article article, Principal principal,
                                @CookieValue(name = "language", defaultValue = "en") String language,
                                @RequestParam Map<String, String> form) throws IOException, ParseException {
        articleServiceImpl.createArticle(article, principal, language, form.get("category"));
        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String articleInfo(@PathVariable Long id, Model model, Principal principal,
                              @CookieValue(name = "language", defaultValue = "en") String language) {
        model.addAttribute(customerAtr, customerServiceImpl.getCustomerByPrincipal(principal));
        model.addAttribute(articleAtr, articleServiceImpl.findById(id));
        model.addAttribute(languageAtr, language);
        return "article-info";
    }

    @GetMapping("/my/articles")
    public String userArticles(Principal principal, Model model,
                               @CookieValue(name = "language", defaultValue = "en") String language) {
        Customer customer = customerServiceImpl.getCustomerByPrincipal(principal);
        model.addAttribute(customerAtr, customer);
        model.addAttribute("categories", Category.values());
        model.addAttribute(articlesAtr, customer.getArticles());
        model.addAttribute(languageAtr, language);
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
        model.addAttribute(languageAtr, language);
        if (customer.isAdmin() || customer == article.getCustomer()) {
            model.addAttribute("categories", Category.values());
            model.addAttribute(customerAtr, customer);
            model.addAttribute(articleAtr, article);
            return "article-edit";
        } else {
            throw new ResponseStatusException(
                    HttpStatus.METHOD_NOT_ALLOWED, "It is not allowed for you"
            );
        }
    }

    @PostMapping("/edit/articles/{id}")
    public String confirmEditArticle(Model model, Principal principal, Article updArticle, @PathVariable Long id,
                                     @CookieValue(name = "language", defaultValue = "en") String language,
                                     @RequestParam Map<String, String> form)
            throws IOException, ParseException {
        articleServiceImpl.editArticle(updArticle, id, language, form.get("category"));
        model.addAttribute(customerAtr, customerServiceImpl.getCustomerByPrincipal(principal));
        model.addAttribute(articleAtr, articleServiceImpl.findById(id));
        return "redirect:/articles/" + id;
    }
}
