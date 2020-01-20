package bot.core.actions;

import bot.Bootstrapper;
import bot.core.actions.base.Action;
import bot.core.actions.base.Unknown;
import bot.core.actions.schedule.ScheduleOfDay;
import com.vk.api.sdk.objects.messages.Message;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ActionExtractor {

    public static Action getCommandTag(Collection<Action> actions, Message message) {
        String body = message.getText();
        for(Action action:actions) {
                if(containsAllIgnoreCase(action.word_tags,Arrays.asList(body.split(" ")))){
                    return action;
                }
        }
        if(Bootstrapper.Configurations.getDaysOfWeek().containsKey(body.split(" ")[0].toLowerCase()))
        {
            return new ScheduleOfDay(Bootstrapper.Configurations.getDaysOfWeek().get(body.split(" ")[0].toLowerCase()),message);
        }
        return new Unknown();
    }
    private static boolean containsAllIgnoreCase(List<String> l, List<String> s){
        for(String key_word: l){
            for (String input_word: s){
                if(key_word.equalsIgnoreCase(input_word)) {
                    return true;
                }
            }
        }
        return false;
    }
}
