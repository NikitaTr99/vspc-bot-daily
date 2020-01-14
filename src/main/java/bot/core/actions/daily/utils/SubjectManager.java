package bot.core.actions.daily.utils;

import bot.Bootstrapper;
import bot.core.actions.schedule.TodaySchedule;
import bot.core.actions.schedule.TomorrowSchedule;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

public class SubjectManager {

    public ArrayList<Subject> subjects = new ArrayList<Subject>();

    public SubjectManager() {
        loadSchedule(Bootstrapper.Configurations.DayToday());
    }

    public SubjectManager(Object object) {
        if(object.getClass().getSimpleName().equals(TodaySchedule.class.getSimpleName())){
            loadSchedule(Bootstrapper.Configurations.DayToday());
        }
        else if(object.getClass().getSimpleName().equals(TomorrowSchedule.class.getSimpleName())){
            loadSchedule(Bootstrapper.Configurations.DayTomorrow());
        }
        else {
            loadSchedule(Bootstrapper.Configurations.DayToday());
        }
    }

    private void loadSchedule(String day_of_week){
        try {
            initSubjects(
                    new FileInputStream(
                            new File(Bootstrapper.Configurations.getPathToDays()
                                    + "/"
                                    + day_of_week
                                    + ".txt"
                            )));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addSubject(String line){
        subjects.add(new Subject(line));
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    private void initSubjects(InputStream inputStream){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                addSubject(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void initSubjectsForTomorrow(InputStream inputStream){
        ArrayList<Subject> subjects = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                subjects.add(new Subject(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSchedule() {
        String newLine = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 1;
        for(Subject subject: subjects){
            stringBuilder.append(counter++)
                    .append(". ")
                    .append(subject)
                    .append(newLine);
        }
        return stringBuilder.toString();
    }
}
