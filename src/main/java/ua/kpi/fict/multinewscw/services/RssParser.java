package ua.kpi.fict.multinewscw.services;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.fict.multinewscw.entities.Article;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class RssParser implements Parser<Article> {
    @Autowired
    URLConnector urlConnector;

    @Override
    public ArrayList<Article> doParse(String resource) throws IOException, FeedException {
        HttpURLConnection connection = urlConnector.setConnection(resource);
        ArrayList<Article> list = new ArrayList<>();
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(connection));
            List entries = feed.getEntries();
            Iterator itEntries = entries.iterator();


            while (itEntries.hasNext()) {
                SyndEntry entry = (SyndEntry) itEntries.next();
                Article article = new Article();
//                System.out.println("######");
                article.setArticleTitle(entry.getTitle());
//                System.out.println("Title: " + entry.getTitle());
                article.setArticleLink(entry.getLink());
//                System.out.println("Link: " + entry.getLink());
                if (entry.getAuthor() != null) {
                    article.setArticleSource(entry.getAuthor());
//                    System.out.println("Author: " + entry.getAuthor());
                }

                if (entry.getDescription() != null) {
                    article.setArticleDescription(entry.getDescription().getValue());
//                    System.out.println("Desc: " + entry.getDescription().getValue());
                }

                article.setArticleDate(entry.getPublishedDate());
//                System.out.println("Date: " + entry.getPublishedDate());
                list.add(article);
            }

        } finally {
            urlConnector.endConnection(connection);
        }
        return list; //todo
    }
}
