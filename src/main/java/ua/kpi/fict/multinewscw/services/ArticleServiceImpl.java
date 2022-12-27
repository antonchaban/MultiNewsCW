package ua.kpi.fict.multinewscw.services;

import com.sun.syndication.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.fict.multinewscw.entities.Article;
import ua.kpi.fict.multinewscw.entities.Customer;
import ua.kpi.fict.multinewscw.repositories.ArticleRepo;
import ua.kpi.fict.multinewscw.repositories.CustomerRepo;

import java.io.IOException;
import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepo articleRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    RssParser rssParser;

    public void save(Article article) {
        articleRepo.save(article);
    }

    public void saveById(Long artId) {
        articleRepo.save(articleRepo.findById(artId).get());
    }

    public List<Article> findAll() {
        return articleRepo.findAll();
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

    public void createArticle(Article article, Principal principal) {
        if (principal == null) {
            article.setCustomer(new Customer());
        } else {
            article.setCustomer(customerRepo.findCustomerByUsername(principal.getName()));
        }
        article.setArticleDate(Date.from(Instant.now()));
        articleRepo.save(article);
    }


    public List<Article> viewAllArticles() {
        return articleRepo.findAll();
    }

    public List<Article> getByAuthor(String authorUserName) {
        return articleRepo.findArticleByCustomerUsername(authorUserName);
    }

    public List<Article> listArticles(String searchWord) {
        if (searchWord != null) return getByTitle(searchWord);
        return viewAllArticles();
    }

    public void deleteArticle(Customer customer, Long id) {
        Article article = articleRepo.findById(id)
                .orElse(null);
        if (article != null) {
            if (article.getCustomer().getCustomerId().equals(customer.getCustomerId()) || customer.isAdmin()) {
                articleRepo.delete(article);
            } else {
                System.err.println("Customer: " + customer.getUsername() + " haven't this article with id" + id);
            }
        } else {
            System.err.println("Article with id" + id + "is not found");
        }
    }

    public void editArticle(Article updatedArticle, Long id) {
        Article article = articleRepo.findById(id).orElse(null);
        if (article != null) {
            article.setArticleTitle(updatedArticle.getArticleTitle());
            article.setArticleLink(updatedArticle.getArticleLink());
            article.setArticleDescription(updatedArticle.getArticleDescription());
            article.setArticleSource(updatedArticle.getArticleSource());
            article.setArticleDate(Date.from(Instant.now()));
            articleRepo.save(article);
        } else {
            System.err.println("Article with id" + id + "is not found");
        }
    }

    public void parseArticle(String link) throws FeedException, IOException {
        ArrayList<Article> listFromRss = rssParser.doParse(link);
        ArrayList<Article> listFromDb = (ArrayList<Article>) articleRepo.findAll();
        for (int i = 0; i < listFromRss.size(); i++) {
            Article articleRss = listFromRss.get(i);
            if (articleRepo.findArticleByArticleLink(articleRss.getArticleLink()) == null) {
                if (Objects.equals(link, "https://www.pravda.com.ua/rss/")) {
                    articleRss.setCustomer(customerRepo.findById(17L).get());
                }
                articleRepo.save(articleRss);
            }

        }

    }
}

