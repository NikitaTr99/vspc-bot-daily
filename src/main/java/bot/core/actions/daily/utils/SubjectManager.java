package bot.core.actions.daily.utils;

import bot.Bootstrapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class SubjectManager {

    public ArrayList<Subject> subjects = new ArrayList<Subject>();

    public SubjectManager() {
        if(!Bootstrapper.Configurations.getPathToDays().equals("null")){
            try {
                initSubjects(
                        new FileInputStream(
                                new File(Bootstrapper.Configurations.getPathToDays()
                                        + "/"
                                        + Bootstrapper.Configurations.getDayOfWeek()
                                        + ".txt"
                        )));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void addSubject(Subject subject){
        subjects.add(subject);
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

    public String TodaySchedule() {
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
