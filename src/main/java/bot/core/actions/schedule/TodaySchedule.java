package bot.core.actions.schedule;

import bot.core.actions.Action;
import bot.vkcore.VKManager;
import com.vk.api.sdk.objects.messages.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

public class TodaySchedule extends Action {
    Locale localeRu = new Locale("ru", "RU");

    public TodaySchedule(String name){
        super(name);
    }

    @Override
    public void execute(Message message) {
        new VKManager().sendMessage(GetTodaySchendle(),message.getUserId());
    }


    private String GetTodaySchendle(){
        return "Сегодня " +
                LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, localeRu) + ": \n" +
                GetBuildedSubjects(Objects.requireNonNull(
                        ClassLoader.getSystemResourceAsStream(
                                "days/" + LocalDate.now().getDayOfWeek().toString().toLowerCase() + ".txt"
                        )));
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
