package ua.kpi.fict.multinewscw.services.implementation;

import com.sun.syndication.io.FeedException;
import lombok.RequiredArgsConstructor;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepo articleRepo;

    private final CustomerRepo customerRepo;

    private final ArticleRssParser articleRssParser;

    private final TranslateAPIParser translateAPIParser;

    private final CategoryParser categoryParser;

    private final ESArticleRepo elasticArticleRepo;

    private final String PRAVDA_LINK = "https://www.pravda.com.ua/rss/";
    private final String CNN_LINK = "http://rss.cnn.com/rss/cnn_topstories.rss";
    private final String FOX_LINK = "https://moxie.foxnews.com/google-publisher/world.xml";
    private final String UNIAN_LINK = "https://rss.unian.net/site/news_ukr.rss";

    private final Long PRAVDA_ID = 5L;
    private final Long CNN_ID = 4L;
    private final Long FOX_ID = 3L;
    private final Long UNIAN_ID = 2L;

    public Article findById(long artId) {
        return articleRepo.findById(artId).orElse(null);
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
        } else {
            article.setArticleLink(fixUrl(article.getArticleLink()));
            esSaveArticle(article);
            articleRepo.save(article);
        }
    }


    private String fixUrl(String url) {
        if (isValidUrl(url)) {
            return url;
        } else {
            return "http://www." + url;
        }
    }

    private boolean isValidUrl(String url) {
        String regex = "^(http|https)://[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})?/?.*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }


    public List<Article> viewAllArticles() {
        return articleRepo.findAll();
    }

    private String formatString(String input) {
        return input.replaceAll(" ", "%20");
    }

    public List<Article> listArticles(String searchWord, String searchSource, String language, String newsDate, String searchCategory) {
        List<Article> articles = new ArrayList<>();

        searchWord = formatString(searchWord);

        switch (searchCriteria(searchWord, searchSource, newsDate, searchCategory)) {
            case ALL_ARTICLES -> articles = viewAllArticles();
            case SEARCH_BY_WORD -> articles = findBySearchWord(searchWord);
            case SEARCH_BY_WORD_AND_DATE -> {
                if (language.equals("en")) {
                    articles = elasticArticleRepo.findArticleByArticleDateMatchesAndArticleTitleEnLikeIgnoreCaseOrArticleDescriptionEnLikeIgnoreCase(
                            java.sql.Date.valueOf(LocalDate.parse(newsDate)), searchWord, searchWord);
                } else {
                    articles = elasticArticleRepo.findArticleByArticleDateMatchesAndArticleTitleLikeIgnoreCaseOrArticleDescriptionLikeIgnoreCase(
                            java.sql.Date.valueOf(LocalDate.parse(newsDate)), searchWord, searchWord);
                }
            }
            case SEARCH_BY_WORD_SOURCE_AND_DATE ->
                    articles = findByWordSourceAndDate(searchWord, searchSource, newsDate, language);
            case SEARCH_BY_SOURCE -> articles = findBySource(searchSource);
            case SEARCH_BY_SOURCE_AND_DATE -> articles = findBySourceAndDate(searchSource, newsDate);
            case SEARCH_BY_WORD_AND_SOURCE -> articles = findByWordAndSource(searchWord, searchSource, language);
            case SEARCH_BY_DATE ->
                    articles = elasticArticleRepo.findArticleByArticleDateMatches(java.sql.Date.valueOf(LocalDate.parse(newsDate)));
            case SEARCH_BY_CATEGORY ->
                    articles = elasticArticleRepo.findArticleByCategoriesIsLikeIgnoreCase(Set.of(Category.valueOf(searchCategory)));
            case SEARCH_BY_CATEGORY_AND_DATE ->
                    articles = elasticArticleRepo.findArticleByArticleDateMatchesAndCategoriesIsLikeIgnoreCase(
                            java.sql.Date.valueOf(LocalDate.parse(newsDate)), Set.of(Category.valueOf(searchCategory)));
        }

        // Filter by category and date if necessary
        if (!searchCategory.equals("")) {
            articles = articles.stream()
                    .filter(article -> article.getCategories().contains(Category.valueOf(searchCategory)))
                    .toList();
        }

        return articles;
    }

    private SearchCriteria searchCriteria(String searchWord, String searchSource, String newsDate, String searchCategory) {
        if (searchWord.equals("") && searchSource.equals("") && newsDate.equals("") && searchCategory.equals("")) {
            return SearchCriteria.ALL_ARTICLES;
        } else if (!searchWord.isEmpty() && searchSource.isEmpty() && newsDate.isEmpty()) {
            return SearchCriteria.SEARCH_BY_WORD;
        } else if (!searchWord.isEmpty() && searchSource.isEmpty() && !newsDate.equals("")) {
            return SearchCriteria.SEARCH_BY_WORD_AND_DATE;
        } else if (!searchWord.isEmpty() && !searchSource.isEmpty() && !newsDate.isEmpty()) {
            return SearchCriteria.SEARCH_BY_WORD_SOURCE_AND_DATE;
        } else if (searchWord.isEmpty() && !searchSource.isEmpty() && newsDate.isEmpty()) {
            return SearchCriteria.SEARCH_BY_SOURCE;
        } else if (searchWord.isEmpty() && !searchSource.isEmpty() && !newsDate.isEmpty()) {
            return SearchCriteria.SEARCH_BY_SOURCE_AND_DATE;
        } else if (!searchWord.isEmpty() && !searchSource.isEmpty() && newsDate.isEmpty()) {
            return SearchCriteria.SEARCH_BY_WORD_AND_SOURCE;
        } else if (!newsDate.isEmpty() && searchWord.isEmpty() && searchSource.isEmpty() && searchCategory.isEmpty()) {
            return SearchCriteria.SEARCH_BY_DATE;
        } else if (searchWord.isEmpty() && searchSource.isEmpty() && newsDate.isEmpty() && !searchCategory.isEmpty()) {
            return SearchCriteria.SEARCH_BY_CATEGORY;
        } else if (searchWord.isEmpty() && searchSource.isEmpty() && !newsDate.isEmpty() && !searchCategory.isEmpty()) {
            return SearchCriteria.SEARCH_BY_CATEGORY_AND_DATE;
        } else {
            return SearchCriteria.UNKNOWN;
        }
    }

    private enum SearchCriteria {
        ALL_ARTICLES,
        SEARCH_BY_WORD,
        SEARCH_BY_WORD_AND_DATE,
        SEARCH_BY_WORD_SOURCE_AND_DATE,
        SEARCH_BY_SOURCE,
        SEARCH_BY_SOURCE_AND_DATE,
        SEARCH_BY_WORD_AND_SOURCE,
        SEARCH_BY_DATE,
        SEARCH_BY_CATEGORY,
        SEARCH_BY_CATEGORY_AND_DATE,
        UNKNOWN
    }

    private List<Article> findByWordSourceAndDate(String searchWord, String searchSource, String newsDate, String language) {
        switch (searchSource) {
            case "UP" -> {
                if (language.equals("en"))
                    return elasticArticleRepo
                            .findArticleByArticleSourceAndArticleDateMatchesAndArticleTitleEnLikeIgnoreCaseOrArticleDescriptionEnLikeIgnoreCase
                                    ("Українська правда", java.sql.Date.valueOf(LocalDate.parse(newsDate)), searchWord, searchWord);
                else
                    return elasticArticleRepo
                            .findArticleByArticleSourceAndArticleDateMatchesAndArticleTitleLikeIgnoreCaseOrArticleDescriptionLikeIgnoreCase
                                    ("Українська правда", java.sql.Date.valueOf(LocalDate.parse(newsDate)), searchWord, searchWord);
            }
            case "UNIAN" -> {
                if (language.equals("en"))
                    return elasticArticleRepo
                            .findArticleByArticleSourceAndArticleDateMatchesAndArticleTitleEnLikeIgnoreCaseOrArticleDescriptionEnLikeIgnoreCase
                                    ("УНІАН", java.sql.Date.valueOf(LocalDate.parse(newsDate)), searchWord, searchWord);
                else
                    return elasticArticleRepo
                            .findArticleByArticleSourceAndArticleDateMatchesAndArticleTitleLikeIgnoreCaseOrArticleDescriptionLikeIgnoreCase
                                    ("УНІАН", java.sql.Date.valueOf(LocalDate.parse(newsDate)), searchWord, searchWord);
            }
            case "CNN" -> {
                if (language.equals("en"))
                    return elasticArticleRepo
                            .findArticleByArticleSourceAndArticleDateMatchesAndArticleTitleEnLikeIgnoreCaseOrArticleDescriptionEnLikeIgnoreCase
                                    ("CNN", java.sql.Date.valueOf(LocalDate.parse(newsDate)), searchWord, searchWord);
                else
                    return elasticArticleRepo
                            .findArticleByArticleSourceAndArticleDateMatchesAndArticleTitleLikeIgnoreCaseOrArticleDescriptionLikeIgnoreCase
                                    ("CNN", java.sql.Date.valueOf(LocalDate.parse(newsDate)), searchWord, searchWord);
            }
            case "FOX" -> {
                if (language.equals("en"))
                    return elasticArticleRepo
                            .findArticleByArticleSourceAndArticleDateMatchesAndArticleTitleEnLikeIgnoreCaseOrArticleDescriptionEnLikeIgnoreCase
                                    ("FOX NEWS", java.sql.Date.valueOf(LocalDate.parse(newsDate)), searchWord, searchWord);
                else
                    return elasticArticleRepo
                            .findArticleByArticleSourceAndArticleDateMatchesAndArticleTitleLikeIgnoreCaseOrArticleDescriptionLikeIgnoreCase
                                    ("FOX NEWS", java.sql.Date.valueOf(LocalDate.parse(newsDate)), searchWord, searchWord);
            }
        }
        return viewAllArticles();
    }

    private List<Article> findBySourceAndDate(String searchSource, String newsDate) {
        switch (searchSource) {
            case "UP" -> {
                return elasticArticleRepo
                        .findArticleByArticleSourceAndArticleDateMatches
                                ("Українська правда", java.sql.Date.valueOf(LocalDate.parse(newsDate)));
            }
            case "UNIAN" -> {
                return elasticArticleRepo
                        .findArticleByArticleSourceAndArticleDateMatches
                                ("УНІАН", java.sql.Date.valueOf(LocalDate.parse(newsDate)));
            }
            case "CNN" -> {
                return elasticArticleRepo
                        .findArticleByArticleSourceAndArticleDateMatches
                                ("CNN", java.sql.Date.valueOf(LocalDate.parse(newsDate)));
            }
            case "FOX" -> {
                return elasticArticleRepo
                        .findArticleByArticleSourceAndArticleDateMatches
                                ("FOX NEWS", java.sql.Date.valueOf(LocalDate.parse(newsDate)));
            }
        }
        return viewAllArticles();
    }

    private List<Article> findByWordAndSource(String searchWord, String searchSource, String language) { // todo use map
        switch (searchSource) {
            case "UP" -> {
                if (language.equals("en"))
                    return elasticArticleRepo.findArticleByArticleSourceAndArticleTitleEnLikeIgnoreCaseOrArticleDescriptionEnLikeIgnoreCase("Українська правда", searchWord, searchWord);
                else
                    return elasticArticleRepo.findArticleByArticleSourceAndArticleTitleLikeIgnoreCaseOrArticleDescriptionLikeIgnoreCase("Українська правда", searchWord, searchWord);
            }
            case "UNIAN" -> {
                if (language.equals("en"))
                    return elasticArticleRepo.findArticleByArticleSourceAndArticleTitleEnLikeIgnoreCaseOrArticleDescriptionEnLikeIgnoreCase("УНІАН", searchWord, searchWord);
                else
                    return elasticArticleRepo.findArticleByArticleSourceAndArticleTitleLikeIgnoreCaseOrArticleDescriptionLikeIgnoreCase("УНІАН", searchWord, searchWord);
            }
            case "CNN" -> {
                if (language.equals("en"))
                    return elasticArticleRepo.findArticleByArticleSourceAndArticleTitleEnLikeIgnoreCaseOrArticleDescriptionEnLikeIgnoreCase("CNN", searchWord, searchWord);
                else
                    return elasticArticleRepo.findArticleByArticleSourceAndArticleTitleLikeIgnoreCaseOrArticleDescriptionLikeIgnoreCase("CNN", searchWord, searchWord);
            }
            case "FOX" -> {
                if (language.equals("en"))
                    return elasticArticleRepo.findArticleByArticleSourceAndArticleTitleEnLikeIgnoreCaseOrArticleDescriptionEnLikeIgnoreCase("FOX NEWS", searchWord, searchWord);
                else
                    return elasticArticleRepo.findArticleByArticleSourceAndArticleTitleLikeIgnoreCaseOrArticleDescriptionLikeIgnoreCase("FOX NEWS", searchWord, searchWord);
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
                    articleRss.getCategories()
                            .add(categoryParser
                                    .doParse(articleRss.getArticleTitleEn() + articleRss.getArticleDescriptionEn()));
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
        esArticle.setCategories(article.getCategories());
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
        Set<Article> articles = esFindByTitle(title);
        articles.addAll(esFindByDesc(title));
        return new ArrayList<>(articles);
    }

    private Set<Article> esFindByTitle(final String title) {
        return elasticArticleRepo.findByArticleTitleLikeIgnoreCaseOrArticleTitleEnLikeIgnoreCase(title, title);
    }

    private Set<Article> esFindByDesc(final String title) {
        return elasticArticleRepo.findByArticleDescriptionLikeIgnoreCaseOrArticleDescriptionEnLikeIgnoreCase(title, title);
    }
}