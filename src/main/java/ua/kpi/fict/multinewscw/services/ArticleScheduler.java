package ua.kpi.fict.multinewscw.services;

import com.sun.syndication.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ArticleScheduler {
    @Autowired
    private ArticleServiceImpl articleService;

    @Sheduled
    public void updateArticles() throws FeedException, IOException {
        articleService.parseArticle("A");
    }


}
