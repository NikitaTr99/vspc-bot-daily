package bot.core.actions;

import bot.vkcore.VKManager;
import com.vk.api.sdk.objects.messages.Message;

public class Unknown extends Action {
    String text = "Я не знаю такой команды.\n" +
            "Что бы получить расписание на сегодня отправь" +
            " \"расписание\" или " +
            "сокращённо \"р\".";

    public Unknown(String name) {
        super(name);
    }

    @Override
    public void execute(Message message) {
        new VKManager().sendMessage(buildMessage(text,message.getPeerId()));
        LogToConsole(log(message));
    }
}
