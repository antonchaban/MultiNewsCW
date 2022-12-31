package ua.kpi.fict.multinewscw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.fict.multinewscw.entities.ArticleMain;
import ua.kpi.fict.multinewscw.entities.InfoLanguageID;

import java.util.List;

@Repository
public interface ArticleMainRepo extends JpaRepository<ArticleMain, InfoLanguageID> {
    List<ArticleMain> findArticleMainByIdArticleId_Customer(String username);
}
