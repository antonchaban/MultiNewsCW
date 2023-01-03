package ua.kpi.fict.multinewscw.services;

import com.sun.syndication.io.FeedException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import ua.kpi.fict.multinewscw.services.implementation.ArticleServiceImpl;

import java.io.IOException;

@Service
public class ArticleScheduler {
    private final String[] RESOURCES_LIST = {
            "https://www.pravda.com.ua/rss/",
            "http://rss.cnn.com/rss/cnn_topstories.rss",
            "https://moxie.foxnews.com/google-publisher/world.xml",
            "https://rss.unian.net/site/news_ukr.rss"};
    // TODO https://nv.ua/ukr/rss/all.xml - alternative
    @Autowired
    private ArticleServiceImpl articleService;

    @Scheduled(initialDelay = 10000, fixedDelayString = "PT30M") // on start and every 30 minutes
    public void updateArticles() throws FeedException, IOException, ParseException {
        for (String resource : RESOURCES_LIST) {
            articleService.parseArticle(resource);
            System.out.println("Update articles from: " + resource);
        }
    }


}
