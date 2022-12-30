package ua.kpi.fict.multinewscw.services;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Service
public class TranslateAPIParser implements Parser<String> {

    @Autowired
    APIConnector apiConnector;

    @Override
    public String doParse(String textToTranslate) throws IOException, ParseException {
        HttpURLConnection conn = apiConnector.setTranslateAPIConnection();
        try {
            String jsonInputString = "{\"q\":" + "\"" + textToTranslate + "\""
                    + ", \"source\": \"uk\"," +
                    " \"target\": \"en\", " +
                    "\"format\": \"text\"}";
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();
            JSONParser parser = new JSONParser();
            JSONObject data_obj = (JSONObject) parser.parse(inline.toString());
            return (String) data_obj.get("translatedText");
        } finally {
            apiConnector.endConnection(conn);
        }
    }
}

