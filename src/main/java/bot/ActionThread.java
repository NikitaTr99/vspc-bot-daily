package bot;

import bot.core.ActionStarter;
import com.vk.api.sdk.objects.messages.Message;

public class ActionThread implements Runnable {
    Message message;
    ActionThread(Message message){
        this.message = message;
    }

    @Override
    public void run() {
        ActionStarter.execute(message);
    }
}
