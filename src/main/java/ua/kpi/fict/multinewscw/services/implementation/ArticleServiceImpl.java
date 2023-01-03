package ua.kpi.fict.multinewscw.services.implementation;

import com.sun.syndication.io.FeedException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.fict.multinewscw.entities.Article;
import ua.kpi.fict.multinewscw.entities.Customer;
import ua.kpi.fict.multinewscw.repositories.ArticleRepo;
import ua.kpi.fict.multinewscw.repositories.CustomerRepo;
import ua.kpi.fict.multinewscw.services.ArticleService;

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
    ArticleRssParser articleRssParser;

    @Autowired
    TranslateAPIParser translateAPIParser;

    private final String PRAVDA_LINK = "https://www.pravda.com.ua/rss/";
    private final String CNN_LINK = "http://rss.cnn.com/rss/cnn_topstories.rss";
    private final String FOX_LINK = "https://moxie.foxnews.com/google-publisher/world.xml";
    private final String UNIAN_LINK = "https://rss.unian.net/site/news_ukr.rss";

    private final Long PRAVDA_ID = 17L;
    private final Long CNN_ID = 18L;
    private final Long FOX_ID = 19L;
    private final Long UNIAN_ID = 20L;

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

    public void createArticle(Article article, Principal principal) throws IOException, ParseException {
        if (principal == null) {
            article.setCustomer(new Customer());
        } else {
            article.setCustomer(customerRepo.findCustomerByUsername(principal.getName()));
        }
        article.setArticleDate(Date.from(Instant.now()));
        addTranslation(article);
        articleRepo.save(article);
        if (article.getArticleLink().isEmpty()){
            article.setArticleLink("http://localhost:8080/articles/" + article.getArticleId());
            articleRepo.save(article);
        }
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
        ArrayList<Article> listFromRss = articleRssParser.doParse(link);
        for (Article articleRss : listFromRss) {
            if (articleRepo.findArticleByArticleLink(articleRss.getArticleLink()) == null) {
                if (Objects.equals(link, PRAVDA_LINK)) {
                    articleRss.setCustomer(customerRepo.findById(PRAVDA_ID).get());
                    if (articleRss.getArticleSource().isEmpty()) articleRss.setArticleSource("Українська правда");
                }
                if (Objects.equals(link, CNN_LINK)) {
                    articleRss.setCustomer(customerRepo.findById(CNN_ID).get());
                    if (articleRss.getArticleSource().isEmpty()) articleRss.setArticleSource("CNN");
                }
                if (Objects.equals(link, FOX_LINK)) {
                    articleRss.setCustomer(customerRepo.findById(FOX_ID).get());
                    if (articleRss.getArticleSource().isEmpty()) articleRss.setArticleSource("FOX NEWS");
                }
                if (Objects.equals(link, UNIAN_LINK)) {
                    articleRss.setCustomer(customerRepo.findById(UNIAN_ID).get());
                    if (articleRss.getArticleSource().isEmpty()) articleRss.setArticleSource("УНІАН");
                }
                articleRepo.save(articleRss);
            }

        }
    }

    public void addTranslation(Article article) throws IOException, ParseException {
        if (article.getArticleTitleEn() == null) {
            String translatedTitle = translateAPIParser.doParse(article.getArticleTitle());
            article.setArticleTitleEn(translatedTitle);
        }
        if (article.getArticleDescriptionEn() == null) {
            String translatedDescription = translateAPIParser.doParse(article.getArticleDescription());
            article.setArticleDescriptionEn(translatedDescription);
        }
        articleRepo.save(article);
    }
    public void addTranslation(Article article, String sourceLang, String targetLang) throws IOException, ParseException {
        if (Objects.equals(sourceLang, "uk")){
            article.setArticleTitleEn(translateAPIParser.doParse(article.getArticleTitle(), sourceLang, targetLang));
            article.setArticleDescriptionEn(translateAPIParser.doParse(article.getArticleDescription(), sourceLang, targetLang));
        }
        if (Objects.equals(sourceLang, "en")){
            article.setArticleTitle(translateAPIParser.doParse(article.getArticleTitleEn(), sourceLang, targetLang));
            article.setArticleDescription(translateAPIParser.doParse(article.getArticleDescriptionEn(), sourceLang, targetLang));
        }

        articleRepo.save(article);
    }
}

