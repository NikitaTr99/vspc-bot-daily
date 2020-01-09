package bot.core.actions.daily;

import bot.Bootstrapper;
import bot.core.actions.Action;
import bot.vkcore.VKManager;
import com.vk.api.sdk.objects.messages.Message;

import java.util.ArrayList;

public class Unsubscribe extends Action {
    public Unsubscribe(ArrayList<String> word_tags) {
        super(word_tags);
    }

    @Override
    public void execute(Message message) {
        if(Bootstrapper.BotSettings.bot_properties.getProperty("subscribers").contains(message.getPeerId().toString())){
            Bootstrapper.BotSettings.RemoveSubscriber(message.getPeerId());
            new VKManager().sendMessage(buildMessage("Вы отписались от рассылки.",message.getPeerId()));
        }
        else {
            new VKManager().sendMessage(buildMessage("Вы не получаете рассылку.",message.getPeerId()));
        }
        LogToConsole(log(message));
    }
}
