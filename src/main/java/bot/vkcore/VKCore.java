package bot.vkcore;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class VKCore {
    private VkApiClient vkApiClient;
    private static int ts;
    private GroupActor groupActor;
    private static int maxMsgId = -1;

    public VKCore() throws ClientException, ApiException {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vkApiClient = new VkApiClient(transportClient);
        Properties prop = new Properties();
        int group_id;
        String access_token;
        try {
            prop.load(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("vkconfig-test.properties")));
            group_id = Integer.parseInt(prop.getProperty("group_id"));
            access_token = prop.getProperty("access_token");
            groupActor = new GroupActor(group_id,access_token);
            ts = vkApiClient.messages().getLongPollServer(groupActor).execute().getTs();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Ошибка при загрузке конфигурации");
        }
    }

    public Message getMessage() throws ClientException, ApiException {
        MessagesGetLongPollHistoryQuery events_query = vkApiClient.messages()
                .getLongPollHistory(groupActor)
                .ts(ts);
        if(maxMsgId > 0)
            events_query.maxMsgId(maxMsgId);
        List<Message> messages = events_query
                .execute()
                .getMessages()
                .getMessages();
        if(!messages.isEmpty()) {
            try{
                ts = vkApiClient
                        .messages()
                        .getLongPollServer(groupActor)
                        .execute()
                        .getTs();
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        if(!messages.isEmpty() && !messages.get(0).isOut()){
            int message_id = messages.get(0).getId();
            if(message_id > maxMsgId) {
                maxMsgId = message_id;
            }
            return messages.get(0);
        }
        return null;
    }

    public VkApiClient getVkApiClient() {
        return vkApiClient;
    }

    public GroupActor getGroupActor() {
        return groupActor;
    }
}
