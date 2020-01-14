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
            if(TimeNow().equals(Bootstrapper.BotSettings.bot_properties.getProperty("notification_time"))){
                if(Bootstrapper.BotSettings.subscribers != null){
                    for(int id : Bootstrapper.BotSettings.subscribers){
                        new Daily(null).execute(new Message().setPeerId(id));
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
                + Bootstrapper.BotSettings.bot_properties.getProperty("time_zone")
        ));
        return now_time.format(System.currentTimeMillis());
    }
}
