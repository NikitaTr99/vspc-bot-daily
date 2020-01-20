package bot;

import bot.core.listners.ActionListener;
import bot.core.listners.ConfigurationsListener;
import bot.core.listners.DailyListener;
import bot.core.vk.VKCore;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
            Executors.newCachedThreadPool().execute(new ConfigurationsListener());
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
        private static Map<String,String> days_of_week_ruENG_map = Map.ofEntries(
                new AbstractMap.SimpleEntry<>("понедельник", "monday"),
                new AbstractMap.SimpleEntry<>("вторник", "tuesday"),
                new AbstractMap.SimpleEntry<>("среда", "wednesday"),
                new AbstractMap.SimpleEntry<>("четверг", "thursday"),
                new AbstractMap.SimpleEntry<>("пятница", "friday"),
                new AbstractMap.SimpleEntry<>("суббота", "saturday"),
                new AbstractMap.SimpleEntry<>("воскресение", "sunday")
        );
        private static ArrayList<String> weekends;

        static {
            try{
                loadConfiguration();
            } catch (IOException e) {
                System.out.println("Error loading configuration. The configuration file was not found.");
                e.printStackTrace();
                System.exit(1);
            }
        }

        private static void loadConfiguration() throws IOException{
            bot_configuration = loadFromFile("bot-config.properties");
            vk_configuration = loadFromFile("vk-config.properties");
            subscribers = loadSubscribers();
            weekends = loadWeekends();
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
        private static ArrayList<String> loadWeekends(){
            String property = bot_configuration.getProperty("weekends");
            if(property.isEmpty()){
                return null;
            }
            ArrayList<String> subscribers = new ArrayList<>();
            for (String s : property.split(";")){
                subscribers.add(new String(s.getBytes(),StandardCharsets.UTF_8));
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

        public static String DayToday(){
            return LocalDateTime
                    .now()
                    .atZone(ZoneId.ofOffset("GMT", ZoneOffset.of(getTimeZone())))
                    .format(DateTimeFormatter.ofPattern("EEEE",Locale.ENGLISH)).toLowerCase();
        }
        public static String DayTomorrow(){
            return LocalDateTime
                    .now()
                    .atZone(ZoneId.ofOffset("GMT", ZoneOffset.of(getTimeZone())))
                    .plusDays(1)
                    .format(DateTimeFormatter.ofPattern("EEEE",Locale.ENGLISH)).toLowerCase();
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
            return vk_configuration.getProperty("access_token");
        }
        public static Integer getVkGroupId(){
            return Integer.parseInt(vk_configuration.getProperty("group_id"));
        }
        public static String getPathToDaily(){
            return bot_configuration.getProperty("path_to_daily");
        }
        public static String getPathToDays(){
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
        public static String getAdmin(){
            return bot_configuration.getProperty("admin");
        }
        public static  Map<String,String> getDaysOfWeek(){
            return days_of_week_ruENG_map;
        }
        public static ArrayList<String> getWeekends() {
            return weekends;
        }
    }
}
