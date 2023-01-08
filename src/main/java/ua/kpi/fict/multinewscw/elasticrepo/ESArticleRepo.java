package ua.kpi.fict.multinewscw.elasticrepo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ua.kpi.fict.multinewscw.entities.Article;

import java.util.List;

public interface ESArticleRepo extends ElasticsearchRepository<Article, Long> {
    List<Article> findByArticleTitleOrArticleDescription(String articleTitle, String articleDescription);

    List<Article> findByArticleTitleOrArticleTitleEn(String articleTitle, String articleTitleEn);

    List<Article> findByArticleDescriptionOrArticleDescriptionEn(String articleDescription, String articleDescriptionEn);
}
