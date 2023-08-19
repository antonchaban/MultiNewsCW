package ua.kpi.fict.multinewscw.elasticrepo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import ua.kpi.fict.multinewscw.entities.Article;

import java.util.Date;
import java.util.List;
import java.util.Set;

@EnableElasticsearchRepositories
public interface ESArticleRepo extends ElasticsearchRepository<Article, Long> {
    Set<Article> findByArticleTitleOrArticleTitleEn(String articleTitle, String articleTitleEn);

    Set<Article> findByArticleDescriptionOrArticleDescriptionEn(String articleDescription, String articleDescriptionEn);

    List<Article> findArticleByArticleSource(String source);

    List<Article> findArticleByArticleDateMatches(Date date);

    List<Article> findArticleByArticleSourceAndArticleDescriptionOrArticleTitle
            (String articleSource, String articleDescription, String articleTitle);

    List<Article> findArticleByArticleSourceAndArticleDescriptionEnOrArticleTitleEn
            (String articleSource, String articleDescription, String articleTitle);

    List<Article> findArticleByArticleSourceAndArticleDateMatches
            (String articleSource, Date articleDate);

    List<Article> findArticleByArticleDateMatchesAndArticleDescriptionOrArticleTitle
            (Date articleDate, String articleDescription, String articleTitle);

    List<Article> findArticleByArticleDateMatchesAndArticleDescriptionEnOrArticleTitleEn
            (Date articleDate, String articleDescriptionEn, String articleTitleEn);

    List<Article> findArticleByArticleSourceAndArticleDateMatchesAndArticleDescriptionOrArticleTitle
            (String articleSource, Date articleDate, String articleDescription, String articleTitle);

    List<Article> findArticleByArticleSourceAndArticleDateMatchesAndArticleDescriptionEnOrArticleTitleEn
            (String articleSource, Date articleDate, String articleDescriptionEn, String articleTitleEn);
}
