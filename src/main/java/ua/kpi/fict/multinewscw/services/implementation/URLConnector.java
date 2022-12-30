package ua.kpi.fict.multinewscw.services.implementation;

import org.springframework.stereotype.Service;
import ua.kpi.fict.multinewscw.services.Connector;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class URLConnector implements Connector {
    @Override
    public HttpURLConnection setConnection(String url) throws IOException {
        URL toConnect = new URL(url);
        return (HttpURLConnection) toConnect.openConnection();
    }

    @Override
    public void endConnection(HttpURLConnection httpURLConnection) {
        httpURLConnection.disconnect();
    }
}
