package ev.projects.meetingmanager.controllers;

import ev.projects.meetingmanager.handlers.FileHandler;
import ev.projects.meetingmanager.models.PersonBinding;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    private final FileHandler fileHandler = new FileHandler();

    @PostMapping("/person")
    private boolean addPersonToMeeting(@RequestParam("meeting_name") String meetingName,
                                    @RequestBody PersonBinding personBinding) {
        return fileHandler.AddPersonToMeeting(meetingName, personBinding);
    }

    @DeleteMapping("/person")
    private void deletePerson(@RequestParam("meeting_name") String meetingName, String personFullName) {
        fileHandler.removePerson(meetingName, personFullName);
    }

}
