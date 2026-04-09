package org.example.output;

import org.example.model.*;

public class SummBase implements Summary {
    private Mission mission;

    public SummBase(Mission mission) {
        this.mission = mission;
    }

    @Override
    public String getSummary() {
        String text = "";
        text += "ID миссии: " + mission.getMissionId() + "\n";
        text += "Дата: " + mission.getDate() + "\n";
        text += "Локация: " + mission.getLocation() + "\n";
        text += "Результат: " + mission.getOutcome() + "\n";
        Curse cur = mission.getCurse();
        text += "Проклятие: " + cur.getName() + "\n";
        text += "Уровень угрозы: " + cur.getThreatLevel() + "\n";

        return text;
    }
}
