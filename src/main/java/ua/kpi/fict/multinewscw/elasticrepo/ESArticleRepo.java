package ua.kpi.fict.multinewscw.elasticrepo;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import ua.kpi.fict.multinewscw.entities.Article;
import ua.kpi.fict.multinewscw.entities.enums.Category;

import java.util.Date;
import java.util.List;
import java.util.Set;

@EnableElasticsearchRepositories
public interface ESArticleRepo extends ElasticsearchRepository<Article, Long> {
    Set<Article> findByArticleTitleLikeIgnoreCaseOrArticleTitleEnLikeIgnoreCase(String articleTitle, String articleTitleEn);

    Set<Article> findByArticleDescriptionLikeIgnoreCaseOrArticleDescriptionEnLikeIgnoreCase(String articleDescription, String articleDescriptionEn);

    List<Article> findArticleByArticleSource(String source);

    List<Article> findArticleByArticleDateMatches(Date date);

    List<Article> findArticleByArticleSourceAndArticleDescriptionLikeIgnoreCaseOrArticleTitleLikeIgnoreCase
            (String articleSource, String articleDescription, String articleTitle);

    List<Article> findArticleByArticleSourceAndArticleDescriptionEnLikeIgnoreCaseOrArticleTitleEnLikeIgnoreCase
            (String articleSource, String articleDescription, String articleTitle);

    List<Article> findArticleByArticleSourceAndArticleDateMatches
            (String articleSource, Date articleDate);

    List<Article> findArticleByArticleDateMatchesAndArticleDescriptionLikeIgnoreCaseOrArticleTitleLikeIgnoreCase
            (Date articleDate, String articleDescription, String articleTitle);

    List<Article> findArticleByArticleDateMatchesAndArticleDescriptionEnLikeIgnoreCaseOrArticleTitleEnLikeIgnoreCase
            (Date articleDate, String articleDescriptionEn, String articleTitleEn);

    List<Article> findArticleByArticleSourceAndArticleDateMatchesAndArticleDescriptionLikeIgnoreCaseOrArticleTitleLikeIgnoreCase
            (String articleSource, Date articleDate, String articleDescription, String articleTitle);

    List<Article> findArticleByArticleSourceAndArticleDateMatchesAndArticleDescriptionEnLikeIgnoreCaseOrArticleTitleEnLikeIgnoreCase
            (String articleSource, Date articleDate, String articleDescriptionEn, String articleTitleEn);

    List<Article> findArticleByCategoriesIsLikeIgnoreCase(Set<Category> categories);

    List<Article> findArticleByArticleDateMatchesAndCategoriesIsLikeIgnoreCase
            (Date articleDate, Set<Category> categories);
}
