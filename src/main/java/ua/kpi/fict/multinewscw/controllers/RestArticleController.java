package ua.kpi.fict.multinewscw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kpi.fict.multinewscw.entities.Article;
import ua.kpi.fict.multinewscw.services.ArticleService;
import ua.kpi.fict.multinewscw.services.CustomerService;

import java.security.Principal;

//@Controller
@RestController
public class RestArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/api/v1/articles")
    public ResponseEntity createArticle(@RequestBody Article article, Principal principal) {
        articleService.createArticle(article, principal);
        return ResponseEntity.ok("Article Created");
    }

    @GetMapping("/api/v1/articles")
    public ResponseEntity viewAllArticles() {
        return ResponseEntity.ok(articleService.viewAllArticles());
    }

    @GetMapping("/api/v1/authors/{authorName}/articles")
    public ResponseEntity viewArticlesByAuthor(@PathVariable String authorName) {
        return ResponseEntity.ok(articleService.getByAuthor(authorName));
    }


}
