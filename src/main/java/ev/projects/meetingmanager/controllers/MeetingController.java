package ev.projects.meetingmanager.controllers;

import ev.projects.meetingmanager.handlers.FileHandler;
import ev.projects.meetingmanager.models.MeetingBinding;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeetingController {

    private final FileHandler fileHandler = new FileHandler();

    @PostMapping("/meeting")
    private void createMeeting(@RequestBody MeetingBinding meetingBinding) {
        fileHandler.createMeeting(meetingBinding);
    }

}
