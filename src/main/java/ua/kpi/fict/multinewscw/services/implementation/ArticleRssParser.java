package ua.kpi.fict.multinewscw.services.implementation;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.fict.multinewscw.entities.Article;
import ua.kpi.fict.multinewscw.services.Parser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class ArticleRssParser implements Parser<ArrayList<Article>> {
    @Autowired
    URLConnector urlConnector;

    @Autowired
    TranslateAPIParser translateAPIParser;

    @Override
    public ArrayList<Article> doParse(String resource) throws IOException, FeedException, ParseException {
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
                String language = translateAPIParser.detectLanguage(entry.getTitle());
                switch (language) {
                    case "uk":
                        setUkArticle(article, entry);
                        break;
                    case "en":
                        setEnArticle(article, entry);
                        break;
                }
                list.add(article);
            }
        } finally {
            urlConnector.endConnection(connection);
        }
        return list;
    }


    private void setUkArticle(Article article, SyndEntry entry) {
        article.setArticleTitle(entry.getTitle());
        article.setArticleLink(entry.getLink());
        if (entry.getAuthor() != null) {
            article.setArticleSource(entry.getAuthor());
        }
        if (entry.getDescription() != null) {
            article.setArticleDescription(entry.getDescription().getValue());
        } else {
            article.setArticleDescription("Немає опису, перегляньте оригінальну статтю для отримання додаткової інформації");
        }
        if (entry.getPublishedDate() != null) {
            article.setArticleDate(entry.getPublishedDate());
        } else {
            article.setArticleDate(Date.from(Instant.now()));
        }
    }

    private void setEnArticle(Article article, SyndEntry entry) {
        article.setArticleTitleEn(entry.getTitle());
        article.setArticleLink(entry.getLink());
        if (entry.getAuthor() != null) {
            article.setArticleSource(entry.getAuthor());
        }
        if (entry.getDescription() != null) {
            article.setArticleDescriptionEn(entry.getDescription().getValue());
        } else {
            article.setArticleDescriptionEn("No description, view original article for additional information");
        }
        if (entry.getPublishedDate() != null) {
            article.setArticleDate(entry.getPublishedDate());
        } else {
            article.setArticleDate(Date.from(Instant.now()));
        }
    }
}
