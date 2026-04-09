package org.example.parser;

import org.example.model.Mission;
import java.io.IOException;

public interface IMissionParser {
    Mission loadMission(String filePath) throws IOException;
}
