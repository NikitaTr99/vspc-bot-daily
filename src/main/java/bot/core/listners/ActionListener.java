package bot.core.listners;

import bot.core.actions.ActionThread;
import bot.core.interfaces.Loggable;
import bot.core.vk.VKCore;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

import java.util.concurrent.Executors;

public class ActionListener implements Runnable, Loggable {
    VKCore vk_core;
    public ActionListener(VKCore vk_core){
        this.vk_core = vk_core;
    }
    @Override
    public void run() {
        LogToConsole("ActionListener started.");
        while (true){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Message message = vk_core.getMessage();
                if(message != null){
                    Executors
                            .newCachedThreadPool()
                            .execute(new ActionThread(message));
                }
            }
            catch (ClientException e){
                LogToConsole("Connection error");
                final int RECONNECT_TIME = 10000;
                LogToConsole("Reconnect after " + RECONNECT_TIME / 1000 + " seconds.");
                try {
                    Thread.sleep(RECONNECT_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            } catch (ApiException ignored){
                try {
                    vk_core = new VKCore();
                } catch (ClientException | ApiException e) {
                    e.printStackTrace();
                    LogToConsole("Critical error. ActionListener will be completed.");
                    break;
                }
            }
        }
    }
}
