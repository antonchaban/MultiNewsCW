package ua.kpi.fict.multinewscw.services.implementation;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.kpi.fict.multinewscw.elasticrepo.ESArticleRepo;
import ua.kpi.fict.multinewscw.entities.enums.Category;
import ua.kpi.fict.multinewscw.services.Parser;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryParser implements Parser<Category> {
    @Value("${keywords.file.path}")
    private String keywordsFilePath;

    private final ESArticleRepo esArticleRepo;


    @Override
    public Category doParse(String content) throws IOException, ParseException {
        Map<String, List<String>> categoryMap = new HashMap<>();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(keywordsFilePath));
        for (Object category : jsonObject.keySet()) {
            String categoryString = (String) category;
            JSONArray keywords = (JSONArray) jsonObject.get(categoryString);
            List<String> keywordsList = keywords.stream().toList();
            categoryMap.put(categoryString, keywordsList);
        }

        for (String category : categoryMap.keySet()) {
            List<String> keywords = categoryMap.get(category);
            for (String keyword : keywords) {
                if (content.toLowerCase().contains(keyword.toLowerCase())) {
                    return Category.valueOf(category.toUpperCase());
                }
            }
        }
        return Category.CATEGORY_OTHER;
    }
}
