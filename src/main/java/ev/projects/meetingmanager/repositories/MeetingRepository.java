package ev.projects.meetingmanager.repositories;

import com.google.gson.reflect.TypeToken;
import ev.projects.meetingmanager.models.MeetingBinding;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;

@Repository
public class MeetingRepository extends JsonFileRepository{

    private final Type meetingsListType = new TypeToken<List<MeetingBinding>>() {}.getType();

    public void createMeeting(MeetingBinding meetingBinding) {
        List<MeetingBinding> meetingBindings = getMeetings();
        meetingBindings.add(meetingBinding);
        writeObjectToFile(meetingBindings, meetingsListType);
    }

    public List<MeetingBinding> getMeetings() {
        return getListFromFile(meetingsListType);
    }

    public void deleteMeeting(MeetingBinding meetingBindingToDelete) {
        List<MeetingBinding> meetingBindings = getMeetings();
        meetingBindings.remove(meetingBindingToDelete);
        writeObjectToFile(meetingBindings, meetingsListType);
    }

}
