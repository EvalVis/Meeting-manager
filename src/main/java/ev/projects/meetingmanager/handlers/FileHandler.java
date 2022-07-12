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

    File dataFile = new File("data/meetings.json");
    private final Type listType = new TypeToken<List<MeetingBinding>>() {}.getType();

    private void createFileIfNotFound() {
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

    public void deleteMeeting(String meetingName, String responsiblePerson) {
        List<MeetingBinding> meetingBindings = getMeetingsFromFile();
        for(MeetingBinding meetingBinding: meetingBindings) {
            if(meetingBinding.getName().equalsIgnoreCase(meetingName)) {
                if(meetingBinding.getResponsiblePerson().equalsIgnoreCase(responsiblePerson)) {
                    meetingBindings.remove(meetingBinding);
                    writeObjectToFile(meetingBindings, listType);
                }
            }
            break;
        }

    }

    private List<MeetingBinding> getMeetingsFromFile() {
        createFileIfNotFound();
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
        createFileIfNotFound();
        try(FileWriter fileWriter = new FileWriter(dataFile)) {
            fileWriter.write(data);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
