package ua.kpi.fict.multinewscw.services;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;

@Service
public class RssParser<T> implements Parser<T>{
    @Autowired
    URLConnector urlConnector;
    @Override
    public T doParse(String resource) throws IOException, FeedException {
        HttpURLConnection connection = urlConnector.setConnection(resource);
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(connection));
            List entries = feed.getEntries();
            Iterator itEntries = entries.iterator();

            while (itEntries.hasNext()) {
                SyndEntry entry = (SyndEntry) itEntries.next();
                System.out.println("######");
                System.out.println("Title: " + entry.getTitle());
                System.out.println("Link: " + entry.getLink());
                if (entry.getAuthor() != null) {
                    System.out.println("Author: " + entry.getAuthor());
                }

                if (entry.getDescription() != null) {
                    System.out.println("Desc: " + entry.getDescription().getValue());
                }

                System.out.println("Date: " + entry.getPublishedDate());

            }

        } finally {
            urlConnector.endConnection(connection);
        }
        return (T) "A"; //todo
    }
}
