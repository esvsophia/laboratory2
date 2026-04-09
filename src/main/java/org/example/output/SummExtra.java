package org.example.output;

import org.example.model.Mission;
import java.util.List;
import java.util.Map;

public class SummExtra implements Summary {
    private Summary wrapped;
    private Mission mission;

    public SummExtra(Summary wrapped, Mission mission) {
        this.wrapped = wrapped;
        this.mission = mission;
    }

    @Override
    public String getSummary() {
        String text = wrapped.getSummary();
        Map<String, Object> extra = mission.getExtraFields();
        if (extra == null || extra.isEmpty()) return text;

        text += "\nДополнительная информация:\n";
        for (String key : extra.keySet()) {
            Object value = extra.get(key);
            text += "\n" + key + ":\n";
            text += formatValue(value, "  ");
        }
        return text;
    }

    private String formatValue(Object value, String indent) {
        String text = "";
        if (value instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) value;
            for (String key : map.keySet()) {
                Object val = map.get(key);
                if (val instanceof List) {
                    text += indent + key + ":\n";
                    for (Object item : (List<Object>) val) {
                        text += formatValue(item, indent + "  ");
                    }
                } else if (val instanceof Map) {
                    text += indent + key + ":\n";
                    text += formatValue(val, indent + "  ");
                } else {
                    text += indent + key + ": " + val + "\n";
                }
            }
        } else if (value instanceof List) {
            for (Object item : (List<Object>) value) {
                if (item instanceof Map) {
                    text += formatValue(item, indent);
                    text += "\n";
                } else {
                    text += indent + item + "\n";
                }
            }
        } else {
            text += indent + value + "\n";
        }
        return text;
    }
}
