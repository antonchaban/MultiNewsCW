package ua.kpi.fict.multinewscw.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.kpi.fict.multinewscw.entities.Article;

public interface ArticleRepo extends CrudRepository<Article, Long> {
}
