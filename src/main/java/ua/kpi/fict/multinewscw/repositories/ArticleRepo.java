package ua.kpi.fict.multinewscw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.fict.multinewscw.entities.Article;

import java.util.List;

public interface ArticleRepo extends JpaRepository<Article, Long> {
    List<Article> findArticleByCustomerUsername(String username);

    Article findArticleByArticleLink(String link);
}
