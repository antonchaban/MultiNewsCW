package ua.kpi.fict.multinewscw.services;

import com.sun.syndication.io.FeedException;

import java.io.IOException;
import java.util.ArrayList;

public interface Parser<T> {
    T doParse(String resource) throws IOException, FeedException;
}
