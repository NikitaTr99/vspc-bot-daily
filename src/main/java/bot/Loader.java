package bot;

import bot.vkcore.VKCore;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        public static Properties bot_properties;
        public static Properties vk_properties;
        static {
            bot_properties = new Properties();
            vk_properties = new Properties();
            try {
                bot_properties = loadFromFile("config-bot.properties");
            }
            catch (IOException ignored) {
                System.out.println("config-bot.properties load from source.");
                try {
                    bot_properties = loadFromSource("config-bot.properties");
                }
                catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error loading configuration.");
                }
            }
            try {
                vk_properties = loadFromFile("vkconfig-test.properties");
            } catch (IOException ignored) {
                System.out.println("vkconfig-test.properties load from source.");
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
            return p;
        }
        private static Properties loadFromSource(String name) throws IOException {
            Properties p = new Properties();
            p.load(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(name)));
            return p;
        }
    }
}
