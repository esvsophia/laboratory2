package org.example.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.HashMap;
import java.util.Map;

public class Mission {
    private String missionId;
    private String date;
    private String location;
    private String outcome;
    private Curse curse;
    private Map<String, Object> extraFields = new HashMap<>();

    public String getMissionId() { return missionId; }
    public String getDate() { return date; }
    public String getLocation() { return location; }
    public String getOutcome() { return outcome; }
    public Curse getCurse() { return curse; }

    public void setMissionId(String missionId) { this.missionId = missionId; }
    public void setDate(String date) { this.date = date; }
    public void setLocation(String location) { this.location = location; }
    public void setOutcome(String outcome) { this.outcome = outcome; }
    public void setCurse(Curse curse) { this.curse = curse; }

    @JsonAnyGetter
    public Map<String, Object> getExtraFields() { return extraFields; }

    @JsonAnySetter
    public void addField(String name, Object value) { extraFields.put(name, value); }
}
