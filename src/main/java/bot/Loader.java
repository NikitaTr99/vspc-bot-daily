package bot;

import bot.vkcore.VKCore;
import com.vk.api.sdk.actions.Groups;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executors;

public class Loader {
    private static VKCore vk_core;

    static {
        try {
            vk_core = new VKCore();
        } catch (ClientException | ApiException e) {
            e.printStackTrace();
        }
    }

    private static void BotMode_SCH() throws InterruptedException {
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
                System.out.println("Connection error");
                final int RECONNECT_TIME = 10000;
                System.out.println("Reconnect after " + RECONNECT_TIME / 1000 + " seconds.");
                Thread.sleep(RECONNECT_TIME);
            } catch (ApiException ignored){

            }
        }
    }

    private static void BotMode_DAILY(){
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
            if(now_time.equals(BotSettings.bot_properties.getProperty("notification_time"))){
                System.out.println(vk_core.getGroupActor());
                for(Integer id:g){
                    System.out.println(id.toString());
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start up bot...");
        switch (BotSettings.bot_properties.getProperty("mode")){
            case "sch":
                BotMode_SCH();
                break;
            case "daily":
                BotMode_DAILY();
                break;
        }
    }

    public static class BotSettings {
        public static Properties bot_properties;
        public static Properties vk_properties;
        static {
            System.out.println("Load configuration...");
            bot_properties = new Properties();
            vk_properties = new Properties();
            try {
                bot_properties = loadFromFile("config-bot.properties");
            }
            catch (IOException ignored) {
                try {
                    bot_properties = loadFromSource("config-bot.properties");
                }
                catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error loading bot configuration.");
                }
            }
            try {
                vk_properties = loadFromFile("vkconfig-test.properties");
            } catch (IOException ignored) {
                try {
                    vk_properties = loadFromSource("vkconfig-test.properties");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error loading VK configuration.");
                }
            }
        }
        private static Properties loadFromFile(String name) throws IOException {
            Properties p = new Properties();
            p.load(new FileInputStream(new File(name)));
            System.out.println(name + " load from file: " + new File(name).getAbsolutePath());
            return p;
        }
        private static Properties loadFromSource(String name) throws IOException {
            Properties p = new Properties();
            p.load(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(name)));
            System.out.println(name + " load from source.");
            return p;
        }
    }
}
