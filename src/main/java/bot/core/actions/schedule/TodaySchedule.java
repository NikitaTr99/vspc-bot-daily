package bot.core.actions.schedule;

import bot.Bootstrapper;
import bot.core.actions.Action;
import bot.core.actions.daily.utils.SubjectManager;
import bot.vkcore.VKManager;
import com.vk.api.sdk.objects.messages.Message;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class TodaySchedule extends Action {
    Locale localeRu = new Locale("ru", "RU");
    static String path_to_days = "days/";
    static {
        if(!Bootstrapper.BotSettings.bot_properties.getProperty("path_to_days").equals("null")){
            path_to_days = Bootstrapper.BotSettings.bot_properties.getProperty("path_to_days");
        }
    }

    public TodaySchedule(ArrayList<String> tags){
        super(tags);
    }

    @Override
    public void execute(Message message) {
        new VKManager().sendMessage(buildMessage("Сегодня "
                + LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, localeRu) + ": \n" +
                        new SubjectManager().TodaySchedule()
                ,message.getPeerId()));
        LogToConsole(log(message));
    }
}
