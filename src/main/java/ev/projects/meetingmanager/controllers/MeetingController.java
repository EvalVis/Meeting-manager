package ev.projects.meetingmanager.controllers;

import ev.projects.meetingmanager.handlers.FileHandler;
import ev.projects.meetingmanager.models.MeetingBinding;
import org.springframework.web.bind.annotation.*;

@RestController
public class MeetingController {

    private final FileHandler fileHandler = new FileHandler();

    @PostMapping("/meeting")
    private void createMeeting(@RequestBody MeetingBinding meetingBinding) {
        fileHandler.createMeeting(meetingBinding);
    }

    @DeleteMapping("/meeting")
    private void deleteMeeting(@RequestParam("meeting_name") String meetingName,
                               @RequestHeader("responsible_person") String responsiblePerson) {
        fileHandler.deleteMeeting(meetingName, responsiblePerson);
    }

}
