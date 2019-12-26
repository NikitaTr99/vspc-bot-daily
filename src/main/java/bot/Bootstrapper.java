package bot;

import bot.vkcore.VKCore;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executors;

public class Bootstrapper {
    private static VKCore vk_core;

    static {
        try {
            vk_core = new VKCore();
        } catch (ClientException | ApiException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        System.out.println("Bootstrap bot...");
        try{
            Executors.newCachedThreadPool().execute(new ActionListener(vk_core));
            Executors.newCachedThreadPool().execute(new DailyListener(vk_core));
        }
        catch (Exception e){
            System.out.println("Something wrong.");
            e.printStackTrace();
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