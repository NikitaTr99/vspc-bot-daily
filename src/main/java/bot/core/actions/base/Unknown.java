package bot.core.actions.base;

import bot.core.vk.VKManager;
import com.vk.api.sdk.objects.messages.Message;

public class Unknown extends Action {
    String text =
            "Неизвестная команда. Что бы получить список команд - \"Команды\"\n";

    public Unknown() {
        super(null);
    }

    @Override
    public void execute(Message message) {
        new VKManager().sendMessage(buildMessage(text,message.getPeerId()));
        LogToConsole(log(message));
    }
}
