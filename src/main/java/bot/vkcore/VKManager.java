package bot.vkcore;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

public class VKManager {
    public static VKCore vkCore;
    static {
        try {
            vkCore = new VKCore();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        try {
            vkCore.getVkApiClient()
                    .messages()
                    .send(vkCore.getGroupActor())
                    .message(message.getText())
                    .peerId(message.getPeerId())
                    .randomId(message.getRandomId())
                    .execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }
}
