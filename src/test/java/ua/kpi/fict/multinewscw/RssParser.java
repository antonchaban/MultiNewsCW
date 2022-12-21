package ua.kpi.fict.multinewscw;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class RssParser {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://moxie.foxnews.com/google-publisher/world.xml");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(httpURLConnection));
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

        } catch (IOException | FeedException e) {
            throw new RuntimeException(e);
        } finally {
            httpURLConnection.disconnect();
        }

    }
}
