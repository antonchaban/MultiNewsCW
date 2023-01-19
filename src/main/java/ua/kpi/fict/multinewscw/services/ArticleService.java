package ua.kpi.fict.multinewscw.services;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import ua.kpi.fict.multinewscw.entities.Article;
import ua.kpi.fict.multinewscw.entities.Customer;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface ArticleService {
    void save(Article article);

    void saveById(Long artId);

    List<Article> findAll();

    Article findById(long artId);

    List<Article> getByTitle(String title);

    List<Article> getBySource(String source);

    List<Article> searchArticles(String title, String source);

    void createArticle(Article article, Principal principal, String lang, String category) throws IOException, ParseException;

    List<Article> viewAllArticles();

    List<Article> getByAuthor(String authorUserName);

    List<Article> listArticles(String searchWord, String searchSource);

    void deleteArticle(Customer customer, Long id);

    void editArticle(Article updatedArticle, Long id, String lang, String category) throws IOException, ParseException;
}
