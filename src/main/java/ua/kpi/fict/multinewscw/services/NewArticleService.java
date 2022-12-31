package ua.kpi.fict.multinewscw.services;

import ua.kpi.fict.multinewscw.entities.Article;
import ua.kpi.fict.multinewscw.entities.ArticleMain;
import ua.kpi.fict.multinewscw.entities.Customer;

import java.security.Principal;
import java.util.List;

public interface NewArticleService {
    void save(ArticleMain article);

    void saveById(Long artId);

    List<ArticleMain> findAll();

    ArticleMain findById(long artId);

    List<ArticleMain> getByTitle(String title);

    List<ArticleMain> getBySource(String source);

    List<ArticleMain> searchArticles(String title, String source);

    void createArticle(ArticleMain article, Principal principal);

    List<ArticleMain> viewAllArticles();

    List<ArticleMain> getByAuthor(String authorUserName);

    List<ArticleMain> listArticles(String searchWord);

    void deleteArticle(Customer customer, Long id);

    void editArticle(Article updatedArticle, Long id);
}
