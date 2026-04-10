package org.example.parser;

import java.util.HashMap;
import java.util.Map;

public class ParserFactory {
    private static Map<String, IMissionParser> parsers = Map.of(
            "json", new JsonMissionParser(),
            "xml",  new XmlMissionParser(),
            "txt",  new TxtMissionParser(),
            "yaml", new YamlMissionParser(),
            "a5",   new A5MissionParser()
    );

    public static IMissionParser getParser(String fileName) {
        if (fileName.contains("Mission A5")) return parsers.get("a5");
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return parsers.get(ext);
    }
}
