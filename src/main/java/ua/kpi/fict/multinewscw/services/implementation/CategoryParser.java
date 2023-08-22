package ua.kpi.fict.multinewscw.services.implementation;

import com.sun.syndication.io.FeedException;
import org.json.simple.parser.ParseException;
import ua.kpi.fict.multinewscw.entities.enums.Category;
import ua.kpi.fict.multinewscw.services.Parser;

import java.io.IOException;

public class CategoryParser implements Parser<Category> {

    @Override // todo implement
    public Category doParse(String content) throws IOException, FeedException, ParseException {
        return null;
    }
}
