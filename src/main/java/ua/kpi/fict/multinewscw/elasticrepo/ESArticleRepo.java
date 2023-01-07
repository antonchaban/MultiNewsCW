package ua.kpi.fict.multinewscw.elasticrepo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ua.kpi.fict.multinewscw.entities.Article;

import java.util.List;

public interface ESArticleRepo extends ElasticsearchRepository<Article, Long> {
    List<Article> findByArticleTitle(String title);
}
