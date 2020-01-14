package bot.core.actions;

import bot.core.actions.admin.ForceUpdateConfig;
import bot.core.actions.base.Action;
import bot.core.actions.base.Help;
import bot.core.actions.base.Start;
import bot.core.actions.daily.Daily;
import bot.core.actions.daily.Subscribe;
import bot.core.actions.daily.Unsubscribe;
import bot.core.actions.schedule.TodaySchedule;
import bot.core.actions.schedule.TomorrowSchedule;

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
        actions.add(new Subscribe(new ArrayList<>(Arrays.asList(
                "Подписаться"
        ))));
        actions.add(new Unsubscribe(new ArrayList<>(Arrays.asList(
                "Отписаться"
        ))));
        actions.add(new ForceUpdateConfig(new ArrayList<>(Arrays.asList(
                "Force", "ForceUpdateConfig"
        ))));
        actions.add(new Help(new ArrayList<>(Arrays.asList(
                "Команды"
        ))));
        actions.add(new TomorrowSchedule(new ArrayList<>(Arrays.asList(
                "Завтра", "З"
        ))));
    }

    public static HashSet<Action> getCommands() {
        return actions;
    }

    public static void addCommands(Action command) {
        actions.add(command);
    }
}
