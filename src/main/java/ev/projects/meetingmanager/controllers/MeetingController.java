package ev.projects.meetingmanager.controllers;

import ev.projects.meetingmanager.models.MeetingBinding;
import ev.projects.meetingmanager.models.MeetingSearchBinding;
import ev.projects.meetingmanager.services.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MeetingController {

    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/meetings")
    private List<MeetingBinding> listMeetings(@RequestBody MeetingSearchBinding meetingSearchBinding) {
        return meetingService.filterMeetings(meetingSearchBinding);
    }

    @PostMapping("/meeting")
    private HttpStatus createMeeting(@RequestBody MeetingBinding meetingBinding) {
        if(!meetingBinding.anyFieldNull()) {
            meetingService.createMeeting(meetingBinding);
            return HttpStatus.CREATED;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @DeleteMapping("/meeting")
    private void deleteMeeting(@RequestParam("meeting_name") String meetingName,
                               @RequestHeader("responsible_person") String responsiblePerson) {
        meetingService.deleteMeeting(meetingName, responsiblePerson);
    }

}
