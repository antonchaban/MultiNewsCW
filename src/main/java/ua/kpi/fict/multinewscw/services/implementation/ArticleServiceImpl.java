package ua.kpi.fict.multinewscw.services.implementation;

import com.sun.syndication.io.FeedException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.fict.multinewscw.elasticrepo.ESArticleRepo;
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

    @Autowired
    private ESArticleRepo elasticArticleRepo;

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

    public void createArticle(Article article, Principal principal, String language) throws IOException, ParseException {
        if (principal == null) {
            article.setCustomer(new Customer());
        } else {
            article.setCustomer(customerRepo.findCustomerByUsername(principal.getName()));
        }
        switch (language) {
            case "en":
                addTranslation(article, "en", "uk");
                break;
            case "uk":
                addTranslation(article, "uk", "en");
                break;
        }
        article.setArticleDate(Date.from(Instant.now()));
        articleRepo.save(article);
        if (article.getArticleLink().isEmpty()) {
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

    public List<Article> listArticles(String searchWord, String searchSource) { // todo implement combined search
        List<Article> articles = new ArrayList<>();
        if (searchWord == null || searchWord.equals("") || searchSource == null || searchSource.equals(""))
            articles = viewAllArticles();
        if (searchWord != null && !searchWord.equals("")) {
            articles = findBySearchWord(searchWord);
        }
        if ("UP".equals(searchSource)) {
            return articles = elasticArticleRepo.findArticleByArticleSource("???????????????????? ????????????");
        } else if ("UNIAN".equals(searchSource)) {
            return articles = elasticArticleRepo.findArticleByArticleSource("??????????");
        } else if ("CNN".equals(searchSource)) {
            return articles = elasticArticleRepo.findArticleByArticleSource("CNN");
        } else if ("FOX".equals(searchSource)) {
            return articles = elasticArticleRepo.findArticleByArticleSource("FOX NEWS");
        }
        return articles;
    }

    private List<Article> findBySearchWord(String searchWord) {
        List<Article> articles = new ArrayList<>();
        articles = esFindByTittleAndDescAllLang(searchWord);
        articles.forEach(article -> article.setCustomer(customerRepo.findCustomerByCustomerId(article.getCustomerId())));
        return articles;
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

    public void editArticle(Article updatedArticle, Long id, String language) throws IOException, ParseException {
        Article article = articleRepo.findById(id).orElse(null);
        if (article != null) {
            switch (language) {
                case "en":
                    article.setArticleTitleEn(updatedArticle.getArticleTitleEn());
                    article.setArticleDescriptionEn(updatedArticle.getArticleDescriptionEn());
                    addTranslation(article, "en", "uk");
                    break;
                case "uk":
                    article.setArticleTitle(updatedArticle.getArticleTitle());
                    article.setArticleDescription(updatedArticle.getArticleDescription());
                    addTranslation(article, "uk", "en");
                    break;
            }
            article.setArticleLink(updatedArticle.getArticleLink());
            article.setArticleSource(updatedArticle.getArticleSource());
            article.setArticleDate(Date.from(Instant.now()));
            articleRepo.save(article);
        } else {
            System.err.println("Article with id" + id + "is not found");
        }
    }

    public void parseArticle(String link) throws FeedException, IOException, ParseException {
        ArrayList<Article> listFromRss = articleRssParser.doParse(link);
        for (Article articleRss : listFromRss) {
            if (articleRepo.findArticleByArticleLink(articleRss.getArticleLink()) == null) {
                switch (link) {
                    case PRAVDA_LINK:
                        parseAssist(articleRss, PRAVDA_ID, "PRAVDA");
                        break;
                    case CNN_LINK:
                        parseAssist(articleRss, CNN_ID, "CNN");
                        break;
                    case FOX_LINK:
                        parseAssist(articleRss, FOX_ID, "FOX NEWS");
                        break;
                    case UNIAN_LINK:
                        parseAssist(articleRss, UNIAN_ID, "??????????");
                        break;
                }
                if (articleRss.getArticleDate() != null) {
                    articleRepo.save(articleRss);
                }
            }

        }
    }

    private void parseAssist(Article articleRss, Long ID, String source) throws IOException, ParseException {
        try {
            articleRss.setCustomer(customerRepo.findById(ID).get());
            if (articleRss.getArticleSource().isEmpty()) articleRss.setArticleSource(source);
            try {
                if (articleRss.getArticleTitleEn().isEmpty()) {
                    addTranslation(articleRss, "uk", "en");
                } else addTranslation(articleRss, "en", "uk");
            } catch (NullPointerException e) {
                addTranslation(articleRss, "uk", "en");
            }
        } catch (NullPointerException e) {
            System.err.println("Error in parseAssist");
        }
    }

    public void addTranslation(Article article, String sourceLang, String targetLang) throws IOException, ParseException {
        switch (sourceLang) {
            case "uk":
                article.setArticleTitleEn(translateAPIParser.doParse(article.getArticleTitle(), sourceLang, targetLang));
                article.setArticleDescriptionEn(translateAPIParser.doParse(article.getArticleDescription(), sourceLang, targetLang));
                break;
            case "en":
                article.setArticleTitle(translateAPIParser.doParse(article.getArticleTitleEn(), sourceLang, targetLang));
                article.setArticleDescription(translateAPIParser.doParse(article.getArticleDescriptionEn(), sourceLang, targetLang));
                break;
        }
        articleRepo.save(article);
    }

    // For ElasticSearch

    public void esSaveAll() {
        List<Article> articles = articleRepo.findAll();
        for (Article article : articles) {
            Article esArticle = new Article();
            esArticle.setArticleId(article.getArticleId());
            esArticle.setArticleTitle(article.getArticleTitle());
            esArticle.setArticleDescription(article.getArticleDescription());
            esArticle.setArticleTitleEn(article.getArticleTitleEn());
            esArticle.setArticleDescriptionEn(article.getArticleDescriptionEn());
            esArticle.setArticleDate(article.getArticleDate());
            esArticle.setArticleLink(article.getArticleLink());
            esArticle.setArticleSource(article.getArticleSource());
            esArticle.setCustomerId(article.getCustomer().getCustomerId());
            elasticArticleRepo.save(esArticle);
        }
    }

    public Article esFindById(final Long id) {
        return elasticArticleRepo.findById(id).orElse(null);
    }

    private List<Article> esFindByTittleAndDescAllLang(String title) {
        List<Article> articles = esFindByTitle(title);
        articles.addAll(esFindByDesc(title));
        return articles;
    }

    private List<Article> esFindByTitle(final String title) {
        return elasticArticleRepo.findByArticleTitleOrArticleTitleEn(title, title);
    }

    private List<Article> esFindByDesc(final String title) {
        return elasticArticleRepo.findByArticleDescriptionOrArticleDescriptionEn(title, title);
    }

    public List<Article> esFindAll() {
        return (List<Article>) elasticArticleRepo.findAll();
    }
}