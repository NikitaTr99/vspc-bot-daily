package bot.vkcore;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

public class VKManager {
    public static VKCore vkCore;
    static {
        try {
            vkCore = new VKCore();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg, int peerId){
        if (msg == null){
            System.out.println("null");
            return;
        }
        try {
            vkCore.getVkApiClient().messages().send(vkCore.getGroupActor()).peerId(peerId).message(msg).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }
}
