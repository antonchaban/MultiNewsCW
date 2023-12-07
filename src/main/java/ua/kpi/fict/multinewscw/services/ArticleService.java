package ua.kpi.fict.multinewscw.services;

import org.json.simple.parser.ParseException;
import ua.kpi.fict.multinewscw.entities.Article;
import ua.kpi.fict.multinewscw.entities.Customer;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface ArticleService {

    Article findById(long artId);

    void createArticle(Article article, Principal principal, String lang, String category) throws IOException, ParseException;

    List<Article> viewAllArticles();

    List<Article> listArticles(String searchWord, String searchSource, String language, String newsDate, String category);

    void deleteArticle(Customer customer, Long id);

    void editArticle(Article updatedArticle, Long id, String lang, String category) throws IOException, ParseException;
}
