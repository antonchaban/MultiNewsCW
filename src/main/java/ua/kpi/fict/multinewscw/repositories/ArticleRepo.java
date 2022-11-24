package ua.kpi.fict.multinewscw.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.kpi.fict.multinewscw.entities.Article;
import ua.kpi.fict.multinewscw.entities.Customer;

import java.util.List;

public interface ArticleRepo extends CrudRepository<Article, Long> {
    List<Article> findArticleByCustomerUserName(String userName);
}
