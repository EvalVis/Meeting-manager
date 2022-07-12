package ev.projects.meetingmanager.controllers;

import ev.projects.meetingmanager.handlers.FileHandler;
import ev.projects.meetingmanager.models.PersonBinding;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    private final FileHandler fileHandler = new FileHandler();

    @PostMapping("/person")
    private void addPersonToMeeting(@RequestParam("meeting_name") String meetingName,
                                    @RequestBody PersonBinding personBinding) {
        fileHandler.AddPersonToMeeting(meetingName, personBinding);
    }

}
