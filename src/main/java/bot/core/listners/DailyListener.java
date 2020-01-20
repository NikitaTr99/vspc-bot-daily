package bot.core.listners;

import bot.Bootstrapper;
import bot.core.actions.daily.Daily;
import bot.core.interfaces.Loggable;
import com.vk.api.sdk.objects.messages.Message;

import java.text.SimpleDateFormat;

public class DailyListener implements Runnable, Loggable {
    @Override
    public void run() {
        LogToConsole("Daily is running.");
        while (true){
            if(TimeNow().equals(Bootstrapper.Configurations.getNotificationTime())){
                if(Bootstrapper.Configurations.getSubscribers() != null){
                    if (!Bootstrapper.Configurations.getWeekends().contains(Bootstrapper.Configurations.DayToday().toLowerCase())){
                        for(int id : Bootstrapper.Configurations.getSubscribers()){
                            new Daily(null).execute(new Message().setPeerId(id));
                        }
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    protected String TimeNow(){
        SimpleDateFormat now_time = new SimpleDateFormat("HH:mm:ss");
        now_time.setTimeZone(java.util.TimeZone.getTimeZone("GMT"
                + Bootstrapper.Configurations.getTimeZone()
        ));
        return now_time.format(System.currentTimeMillis());
    }
}
