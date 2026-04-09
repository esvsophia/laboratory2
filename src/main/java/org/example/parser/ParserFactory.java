package org.example.parser;

import java.util.HashMap;
import java.util.Map;

public class ParserFactory {
    private static final Map<String, IMissionParser> parsers = new HashMap<>();

    static {
        parsers.put("json", new JsonMissionParser());
        parsers.put("xml", new XmlMissionParser());
        parsers.put("txt", new TxtMissionParser());
        parsers.put("yaml", new YamlMissionParser());
        parsers.put("a5", new A5MissionParser());
    }

    public static IMissionParser getParser(String fileName) {
        if (fileName.contains("Mission A5")) return parsers.get("a5");
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return parsers.getOrDefault(ext, parsers.get("txt"));
    }
}