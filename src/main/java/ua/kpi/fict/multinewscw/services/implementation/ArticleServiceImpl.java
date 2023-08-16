package ua.kpi.fict.multinewscw.services.implementation;

import com.sun.syndication.io.FeedException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.fict.multinewscw.elasticrepo.ESArticleRepo;
import ua.kpi.fict.multinewscw.entities.Article;
import ua.kpi.fict.multinewscw.entities.Customer;
import ua.kpi.fict.multinewscw.entities.enums.Category;
import ua.kpi.fict.multinewscw.repositories.ArticleRepo;
import ua.kpi.fict.multinewscw.repositories.CustomerRepo;
import ua.kpi.fict.multinewscw.services.ArticleService;

import java.io.IOException;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    private final Long PRAVDA_ID = 5L;
    private final Long CNN_ID = 4L;
    private final Long FOX_ID = 3L;
    private final Long UNIAN_ID = 2L;

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

    public void createArticle(Article article, Principal principal, String language, String category) throws IOException, ParseException {
        if (principal == null) {
            article.setCustomer(new Customer());
        } else {
            article.setCustomer(customerRepo.findCustomerByUsername(principal.getName()));
        }
        switch (language) {
            case "en" -> addTranslation(article, "en", "uk");
            case "uk" -> addTranslation(article, "uk", "en");
        }
        article.setArticleDate(Date.from(Instant.now()));
        try {
            article.getCategories().add(Category.valueOf(category));
        } catch (NullPointerException e) {
            System.out.println("Category is null, setting other value");
            article.getCategories().add(Category.CATEGORY_OTHER);
        }
        articleRepo.save(article);
        esSaveArticle(article);
        if (article.getArticleLink().isEmpty()) {
            article.setArticleLink("http://localhost:8080/articles/" + article.getArticleId());
            esSaveArticle(article);
            articleRepo.save(article);
        }
    }


    public List<Article> viewAllArticles() {
        return articleRepo.findAll();
    }

    public List<Article> getByAuthor(String authorUserName) {
        return articleRepo.findArticleByCustomerUsername(authorUserName);
    }

    public List<Article> listArticles(String searchWord, String searchSource, String language, String newsDate) {
        // todo date search
        List<Article> articles = new ArrayList<>();
        if (searchWord == null || searchWord.equals("") || searchSource == null || searchSource.equals(""))
            articles = viewAllArticles();
        if (searchWord != null && !searchWord.equals("") && searchSource.equals("")) {
            articles = findBySearchWord(searchWord);
        }
        if (searchWord == null || searchWord.equals("") && searchSource != null && !searchSource.equals("")) {
            articles = findBySource(searchSource);
        }
        if (searchWord != null && !searchWord.equals("") && searchSource != null && !searchSource.equals("")) {
            articles = findByWordAndSource(searchWord, searchSource, language);
        }
        return articles;
    }

    private List<Article> findByWordAndSource(String searchWord, String searchSource, String language) {
        switch (searchSource) {
            case "UP" -> {
                if (language.equals("en"))
                    return elasticArticleRepo.findArticleByArticleSourceAndArticleDescriptionEnOrArticleTitleEn("Українська правда", searchWord, searchWord);
                else
                    return elasticArticleRepo.findArticleByArticleSourceAndArticleDescriptionOrArticleTitle("Українська правда", searchWord, searchWord);
            }
            case "UNIAN" -> {
                if (language.equals("en"))
                    return elasticArticleRepo.findArticleByArticleSourceAndArticleDescriptionEnOrArticleTitleEn("УНІАН", searchWord, searchWord);
                else
                    return elasticArticleRepo.findArticleByArticleSourceAndArticleDescriptionOrArticleTitle("УНІАН", searchWord, searchWord);
            }
            case "CNN" -> {
                if (language.equals("en"))
                    return elasticArticleRepo.findArticleByArticleSourceAndArticleDescriptionEnOrArticleTitleEn("CNN", searchWord, searchWord);
                else
                    return elasticArticleRepo.findArticleByArticleSourceAndArticleDescriptionOrArticleTitle("CNN", searchWord, searchWord);
            }
            case "FOX" -> {
                if (language.equals("en"))
                    return elasticArticleRepo.findArticleByArticleSourceAndArticleDescriptionEnOrArticleTitleEn("FOX NEWS", searchWord, searchWord);
                else
                    return elasticArticleRepo.findArticleByArticleSourceAndArticleDescriptionOrArticleTitle("FOX NEWS", searchWord, searchWord);
            }
        }
        return viewAllArticles();
    }

    private List<Article> findBySource(String source) {
        switch (source) {
            case "UP" -> {
                return elasticArticleRepo.findArticleByArticleSource("Українська правда");
            }
            case "UNIAN" -> {
                return elasticArticleRepo.findArticleByArticleSource("УНІАН");
            }
            case "CNN" -> {
                return elasticArticleRepo.findArticleByArticleSource("CNN");
            }
            case "FOX" -> {
                return elasticArticleRepo.findArticleByArticleSource("FOX NEWS");
            }
        }
        return viewAllArticles();
    }

    private List<Article> findBySearchWord(String searchWord) {
        List<Article> articles;
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
                elasticArticleRepo.deleteById(id);
            } else System.err.println("Customer: " + customer.getUsername() + " haven't this article with id" + id);
        } else System.err.println("Article with id" + id + "is not found");
    }

    public void editArticle(Article updatedArticle, Long id, String language, String category) throws IOException, ParseException {
        Article article = articleRepo.findById(id).orElse(null);
        if (article != null) {
            switch (language) {
                case "en" -> {
                    article.setArticleTitleEn(updatedArticle.getArticleTitleEn());
                    article.setArticleDescriptionEn(updatedArticle.getArticleDescriptionEn());
                    addTranslation(article, "en", "uk");
                }
                case "uk" -> {
                    article.setArticleTitle(updatedArticle.getArticleTitle());
                    article.setArticleDescription(updatedArticle.getArticleDescription());
                    addTranslation(article, "uk", "en");
                }
            }
            article.setArticleLink(updatedArticle.getArticleLink());
            article.setArticleDate(Date.from(Instant.now()));
            article.setArticleSource(updatedArticle.getArticleSource());
            try {
                article.getCategories().clear();
                article.getCategories().add(Category.valueOf(category));
            } catch (NullPointerException e) {
                System.out.println("Category is null, setting other value");
                article.getCategories().clear();
                article.getCategories().add(Category.CATEGORY_OTHER);
            }
            articleRepo.save(article);
            esSaveArticle(article);
        } else {
            System.err.println("Article with id" + id + "is not found");
        }
    }

    public void parseArticle(String link) throws FeedException, IOException, ParseException {
        ArrayList<Article> listFromRss = articleRssParser.doParse(link);
        for (Article articleRss : listFromRss) {
            if (articleRepo.findArticleByArticleLink(articleRss.getArticleLink()) == null) {
                switch (link) {
                    case PRAVDA_LINK -> parseAssist(articleRss, PRAVDA_ID, "PRAVDA");
                    case CNN_LINK -> parseAssist(articleRss, CNN_ID, "CNN");
                    case FOX_LINK -> parseAssist(articleRss, FOX_ID, "FOX NEWS");
                    case UNIAN_LINK -> parseAssist(articleRss, UNIAN_ID, "УНІАН");
                }
                if (articleRss.getArticleDate() != null) {
                    articleRss.getCategories().add(Category.CATEGORY_OTHER); // todo set category by keywords
                    articleRepo.save(articleRss);
                    esSaveArticle(articleRss);
                    System.out.println("Articles added to ElasticSearch");
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

    private void addTranslation(Article article, String sourceLang, String targetLang) throws IOException, ParseException {
        switch (sourceLang) {
            case "uk" -> {
                article.setArticleTitleEn(translateAPIParser.doParse(article.getArticleTitle(), sourceLang, targetLang));
                article.setArticleDescriptionEn(translateAPIParser.doParse(article.getArticleDescription(), sourceLang, targetLang));
            }
            case "en" -> {
                article.setArticleTitle(translateAPIParser.doParse(article.getArticleTitleEn(), sourceLang, targetLang));
                article.setArticleDescription(translateAPIParser.doParse(article.getArticleDescriptionEn(), sourceLang, targetLang));
            }
        }
        articleRepo.save(article);
    }

    // For ElasticSearch

    private void esSaveArticle(Article article) {
        esCopyArticleData(article);
    }

    private void esCopyArticleData(Article article) {
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

    public void esSaveAll() {
        List<Article> articles = articleRepo.findAll();
        for (Article article : articles) {
            esCopyArticleData(article);
        }
    }

    public Article esFindById(final Long id) {
        return elasticArticleRepo.findById(id).orElse(null);
    }

    private List<Article> esFindByTittleAndDescAllLang(String title) {
        List<Article> result = new ArrayList<>();
        Set<Article> articles = esFindByTitle(title);
        articles.addAll(esFindByDesc(title));
        result.addAll(articles);
        return result;
    }

    private Set<Article> esFindByTitle(final String title) {
        return elasticArticleRepo.findByArticleTitleOrArticleTitleEn(title, title);
    }

    private Set<Article> esFindByDesc(final String title) {
        return elasticArticleRepo.findByArticleDescriptionOrArticleDescriptionEn(title, title);
    }

    public List<Article> esFindAll() {
        return (List<Article>) elasticArticleRepo.findAll();
    }
}