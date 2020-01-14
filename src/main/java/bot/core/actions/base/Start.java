package bot.core.actions.base;

import bot.core.vk.VKManager;
import com.vk.api.sdk.objects.messages.Message;

import java.util.ArrayList;

public class Start extends Action {

    String text = "Привет я чат бот группы 1Дд и" +
            " переодически я буду инофрмировать тебя о нашем расписании.\n" +
            "Что бы получать рассылку отправь \"Подписаться\".\n" +
            "Так же ты можешь узнать своё расписание на сегодня в любое время отправив \"Расписание\"";

    public Start(ArrayList<String> tags) {
        super(tags);
    }

    @Override
    public void execute(Message message) {
        new VKManager().sendMessage(buildMessage(text,message.getPeerId()));
        LogToConsole(log(message));
    }
}
