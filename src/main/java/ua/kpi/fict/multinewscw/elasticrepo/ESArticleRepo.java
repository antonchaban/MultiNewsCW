package ua.kpi.fict.multinewscw.elasticrepo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ua.kpi.fict.multinewscw.entities.Article;

public interface ESArticleRepo extends ElasticsearchRepository<Article, Long> {
}
