package ua.kpi.fict.multinewscw.services.implementation;

import org.springframework.stereotype.Service;
import ua.kpi.fict.multinewscw.services.Connector;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class APIConnector implements Connector {

    @Override
    public HttpURLConnection setTranslateAPIConnection() throws IOException {
        URL url = new URL("http://localhost:5000/translate");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        return conn;
    }

    @Override
    public void endConnection(HttpURLConnection httpURLConnection) {
        httpURLConnection.disconnect();
    }
}
