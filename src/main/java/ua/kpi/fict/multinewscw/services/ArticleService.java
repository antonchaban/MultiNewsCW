package ua.kpi.fict.multinewscw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.fict.multinewscw.entities.Article;
import ua.kpi.fict.multinewscw.entities.Customer;
import ua.kpi.fict.multinewscw.repositories.ArticleRepo;
import ua.kpi.fict.multinewscw.repositories.CustomerRepo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepo articleRepo;

    @Autowired
    private CustomerRepo customerRepo;

    public void save(Article article) {
        articleRepo.save(article);
    }

    public void saveById(Long artId) {
        articleRepo.save(articleRepo.findById(artId).get());
    }

    public List<Article> findAll() {
        return (List<Article>) articleRepo.findAll();
    }

    public Article findById(long artId) {
        return articleRepo.findById(artId).get();
    }

    public List<Article> getByTitle(String title) {
        List<Article> articles = new ArrayList<>();
        for (Article article : articleRepo.findAll()) {
            if (article.getArticleTitle().toLowerCase().contains(title.toLowerCase())) {
                articles.add(article);
            }
        }
        return articles;
    }

    public List<Article> getBySource(String source) {
        List<Article> articles = new ArrayList<>();
        for (Article article : articleRepo.findAll()) {
            if (article.getArticleSource().toLowerCase().contains(source.toLowerCase())) {
                articles.add(article);
            }
        }
        return articles;
    }

    public List<Article> searchArticles(String title, String source) {
        List<Article> searchedArticles = new ArrayList<>();
        if (title != null && source == null) {
            searchedArticles = getByTitle(title);

        } else if (title == null && source != null) {
            searchedArticles = getBySource(source);
        }
        return searchedArticles;
    }

    public Article createArticle(Article article){
        Customer customer = customerRepo.findById(article.getCustomer().getCustomerId()).get();
        article.setCustomer(customer);
        article.setArticleDate(Date.from(Instant.now()));
        return articleRepo.save(article);
    }

    public List<Article> viewAllArticles(){
        return (List<Article>) articleRepo.findAll();
    }

    public List<Article> getByAuthor(String authorUserName){
        return articleRepo.findArticleByCustomerUserName(authorUserName);
    }

    public void deleteById(long artId) {
        articleRepo.deleteById(artId);
    }
}
