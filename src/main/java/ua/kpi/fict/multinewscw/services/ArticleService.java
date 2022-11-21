package ua.kpi.fict.multinewscw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.fict.multinewscw.entities.Article;
import ua.kpi.fict.multinewscw.repositories.ArticleRepo;
import ua.kpi.fict.multinewscw.repositories.UserRepo;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepo articleRepo;

    @Autowired
    private UserRepo userRepo;

    public void save(Article article) {
        articleRepo.save(article);
    }
}
