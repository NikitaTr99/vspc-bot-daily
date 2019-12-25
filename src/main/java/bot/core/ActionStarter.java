package bot.core;

import com.vk.api.sdk.objects.messages.Message;

public class ActionStarter {
    public static void execute(Message message) {
        ActionExtractor.getCommandTag(ActionManager.getCommands(),message).execute(message);
    }
}
