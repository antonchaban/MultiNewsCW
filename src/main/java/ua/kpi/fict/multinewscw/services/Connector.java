package ua.kpi.fict.multinewscw.services;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface Connector {
    default HttpURLConnection setConnection(String url) throws IOException{
        throw new UnsupportedOperationException("Must be implemented if used");
    };
    void endConnection(HttpURLConnection httpURLConnection);

    default HttpURLConnection setTranslateAPIConnection() throws IOException{
        throw new UnsupportedOperationException("Must be implemented if used");
    }
}
