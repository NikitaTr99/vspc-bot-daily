package bot.core.actions.schedule;

import bot.Bootstrapper;
import bot.core.actions.Action;
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
        new VKManager().sendMessage(buildMessage(GetTodaySchedule(),message.getPeerId()));
        LogToConsole(log(message));
    }


    private String GetTodaySchedule(){
        return Bootstrapper.BotSettings.bot_properties
                .getProperty("path_to_days").equals("null")? TodayScheduleSource() : TodaySchedulePath();
    }

    private String TodayScheduleSource(){
        return "Сегодня " +
                LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, localeRu) + ": \n" +
                GetBuildedSubjects(Objects.requireNonNull(
                        ClassLoader.getSystemResourceAsStream(
                                path_to_days + LocalDate.now().getDayOfWeek().toString().toLowerCase() + ".txt"
                        )));
    }

    private String TodaySchedulePath() {
        try {
            return "Сегодня " +
                    LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, localeRu) + ": \n" +
                    GetBuildedSubjects(new FileInputStream(new File(path_to_days
                            + "/"
                            + LocalDate.now().getDayOfWeek().toString().toLowerCase()
                            + ".txt"
                    )));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String GetBuildedSubjects(InputStream inputStream){

        String newLine = System.getProperty("line.separator");
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder result = new StringBuilder();
            String line; int counter = 1;
            while ((line = reader.readLine()) != null) {
                result.append(counter)
                        .append(". ").append(line)
                        .append(newLine);
                counter++;
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
