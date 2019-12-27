package bot.core;

import bot.core.actions.Action;
import bot.core.actions.Start;
import bot.core.actions.Unknown;
import bot.core.actions.daily.Daily;
import bot.core.actions.schedule.TodaySchedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ActionManager {
    private static HashSet<Action> actions = new HashSet<>();

    static {
        actions.add(new Start(new ArrayList<>(Arrays.asList(
                "Начать", "Н"
        ))));
        actions.add(new TodaySchedule(new ArrayList<>(Arrays.asList(
                "Расписание", "Р"
        ))));
        actions.add(new Daily(new ArrayList<>(Arrays.asList(
                "Сводка", "С"
        ))));
    }

    public static HashSet<Action> getCommands() {
        return actions;
    }

    public static void addCommands(Action command) {
        actions.add(command);
    }
}
