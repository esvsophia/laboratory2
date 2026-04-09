package org.example.report;

import org.example.model.Mission;
import java.util.List;
import java.util.Map;

public class DetailedReporter implements IMissionReporter {

    @Override
    public String format(Mission m) {
        String text = "";
        text += "ОТЧЕТ О МИССИИ: " + m.getMissionId() + "\n";
        text += "Дата: " + m.getDate() + "\n";
        text += "Локация: " + m.getLocation() + "\n";
        text += "Результат: " + m.getOutcome() + "\n";
        if (m.getCurse() != null) {
            text += "Цель: " + m.getCurse() + "\n";
        }

        Map<String, Object> extra = m.getExtraFields();
        if (extra == null || extra.isEmpty()) return text;

        for (String key : extra.keySet()) {
            Object value = extra.get(key);
            if (value instanceof Map) {
                text += "\n[" + key + "]\n";
                Map<String, Object> map = (Map<String, Object>) value;
                for (String subKey : map.keySet()) {
                    text += "  " + subKey + ": " + map.get(subKey) + "\n";
                }
            } else if (value instanceof List) {
                text += "\n[" + key + "]\n";
                List<Object> list = (List<Object>) value;
                for (Object item : list) {
                    if (item instanceof Map) {
                        Map<String, Object> itemMap = (Map<String, Object>) item;
                        for (String subKey : itemMap.keySet()) {
                            text += "  " + subKey + ": " + itemMap.get(subKey) + "\n";
                        }
                        text += "\n";
                    } else {
                        text += "  " + item + "\n";
                    }
                }
            } else {
                text += key + ": " + value + "\n";
            }
        }
        return text;
    }
}
