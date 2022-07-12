package ev.projects.meetingmanager.handlers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import ev.projects.meetingmanager.models.MeetingBinding;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class FileHandler {

    File dataFile;
    private final Type listType = new TypeToken<List<MeetingBinding>>() {}.getType();

    public FileHandler() {
        createFileIfNotFound();
    }

    private void createFileIfNotFound() {
        dataFile = new File("data/meetings.json");
        if(!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                writeToFile("[]");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createMeeting(MeetingBinding meetingBinding) {
        List<MeetingBinding> meetingBindings = getMeetingsFromFile();
        meetingBindings.add(meetingBinding);
        writeObjectToFile(meetingBindings, listType);
    }

    private List<MeetingBinding> getMeetingsFromFile() {
        List<MeetingBinding> meetings = null;
        try(JsonReader reader = new JsonReader(new FileReader(dataFile))) {
            meetings = new Gson().fromJson(reader, listType);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return meetings;
    }

    private <T> void writeObjectToFile(T data, Type dataType) {
        String jsonData = new Gson().toJson(data, dataType);
        writeToFile(jsonData);
    }

    private void writeToFile(String data) {
        try(FileWriter fileWriter = new FileWriter(dataFile)) {
            fileWriter.write(data);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
