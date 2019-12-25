package bot.core;

import bot.core.actions.Action;
import bot.core.actions.Unknown;
import com.vk.api.sdk.objects.messages.Message;

import java.util.Collection;

public class ActionExtractor {

    public static Action getCommandTag(Collection<Action> actions, Message message) {
        String body = message.getText();
        for(Action action:actions) {
            for(String s : action.word_tags){
                if (s.equalsIgnoreCase(body.split(" ")[0])){
                    return action;
                }
            }
        }
        return new Unknown();
    }
}
