package bot;

import bot.vkcore.VKCore;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

import java.util.concurrent.Executors;

public class BotWorker {
    private static VKCore vk_core;
    static {
        try {
            vk_core = new VKCore();
        } catch (ClientException | ApiException e) {
            e.printStackTrace();
        }
    }

    public static void main() throws NullPointerException, InterruptedException {
        System.out.println("Start up bot...");
        while (true){
            Thread.sleep(300);
            try {
                Message message = vk_core.getMessage();
                if(message != null){
                    Executors
                            .newCachedThreadPool()
                            .execute(new ActionThread(message));

                }
            } catch (ClientException e){
                System.out.println("Возникли проблемы");
                final int RECONNECT_TIME = 10000;
                System.out.println("Повторное соединение через " + RECONNECT_TIME / 1000 + " секунд");
                Thread.sleep(RECONNECT_TIME);
            } catch (ApiException ignored){

            }
        }
    }
}
