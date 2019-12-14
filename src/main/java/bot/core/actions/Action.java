package bot.core.actions;

import com.vk.api.sdk.objects.messages.Message;

import java.text.SimpleDateFormat;

public abstract class Action {

    public final String name;

    public Action(String name){
        this.name = name;
    }

    public abstract void execute(Message message);
}
