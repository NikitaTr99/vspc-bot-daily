package bot;

import bot.vkcore.VKCore;
import bot.vkcore.VKManager;
import com.vk.api.sdk.actions.Groups;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DailyListener implements Runnable {
    private VKCore vk_core;
    DailyListener(VKCore vk_core){
        this.vk_core = vk_core;
    }
    @Override
    public void run() {
        System.out.println("DailyListener is ran.");
        List<Integer> g = new ArrayList<>();
        try {
            g = new Groups(vk_core.getVkApiClient())
                    .getMembers(vk_core.getGroupActor())
                    .groupId(String.valueOf(vk_core.getGroupActor().getGroupId()))
                    .execute()
                    .getItems();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        while (true){
            String now_time = new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis());
            if(now_time.equals(Bootstrapper.BotSettings.bot_properties.getProperty("notification_time"))){
                System.out.println(vk_core.getGroupActor());
                    System.out.println(g.toString());
                    for(int id : g){
                        new VKManager().sendMessage(
                                new Message()
                                        .setPeerId(id)
                                        .setRandomId((int) System.currentTimeMillis())
                                        .setText("You daily: \n"));
                    }
                try {
                    Thread.sleep(82800000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
