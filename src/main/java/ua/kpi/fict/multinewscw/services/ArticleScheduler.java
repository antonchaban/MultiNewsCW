package ua.kpi.fict.multinewscw.services;

import com.sun.syndication.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@Service
public class ArticleScheduler {
    @Autowired
    private ArticleServiceImpl articleService;

    @Scheduled(initialDelay = 30000, fixedDelay = 30000)
    public void updateArticles() throws FeedException, IOException {
        articleService.parseArticle("https://www.pravda.com.ua/rss/");
        System.out.println("Update articles");
    }


}
