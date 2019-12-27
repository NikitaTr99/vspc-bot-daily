package bot.core.actions.daily.utils;

import java.util.ArrayList;

public class Subject {
    public String name;
    public String classroom;
    public String start_time;
    public String end_time;

    Subject(String line){
        name = line.split(";")[0];
        classroom = line.split(";")[1];
        start_time = line.split(";")[2];
        end_time = line.split(";")[3];
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s - %s).",name,classroom,start_time,end_time);
    }
}
