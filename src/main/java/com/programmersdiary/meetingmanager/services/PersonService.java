package com.programmersdiary.meetingmanager.services;

import com.programmersdiary.meetingmanager.models.MeetingBinding;
import com.programmersdiary.meetingmanager.models.PersonBinding;
import com.programmersdiary.meetingmanager.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public boolean addPersonToMeeting(String meetingName, PersonBinding personBinding) {
        boolean intersectingMeetings = false;
        List<MeetingBinding> meetingBindings = personRepository.getMeetings();
        MeetingBinding meetingToAddTo = MeetingBinding.findMeetingByName(meetingBindings, meetingName);
        if(meetingToAddTo != null) {
            if (meetingIntersects(meetingBindings, meetingToAddTo)) {
                intersectingMeetings = true;
            }
            if (!meetingToAddTo.findParticipant(personBinding.getFullName())) {
                personRepository.addPersonToMeeting(meetingName, personBinding);
            }
        }
        return intersectingMeetings;
    }

    public boolean removePersonFromMeeting(String meetingName, String personFullName) {
        List<MeetingBinding> meetingBindings = personRepository.getMeetings();
        MeetingBinding meetingToRemoveFrom = MeetingBinding.findMeetingByName(meetingBindings, meetingName);
        if (meetingToRemoveFrom == null || meetingToRemoveFrom.getResponsiblePerson().equalsIgnoreCase(personFullName)) {
            return false;
        }
        personRepository.deletePersonFromMeeting(meetingName, personFullName);
        return true;
    }

    private boolean meetingIntersects(List<MeetingBinding> meetingBindings, MeetingBinding meetingBindingToCompare) {
        for(MeetingBinding meetingBinding: meetingBindings) {
            if(meetingBinding.meetingsIntersect(meetingBindingToCompare) &&
                    !meetingBinding.equals(meetingBindingToCompare)) {
                return true;
            }
        }
        return false;
    }

}
