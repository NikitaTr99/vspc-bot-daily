package bot.core.interfaces;

import java.nio.charset.StandardCharsets;

public interface Loggable {
    default void LogToConsole(String log)  {
        System.out.println(new String(log.getBytes(), StandardCharsets.UTF_8));
    }
}
