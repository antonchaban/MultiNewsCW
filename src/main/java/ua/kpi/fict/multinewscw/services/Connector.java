package ua.kpi.fict.multinewscw.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public interface Connector {
    HttpURLConnection setConnection(String url) throws IOException;
    void endConnection(HttpURLConnection httpURLConnection);
}
