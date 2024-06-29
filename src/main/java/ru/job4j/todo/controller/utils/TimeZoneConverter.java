package ru.job4j.todo.controller.utils;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.ZoneId;
import java.util.*;

public class TimeZoneConverter {
    public static Collection<Task> convert(Collection<Task> tasks, User user) {
        Collection<Task> data = new ArrayList<>();
        for (Task t : tasks) {
            var userTimeZoneId = user.getTimezone();
            if (userTimeZoneId == null) {
                System.out.println(TimeZone.getDefault().getID());
                userTimeZoneId = TimeZone.getDefault().getID();
            }
            t.setCreated(
                    t.getCreated()
                            .atZone(ZoneId.of("UTC"))
                            .withZoneSameInstant(
                                    ZoneId.of(userTimeZoneId))
                            .toLocalDateTime());
            data.add(t);
        }
        return data;
    }
}
