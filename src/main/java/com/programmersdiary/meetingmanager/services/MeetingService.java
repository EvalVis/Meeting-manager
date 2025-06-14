package com.programmersdiary.meetingmanager.services;

import com.programmersdiary.meetingmanager.models.MeetingBinding;
import com.programmersdiary.meetingmanager.models.MeetingSearchBinding;
import com.programmersdiary.meetingmanager.repositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public void createMeeting(MeetingBinding meetingBinding) {
        meetingRepository.createMeeting(meetingBinding);
    }

    public List<MeetingBinding> filterMeetings(MeetingSearchBinding meetingSearchBinding) {
        List<MeetingBinding> meetingBindings = meetingRepository.getMeetings();
        meetingBindings.removeIf(meetingBinding -> !meetingSearchBinding.matchesSearch(meetingBinding));
        return meetingBindings;
    }

    public void deleteMeeting(String meetingName, String responsiblePerson) {
        List<MeetingBinding> meetingBindings = meetingRepository.getMeetings();
        MeetingBinding meetingToDelete = MeetingBinding.findMeetingByName(meetingBindings, meetingName);
        if(meetingToDelete != null && meetingToDelete.getResponsiblePerson().equalsIgnoreCase(responsiblePerson)) {
            meetingRepository.deleteMeeting(meetingName);
        }
    }


}
