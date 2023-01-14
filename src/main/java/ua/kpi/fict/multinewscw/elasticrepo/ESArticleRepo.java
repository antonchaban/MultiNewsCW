package ua.kpi.fict.multinewscw.elasticrepo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ua.kpi.fict.multinewscw.entities.Article;

import java.util.Date;
import java.util.List;

public interface ESArticleRepo extends ElasticsearchRepository<Article, Long> {
    List<Article> findByArticleTitleOrArticleDescription(String articleTitle, String articleDescription);

    List<Article> findByArticleTitleOrArticleTitleEn(String articleTitle, String articleTitleEn);

    List<Article> findByArticleDescriptionOrArticleDescriptionEn(String articleDescription, String articleDescriptionEn);

    List<Article> findArticleByArticleSource(String source);

    List<Article> findArticleByArticleDate(Date date);

    List<Article> findArticleByArticleTitleOrArticleTitleEnAndArticleDescriptionOrArticleDescriptionEnAndArticleSource(
            String articleTitle, String articleTitleEn, String articleDescription,
            String articleDescriptionEn, String source); // todo test
}
