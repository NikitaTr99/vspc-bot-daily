package bot.core.actions.schedule;

import bot.core.actions.base.Action;
import bot.core.actions.daily.utils.SubjectManager;
import bot.core.vk.VKManager;
import com.vk.api.sdk.objects.messages.Message;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class TomorrowSchedule extends Action {
    public TomorrowSchedule(ArrayList<String> word_tags) {
        super(word_tags);
    }

    @Override
    public void execute(Message message) {
        new VKManager().sendMessage(buildMessage("Завтра "
                        + LocalDate.now().getDayOfWeek().plus(1).getDisplayName(TextStyle.FULL, new Locale("ru", "RU")) + ": \n" +
                        new SubjectManager(this).getSchedule()
                ,message.getPeerId()));
        LogToConsole(log(message));
    }
}
