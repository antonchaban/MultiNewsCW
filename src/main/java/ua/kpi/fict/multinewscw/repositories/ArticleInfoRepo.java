package ua.kpi.fict.multinewscw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.fict.multinewscw.entities.ArticleInfo;

@Repository
public interface ArticleInfoRepo extends JpaRepository<ArticleInfo, Long> {
}
