package bot.core.actions;

import bot.vkcore.VKManager;
import com.vk.api.sdk.objects.messages.Message;

import java.util.ArrayList;

public class Unknown extends Action {
    String text =
            "Неизвестная команда.\n" +
            "Вот список команд которые я знаю: \n" +
                    "1. Расписание.\n" +
                    "2. Сводка.\n" +
                    "3. Подписаться.\n" +
                    "4. Отписаться.";

    public Unknown() {
        super(null);
    }

    @Override
    public void execute(Message message) {
        new VKManager().sendMessage(buildMessage(text,message.getPeerId()));
        LogToConsole(log(message));
    }
}
