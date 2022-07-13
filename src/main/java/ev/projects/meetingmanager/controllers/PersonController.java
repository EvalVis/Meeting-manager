package ev.projects.meetingmanager.controllers;

import ev.projects.meetingmanager.models.PersonBinding;
import ev.projects.meetingmanager.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person")
    private ResponseEntity<Boolean> addPersonToMeeting(@RequestParam("meeting_name") String meetingName,
                                                       @RequestBody PersonBinding personBinding) {
        if(!personBinding.anyFieldNull()) {
            Boolean warned = personService.AddPersonToMeeting(meetingName, personBinding);
            return new ResponseEntity<>(warned, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/person")
    private void deletePersonFromMeeting(@RequestParam("meeting_name") String meetingName,
                              @RequestParam("person_full_name") String personFullName) {
        personService.removePersonFromMeeting(meetingName, personFullName);
    }

}
