package ua.kpi.fict.multinewscw.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.kpi.fict.multinewscw.entities.Article;
import ua.kpi.fict.multinewscw.services.ArticleService;
import ua.kpi.fict.multinewscw.services.CustomerService;

@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/articles")
        public String viewAllArticles(Model model) {
        model.addAttribute("articles", articleService.viewAllArticles());
        return "newshome";
    }

    @PostMapping ("/articles/create")
    public String createArticle(Article article) {
        article.setCustomer(customerService.findById(1));
        articleService.createArticle(article);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String articleInfo(@PathVariable Long id, Model model){
        model.addAttribute("article", articleService.findById(id));
        return "article-info";
    }
}
