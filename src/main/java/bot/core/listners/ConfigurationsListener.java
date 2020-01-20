package bot.core.listners;

import bot.Bootstrapper;
import bot.core.interfaces.Loggable;
import org.apache.commons.codec.digest.DigestUtils;
import java.io.FileInputStream;
import java.io.IOException;

public class ConfigurationsListener implements Runnable, Loggable {
    private String bot_configMD5;
    private String vk_configMD5;
    private boolean exit = false;
    {
        try {
            bot_configMD5 = DigestUtils.md5Hex(new FileInputStream("bot-config.properties"));
            vk_configMD5 = DigestUtils.md5Hex(new FileInputStream("vk-config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        String bot_configMD5_new = null;
        String vk_configMD5_new = null;
        while (!exit){
            try {
                bot_configMD5_new = DigestUtils.md5Hex(new FileInputStream("bot-config.properties"));
                vk_configMD5_new = DigestUtils.md5Hex(new FileInputStream("vk-config.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(bot_configMD5 != null && vk_configMD5 != null){
                if(!bot_configMD5.equals(bot_configMD5_new) || !vk_configMD5.equals(vk_configMD5_new)){
                    try {
                        bot_configMD5 = DigestUtils.md5Hex(new FileInputStream("bot-config.properties"));
                        vk_configMD5 = DigestUtils.md5Hex(new FileInputStream("vk-config.properties"));
                    } catch (IOException e) {
                        e.printStackTrace();
                        stop();
                    }
                    Bootstrapper.Configurations.updateConfiguration();
                    LogToConsole("Configuration update success.");
                }
            }
            else {
                try {
                    bot_configMD5_new = DigestUtils.md5Hex(new FileInputStream("bot-config.properties"));
                    vk_configMD5_new = DigestUtils.md5Hex(new FileInputStream("vk-config.properties"));
                } catch (IOException e) {
                    e.printStackTrace();
                    LogToConsole("Fatal error load config.");
                    stop();
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void stop(){
        LogToConsole("Fatal error");
        exit = true;
    }
}
