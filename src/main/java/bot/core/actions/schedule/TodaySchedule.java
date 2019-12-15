package bot.core.actions.schedule;

import bot.core.actions.Action;
import bot.vkcore.VKManager;
import com.vk.api.sdk.objects.messages.Message;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

public class TodaySchedule extends Action {
    Locale localeRu = new Locale("ru", "RU");
    static Properties properties = new Properties();
    static String path_to_days = "days/";
    static {
        try {
            properties.load(Objects.requireNonNull(
                    ClassLoader.getSystemResourceAsStream("config-bot.properties")));
        } catch (IOException ignored) {

        }
        if(!properties.getProperty("path_to_days").equals("null")){
            path_to_days = properties.getProperty("path_to_days");
        }
    }

    public TodaySchedule(String name){
        super(name);
    }

    @Override
    public void execute(Message message) {
        new VKManager().sendMessage(GetTodaySchedule(),message.getUserId());
        LogToConsole(log(message));
    }


    private String GetTodaySchedule(){
        return properties.getProperty("path_to_days").equals("null")? TodayScheduleSource() :TodaySchendlePath();
    }

    private String TodayScheduleSource(){
        return "Сегодня " +
                LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, localeRu) + ": \n" +
                GetBuildedSubjects(Objects.requireNonNull(
                        ClassLoader.getSystemResourceAsStream(
                                path_to_days + LocalDate.now().getDayOfWeek().toString().toLowerCase() + ".txt"
                        )));
    }

    private String TodaySchendlePath() {
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
