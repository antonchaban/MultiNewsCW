package ua.kpi.fict.multinewscw.elasticrepo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import ua.kpi.fict.multinewscw.entities.Article;

import java.util.Date;
import java.util.List;
import java.util.Set;

@EnableElasticsearchRepositories
public interface ESArticleRepo extends ElasticsearchRepository<Article, Long> {
    List<Article> findByArticleTitleOrArticleDescription(String articleTitle, String articleDescription);

    Set<Article> findByArticleTitleOrArticleTitleEn(String articleTitle, String articleTitleEn);

    Set<Article> findByArticleDescriptionOrArticleDescriptionEn(String articleDescription, String articleDescriptionEn);

    List<Article> findArticleByArticleSource(String source);

    List<Article> findArticleByArticleDateMatches(Date date);

    List<Article> findArticleByArticleSourceAndArticleDescription(String source, String articleTitle);

    List<Article> findArticleByArticleSourceAndArticleDescriptionOrArticleTitle(String articleSource, String articleDescription, String articleTitle);

    List<Article> findArticleByArticleSourceAndArticleDescriptionEnOrArticleTitleEn(String articleSource, String articleDescription, String articleTitle);
}
