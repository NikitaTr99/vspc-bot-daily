package bot;

import bot.vkcore.VKCore;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Loader {
    private static VKCore vkCore;
    static {
        try {
            vkCore = new VKCore();
        } catch (ClientException | ApiException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BotWorker.main();
    }

    public static class BotSettings {
        public static Properties properties;
        static {
            properties = new Properties();
            try {
                properties.load(new FileInputStream(new File("/config-bot.properties")));
            }
            catch (IOException ignored) {
                System.out.println("config-bot.properties load from source.");
                try {
                    properties.load(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("config-bot.properties")));
                }
                catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error loading configuration.");
                }
            }
        }
    }
}
