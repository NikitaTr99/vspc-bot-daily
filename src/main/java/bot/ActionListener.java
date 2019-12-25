package bot;

import bot.core.ActionThread;
import bot.vkcore.VKCore;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

import java.util.concurrent.Executors;

public class ActionListener implements Runnable {
    VKCore vk_core;
    ActionListener(VKCore vk_core){
        this.vk_core = vk_core;
    }
    @Override
    public void run() {
        System.out.println("ActionListener is ran.");
        while (true){
            try {
                Thread.sleep(300);
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
            } catch (ClientException e){
                System.out.println("Connection error");
                final int RECONNECT_TIME = 10000;
                System.out.println("Reconnect after " + RECONNECT_TIME / 1000 + " seconds.");
                try {
                    Thread.sleep(RECONNECT_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            } catch (ApiException ignored){

            }
        }
    }
}
