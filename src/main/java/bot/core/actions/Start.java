package bot.core.actions;

import bot.vkcore.VKManager;
import com.vk.api.sdk.objects.messages.Message;

public class Start extends Action {

    String answer = "Привет я чат бот группы 1Дд." +
            " Переодически я буду инофрмировать тебя.(но это не точно). \n" +
            "Вот список команд которые я знаю: \n" +
            "1. \"Расписание\" или сокращённо \"р\"";


    public Start(String name) {
        super(name);
    }

    @Override
    public void execute(Message message) {
        new VKManager().sendMessage(answer,message.getUserId());
    }
}
