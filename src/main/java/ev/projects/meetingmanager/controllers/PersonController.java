package ev.projects.meetingmanager.controllers;

import ev.projects.meetingmanager.models.PersonBinding;
import ev.projects.meetingmanager.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person")
    private boolean addPersonToMeeting(@RequestParam("meeting_name") String meetingName,
                                    @RequestBody PersonBinding personBinding) {
        return personService.AddPersonToMeeting(meetingName, personBinding);
    }

    @DeleteMapping("/person")
    private void deletePerson(@RequestParam("meeting_name") String meetingName, String personFullName) {
        personService.removePersonFromMeeting(meetingName, personFullName);
    }

}
