package ua.kpi.fict.multinewscw;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TestTranslate {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:5000/translate");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            String jsonInputString = "{\"q\": \"Привіт світ\", \"source\": \"uk\", \"target\": \"en\", \"format\": \"text\"}";
            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            /*try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }*/


            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
//
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
//
            scanner.close();
            System.out.println(inline.toString());
//
            JSONParser parse = new JSONParser();
            JSONObject data_obj = (JSONObject) parse.parse(inline.toString());
//
            String translatedText = (String) data_obj.get("translatedText");
            System.out.println(translatedText);
//
//            JSONObject obj = (JSONObject) data_obj.get("data");
//
//
//            JSONArray arr = (JSONArray) obj.get("scheduleFirstWeek");

            conn.disconnect();



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
