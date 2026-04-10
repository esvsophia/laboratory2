package org.example.parser;

import org.example.model.Curse;
import org.example.model.Mission;
import java.io.*;
import java.util.*;

public class YamlMissionParser implements IMissionParser {

    @Override
    public Mission loadMission(String filePath) throws IOException {
        Mission mission = new Mission();
        List<Map<String, Object>> currentList = null;
        Map<String, Object> currentListItem = null;
        Map<String, Object> currentBlock = null;
        String currentBlockName = "";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                int indent = countIndent(line);
                String trimmed = line.trim();

                if (indent == 0) {
                    if (currentListItem != null && currentList != null) {
                        currentList.add(new HashMap<>(currentListItem));
                        currentListItem = null;
                    }
                    if (currentList != null) {
                        mission.addField(currentBlockName, new ArrayList<>(currentList));
                        currentList = null;
                    }
                    if (currentBlock != null) {
                        saveBlock(mission, currentBlockName, currentBlock);
                        currentBlock = null;
                    }

                    int sep = trimmed.indexOf(':');
                    if (sep == -1) continue;
                    String key = trimmed.substring(0, sep).trim();
                    String value = trimmed.substring(sep + 1).trim();

                    if (value.isEmpty()) {
                        currentBlockName = key;
                    } else {
                        setBaseField(mission, key, value);
                    }

                } else if (indent == 2) {
                    if (trimmed.startsWith("- ")) {
                        if (currentListItem != null && currentList != null) {
                            currentList.add(new HashMap<>(currentListItem));
                        }
                        if (currentList == null) {
                            currentList = new ArrayList<>();
                        }
                        currentListItem = new HashMap<>();
                        currentBlock = null;

                        String rest = trimmed.substring(2).trim();
                        if (rest.contains(":")) {
                            int sep = rest.indexOf(':');
                            String key = rest.substring(0, sep).trim();
                            String value = rest.substring(sep + 1).trim();
                            currentListItem.put(key, value);
                        }
                    } else {
                        if (currentBlock == null) {
                            currentBlock = new HashMap<>();
                        }
                        int sep = trimmed.indexOf(':');
                        if (sep == -1) continue;
                        String key = trimmed.substring(0, sep).trim();
                        String value = trimmed.substring(sep + 1).trim();
                        currentBlock.put(key, value);
                    }

                } else if (indent == 4) {
                    if (currentListItem != null) {
                        int sep = trimmed.indexOf(':');
                        if (sep == -1) continue;
                        String key = trimmed.substring(0, sep).trim();
                        String value = trimmed.substring(sep + 1).trim();
                        currentListItem.put(key, value);
                    }
                }
            }

            if (currentListItem != null && currentList != null) {
                currentList.add(new HashMap<>(currentListItem));
            }
            if (currentList != null) {
                mission.addField(currentBlockName, currentList);
            }
            if (currentBlock != null) {
                saveBlock(mission, currentBlockName, currentBlock);
            }
        }

        return mission;
    }

    private void setBaseField(Mission mission, String key, String value) {
        switch (key) {
            case "missionId": mission.setMissionId(value); break;
            case "date":      mission.setDate(value); break;
            case "location":  mission.setLocation(value); break;
            case "outcome":   mission.setOutcome(value); break;
            default:          mission.addField(key, value); break;
        }
    }

    private void saveBlock(Mission mission, String name, Map<String, Object> block) {
        if (name.equals("curse")) {
            mission.setCurse(new Curse(
                    (String) block.getOrDefault("name", ""),
                    (String) block.getOrDefault("threatLevel", "")
            ));
        } else {
            mission.addField(name, new HashMap<>(block));
        }
    }

    private int countIndent(String line) {
        int count = 0;
        for (char c : line.toCharArray()) {
            if (c == ' ') count++;
            else break;
        }
        return count;
    }
}
