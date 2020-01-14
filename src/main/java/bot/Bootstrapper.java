package bot;

import bot.core.listners.ActionListener;
import bot.core.listners.DailyListener;
import bot.core.vk.VKCore;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
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
            Executors.newCachedThreadPool().execute(new DailyListener());
        }
        catch (Exception e){
            System.out.println("Something wrong.");
            e.printStackTrace();
        }
    }


    public static class Configurations {
        private static Properties bot_configuration;
        private static Properties vk_configuration;
        private static ArrayList<Integer> subscribers;

        static {
            try{
                loadConfiguration();
            } catch (IOException e) {
                System.out.println("Error loading configuration. The configuration file was not found.");
                System.exit(1);
            }
        }

        private static void loadConfiguration() throws IOException{
            bot_configuration = loadFromFile("bot-config.properties");
            vk_configuration = loadFromFile("vk-config.properties");
            subscribers = loadSubscribers();
        }
        public static void updateConfiguration(){
            try{
                loadConfiguration();
            } catch (Exception e) {
                System.out.println("Error updating configuration.");
                e.printStackTrace();
            }
        }

        private static ArrayList<Integer> loadSubscribers(){
            String property = bot_configuration.getProperty("subscribers");
            ArrayList<Integer> subscribers = new ArrayList<>();
            if(property.isEmpty()){
                return null;
            }
            for (String s : property.split(";")){
                subscribers.add(Integer.parseInt(s));
            }
            return subscribers;
        }
        private static Properties loadFromFile(String name){
            Properties properties = new Properties();
            try{
                properties.load(new FileInputStream(new File(name)));
            } catch (IOException e) {
                System.out.println("Error loading configuration from file.");
                e.printStackTrace();
            }
            return properties;
        }
        private static String DayToday(){
            SimpleDateFormat now_time = new SimpleDateFormat("EEEE", Locale.ENGLISH);
            now_time.setTimeZone(java.util.TimeZone.getTimeZone("GMT"
                    + bot_configuration.getProperty("time_zone")
            ));
            return now_time.format(System.currentTimeMillis());
        }

        public static void addSubscriber(Integer id){
            String property = bot_configuration.getProperty("subscribers");
            property += id + ";";
            bot_configuration.setProperty("subscribers", property);
            try {
                bot_configuration.store(new FileOutputStream("bot-config.properties"),"Added subscriber");
                loadConfiguration();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error updating configuration.");
            }
        }
        public static void removeSubscriber(Integer id){
            String property = bot_configuration.getProperty("subscribers");
            property = property.replaceAll(id + ";","");
            bot_configuration.setProperty("subscribers", property);
            try {
                bot_configuration.store(new FileOutputStream("bot-config.properties"),"Removed subscriber");
                loadConfiguration();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error updating configuration.");
            }
        }

        public static String getVkAccessToken(){
            updateConfiguration();
            return vk_configuration.getProperty("access_token");
        }
        public static Integer getVkGroupId(){
            updateConfiguration();
            return Integer.parseInt(vk_configuration.getProperty("group_id"));
        }
        public static String getPathToDaily(){
            updateConfiguration();
            return bot_configuration.getProperty("path_to_daily");
        }
        public static String getPathToDays(){
            updateConfiguration();
            return bot_configuration.getProperty("path_to_days");
        }
        public static String getNotificationTime(){
            return bot_configuration.getProperty("notification_time");
        }
        public static String getTimeZone(){
            return bot_configuration.getProperty("time_zone");
        }
        public static ArrayList<Integer> getSubscribers(){
            return subscribers;
        }
        public static String getDayOfWeek(){
            updateConfiguration();
            return DayToday();
        }
        public static String getAdmin(){
            return bot_configuration.getProperty("admin");
        }
    }
}
