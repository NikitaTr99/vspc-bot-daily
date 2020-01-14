package bot.core.actions.base;

import bot.core.vk.VKManager;
import com.vk.api.sdk.objects.messages.Message;

import java.util.ArrayList;

public class Help extends Action {
    String text =  "Вот список команд которые я знаю: \n" +
            "1. \"Расписание\".\n" +
            "2. \"Завтра\"." +
            "3. \"Сводка\".\n" +
            "4. \"Подписаться\".\n" +
            "5. \"Отписаться\".\n" +
            "6. \"Команды\".";

    public Help(ArrayList<String> word_tags) {
        super(word_tags);
    }

    @Override
    public void execute(Message message) {
        new VKManager().sendMessage(buildMessage(text,message.getPeerId()));
        LogToConsole(log(message));
    }
}
