package bot.core.actions;

import com.vk.api.sdk.objects.messages.Message;

public class ActionThread implements Runnable {
    Message message;
    public ActionThread(Message message){
        this.message = message;
    }

    @Override
    public void run() {
        ActionStarter.execute(message);
    }
}
