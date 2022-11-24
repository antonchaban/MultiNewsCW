package ua.kpi.fict.multinewscw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.kpi.fict.multinewscw.entities.Article;
import ua.kpi.fict.multinewscw.entities.Customer;
import ua.kpi.fict.multinewscw.services.ArticleService;
import ua.kpi.fict.multinewscw.services.CustomerService;

import javax.naming.NameAlreadyBoundException;

//@Controller
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/articles")
    public ResponseEntity createArticle(@RequestBody Article article) {
        articleService.createArticle(article);
        return ResponseEntity.ok("Article Created");
    }

    @GetMapping("/articles")
    public ResponseEntity viewAllArticles() {
        return ResponseEntity.ok(articleService.viewAllArticles());
    }

    @GetMapping("/authors/{authorName}/articles")
    public ResponseEntity viewArticlesByAuthor(@PathVariable String authorName) {
        return ResponseEntity.ok(articleService.getByAuthor(authorName));
    }


}
