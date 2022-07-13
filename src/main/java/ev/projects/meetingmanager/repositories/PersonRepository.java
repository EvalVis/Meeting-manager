package ev.projects.meetingmanager.repositories;

import com.google.gson.reflect.TypeToken;
import ev.projects.meetingmanager.models.MeetingBinding;
import ev.projects.meetingmanager.models.PersonBinding;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;

@Repository
public class PersonRepository extends JsonFileRepository {

    private final Type meetingsListType = new TypeToken<List<MeetingBinding>>() {}.getType();

    public List<MeetingBinding> getMeetings() {
        return getListFromFile(meetingsListType);
    }

    public void addPersonToMeeting(String meetingName, PersonBinding personBinding) {
        List<MeetingBinding> meetingBindings = getMeetings();
        MeetingBinding meetingToAddTo = MeetingBinding.findMeetingByName(meetingBindings, meetingName);
        meetingToAddTo.addParticipant(personBinding);
        writeObjectToFile(meetingBindings, meetingsListType);
    }

    public void deletePersonFromMeeting(String meetingName, String personFullName) {
        List<MeetingBinding> meetingBindings = getMeetings();
        MeetingBinding meetingToRemoveFrom = MeetingBinding.findMeetingByName(meetingBindings, meetingName);
        meetingToRemoveFrom.removeParticipant(personFullName);
        writeObjectToFile(meetingBindings, meetingsListType);
    }

}
