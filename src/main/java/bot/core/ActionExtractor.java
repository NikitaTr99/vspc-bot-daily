package bot.core;

import bot.core.actions.Action;
import bot.core.actions.Unknown;
import com.vk.api.sdk.objects.messages.Message;

import java.util.Collection;

public class ActionExtractor {
    public static Action getCommand(Collection<Action> actions, Message message) {
        String body = message.getBody();
        for(Action action:actions) {
            if(action.name.equals(body.split(" ")[0].toLowerCase()) ||
                    action.name.toLowerCase().charAt(0) == body.split(" ")[0].toLowerCase().charAt(0)){
                return action;
            }
        }
        return new Unknown("Unknown");
    }
}
