package ru.job4j.todo.controller.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Data
@Component
public class TimeZoneLoader {
    private Map<Integer, String> timeZoneIds;

    public TimeZoneLoader() {
        this.timeZoneIds = new HashMap<>();
        init();
    }

    private void init() {
        int id = 1;
        for (String timeId : TimeZone.getAvailableIDs()) {
            var zone = TimeZone.getTimeZone(timeId);
            timeZoneIds.put(id, zone.getID());
            id++;
        }
    }
}
