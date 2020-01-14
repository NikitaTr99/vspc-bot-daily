package bot.core.actions.schedule;

import bot.Bootstrapper;
import bot.core.actions.base.Action;
import bot.core.actions.daily.utils.SubjectManager;
import bot.core.vk.VKManager;
import com.vk.api.sdk.objects.messages.Message;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class TodaySchedule extends Action {
    public TodaySchedule(ArrayList<String> tags){
        super(tags);
    }

    @Override
    public void execute(Message message) {
        new VKManager().sendMessage(buildMessage("Сегодня "
                + LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("ru", "RU")) + ": \n" +
                        new SubjectManager().TodaySchedule()
                ,message.getPeerId()));
        LogToConsole(log(message));
    }
}
