package org.example.parser;

import org.example.model.Curse;
import org.example.model.Mission;
import java.io.*;
import java.util.*;

public class TxtMissionParser implements IMissionParser {

    @Override
    public Mission loadMission(String filePath) throws IOException {
        Mission mission = new Mission();
        List<Map<String, Object>> sorcerers = new ArrayList<>();
        List<Map<String, Object>> techniques = new ArrayList<>();
        String currentSection = "";
        Map<String, Object> currentBlock = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("[") && line.endsWith("]")) {
                    saveBlock(mission, currentSection, currentBlock, sorcerers, techniques);
                    currentSection = line.substring(1, line.length() - 1);
                    currentBlock = new HashMap<>();
                    continue;
                }

                int sep = line.indexOf('=');
                if (sep == -1) continue;
                String key = line.substring(0, sep).trim();
                String value = line.substring(sep + 1).trim();
                currentBlock.put(key, value);
            }
            saveBlock(mission, currentSection, currentBlock, sorcerers, techniques);
        }

        if (!sorcerers.isEmpty()) mission.addField("sorcerers", sorcerers);
        if (!techniques.isEmpty()) mission.addField("techniques", techniques);

        return mission;
    }

    private void saveBlock(Mission mission, String section, Map<String, Object> block,
                           List<Map<String, Object>> sorcerers,
                           List<Map<String, Object>> techniques) {
        if (block.isEmpty() || section.isEmpty()) return;

        switch (section) {
            case "MISSION":
                if (block.containsKey("missionId")) mission.setMissionId((String) block.get("missionId"));
                if (block.containsKey("date"))      mission.setDate((String) block.get("date"));
                if (block.containsKey("location"))  mission.setLocation((String) block.get("location"));
                if (block.containsKey("outcome"))   mission.setOutcome((String) block.get("outcome"));
                if (block.containsKey("damageCost")) mission.addField("damageCost", block.get("damageCost"));
                break;
            case "CURSE":
                mission.setCurse(new Curse(
                        (String) block.get("name"),
                        (String) block.get("threatLevel")
                ));
                break;
            case "SORCERER":
                sorcerers.add(new HashMap<>(block));
                break;
            case "TECHNIQUE":
                techniques.add(new HashMap<>(block));
                break;
            default:
                mission.addField(section.toLowerCase(), new HashMap<>(block));
                break;
        }
    }
}
