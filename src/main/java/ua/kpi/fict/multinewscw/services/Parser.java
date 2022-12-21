package ua.kpi.fict.multinewscw.services;

public interface Parser<T> {
    T doParse(String resource);
}
