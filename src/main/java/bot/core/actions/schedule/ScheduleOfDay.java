package bot.core.actions.schedule;

import bot.Bootstrapper;
import bot.core.actions.base.Action;
import bot.core.actions.daily.utils.SubjectManager;
import bot.core.vk.VKManager;
import com.vk.api.sdk.objects.messages.Message;
import java.util.Map;

public class ScheduleOfDay extends Action {
    private String ofDay;

    public ScheduleOfDay(String day, Message message){
        super(null);
        ofDay = day.toLowerCase();
    }

    @Override
    public void execute(Message message) {
        new VKManager().sendMessage(buildMessage("Расписание на "
                        + getBeautyCase(getKey(Bootstrapper.Configurations.getDaysOfWeek(), ofDay)) + ": \n" +
                        new SubjectManager(this).getSchedule()
                ,message.getPeerId()));
        LogToConsole(log(message));
    }

    private String getKey(Map<String,String> map, String value){
        for(Map.Entry<String,String> entry: map.entrySet()){
            if(entry.getValue().equalsIgnoreCase(value)){
                return entry.getKey();
            }
        }
        return null;
    }

    private String getBeautyCase(String day){
        switch (day){
            case "понедельник":
                return "понедельник";
            case "вторник":
                return "вторник";
            case "среда":
                return "среду";
            case "четвер":
                return "четвер";
            case "пятница":
                return "пятницу";
            case "суббота":
                return "субботу";
            case "воскресенье":
                return "воскресенье";
            default:
                return day;
        }
    }

    public String getOfDay() {
        return ofDay;
    }
}
