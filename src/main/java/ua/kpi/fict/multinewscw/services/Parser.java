package ua.kpi.fict.multinewscw.services;

import com.sun.syndication.io.FeedException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public interface Parser<T> {
    T doParse(String content) throws IOException, FeedException, ParseException;
}
