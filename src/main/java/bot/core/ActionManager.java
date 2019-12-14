package bot.core;

import bot.core.actions.Action;
import bot.core.actions.Start;
import bot.core.actions.Unknown;
import bot.core.actions.schedule.TodaySchedule;

import java.util.HashSet;

public class ActionManager {
    private static HashSet<Action> actions = new HashSet<>();

    static {
        actions.add(new Unknown("Unknown"));
        actions.add(new Start("Начать"));
        actions.add(new TodaySchedule("Расписание"));
    }

    public static HashSet<Action> getCommands() {
        return actions;
    }

    public static void addCommands(Action command) {
        actions.add(command);
    }
}
