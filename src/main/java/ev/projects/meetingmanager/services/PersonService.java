package ev.projects.meetingmanager.services;

import ev.projects.meetingmanager.models.MeetingBinding;
import ev.projects.meetingmanager.models.PersonBinding;
import ev.projects.meetingmanager.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public boolean AddPersonToMeeting(String meetingName, PersonBinding personBinding) {
        boolean intersectingMeetings = false;
        List<MeetingBinding> meetingBindings = personRepository.getMeetings();
        MeetingBinding meetingToAddTo = MeetingBinding.findMeetingByName(meetingBindings, meetingName);
        if(MeetingBinding.meetingsIntersect(meetingBindings, meetingToAddTo)) {
            intersectingMeetings = true;
        }
        if(!meetingToAddTo.findParticipant(personBinding.getFullName())) {
            personRepository.addPersonToMeeting(meetingName, personBinding);
        }
        return intersectingMeetings;
    }

    public void removePersonFromMeeting(String meetingName, String personFullName) {
        List<MeetingBinding> meetingBindings = personRepository.getMeetings();
        MeetingBinding meetingToRemoveFrom = MeetingBinding.findMeetingByName(meetingBindings, meetingName);
        if(meetingToRemoveFrom != null &&
                !meetingToRemoveFrom.getResponsiblePerson().equalsIgnoreCase(personFullName)) {
            personRepository.deletePersonFromMeeting(meetingName, personFullName);
        }
    }

}
