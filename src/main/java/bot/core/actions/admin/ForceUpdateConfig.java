package bot.core.actions.admin;

import bot.Bootstrapper;
import bot.core.actions.base.Action;
import bot.core.vk.VKManager;
import com.vk.api.sdk.objects.messages.Message;

import java.util.ArrayList;

public class ForceUpdateConfig extends Action {

    public ForceUpdateConfig(ArrayList<String> word_tags) {
        super(word_tags);
    }

    @Override
    public void execute(Message message) {
        if(message.getPeerId().toString().equals(Bootstrapper.Configurations.getAdmin())){
            new VKManager().sendMessage(buildMessage("Force update configuration function is start.",message.getPeerId()));
            Bootstrapper.Configurations.updateConfiguration();

        }
        else{
            new VKManager().sendMessage(buildMessage("You don't have access to this action.",message.getPeerId()));
        }
        LogToConsole(log(message));
    }

}
