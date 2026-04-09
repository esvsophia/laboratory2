package org.example.parser;

import org.example.model.Curse;
import org.example.model.Mission;
import java.io.*;
import java.util.*;

public class A5MissionParser implements IMissionParser {

    @Override
    public Mission loadMission(String filePath) throws IOException {
        Mission mission = new Mission();
        List<Map<String, Object>> sorcerers = new ArrayList<>();
        List<Map<String, Object>> techniques = new ArrayList<>();
        List<Map<String, Object>> timeline = new ArrayList<>();
        List<String> enemyActions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] p = line.split("\\|");

                switch (p[0]) {
                    case "MISSION_CREATED":
                        mission.setMissionId(p[1]);
                        mission.setDate(p[2]);
                        mission.setLocation(p[3]);
                        break;
                    case "CURSE_DETECTED":
                        mission.setCurse(new Curse(p[1], p[2]));
                        break;
                    case "SORCERER_ASSIGNED":
                        Map<String, Object> sorcerer = new HashMap<>();
                        sorcerer.put("name", p[1]);
                        sorcerer.put("rank", p[2]);
                        sorcerers.add(sorcerer);
                        break;
                    case "TECHNIQUE_USED":
                        Map<String, Object> technique = new HashMap<>();
                        technique.put("name", p[1]);
                        technique.put("type", p[2]);
                        technique.put("owner", p[3]);
                        technique.put("damage", p[4]);
                        techniques.add(technique);
                        break;
                    case "TIMELINE_EVENT":
                        Map<String, Object> event = new HashMap<>();
                        event.put("timestamp", p[1]);
                        event.put("type", p[2]);
                        event.put("description", p[3]);
                        timeline.add(event);
                        break;
                    case "ENEMY_ACTION":
                        enemyActions.add(p[1] + ": " + p[2]);
                        break;
                    case "CIVILIAN_IMPACT":
                        Map<String, Object> civilian = new HashMap<>();
                        for (int i = 1; i < p.length; i++) {
                            String[] kv = p[i].split("=");
                            civilian.put(kv[0], kv[1]);
                        }
                        mission.addField("civilianImpact", civilian);
                        break;
                    case "MISSION_RESULT":
                        mission.setOutcome(p[1]);
                        if (p.length > 2) {
                            String[] kv = p[2].split("=");
                            mission.addField(kv[0], kv[1]);
                        }
                        break;
                }
            }
        }

        if (!sorcerers.isEmpty()) mission.addField("sorcerers", sorcerers);
        if (!techniques.isEmpty()) mission.addField("techniques", techniques);
        if (!timeline.isEmpty()) mission.addField("timeline", timeline);
        if (!enemyActions.isEmpty()) mission.addField("enemyActions", enemyActions);

        return mission;
    }
}
