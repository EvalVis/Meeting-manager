package ev.projects.meetingmanager.handlers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import ev.projects.meetingmanager.models.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
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

    public boolean AddPersonToMeeting(String meetingName, PersonBinding personBinding) {
        boolean intersectingMeetings = false;
        List<MeetingBinding> meetingBindings = getMeetingsFromFile();
        MeetingBinding meetingToAddTo = MeetingBinding.findMeetingByName(meetingBindings, meetingName);
        if(MeetingBinding.meetingsIntersect(meetingBindings, meetingToAddTo)) {
            intersectingMeetings = true;
        }
        if(!meetingToAddTo.findParticipant(personBinding.getFullName())) {
            meetingToAddTo.addParticipant(personBinding);
        }
        return intersectingMeetings;
    }

    public void removePerson(String meetingName, String personFullName) {
        List<MeetingBinding> meetingBindings = getMeetingsFromFile();
        MeetingBinding meetingToRemoveFrom = MeetingBinding.findMeetingByName(meetingBindings, meetingName);
        if(meetingToRemoveFrom != null &&
                !meetingToRemoveFrom.getResponsiblePerson().equalsIgnoreCase(personFullName)) {
            meetingToRemoveFrom.removeParticipant(personFullName);
        }
    }

    public List<MeetingBinding> listMeetings(MeetingSearchBinding meetingSearchBinding) {
        List<MeetingBinding> meetingBindings = getMeetingsFromFile();
        return meetingSearchBinding.filterMeetings(meetingBindings);
    }



    public void deleteMeeting(String meetingName, String responsiblePerson) {
        List<MeetingBinding> meetingBindings = getMeetingsFromFile();
        MeetingBinding meetingToDelete = MeetingBinding.findMeetingByName(meetingBindings, meetingName);
        if(meetingToDelete != null && meetingToDelete.getResponsiblePerson().equalsIgnoreCase(responsiblePerson)) {
            meetingBindings.remove(meetingToDelete);
            writeObjectToFile(meetingBindings, listType);
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
