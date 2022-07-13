package ev.projects.meetingmanager.services;

import ev.projects.meetingmanager.models.MeetingBinding;
import ev.projects.meetingmanager.models.MeetingCategory;
import ev.projects.meetingmanager.models.MeetingType;
import ev.projects.meetingmanager.models.PersonBinding;
import ev.projects.meetingmanager.repositories.MeetingRepository;
import ev.projects.meetingmanager.repositories.PersonRepository;
import ev.projects.meetingmanager.testutils.TestUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Date;
import java.util.List;

public class PersonServiceTests {

    private MeetingRepository meetingRepository;
    private PersonRepository personRepository;
    private PersonService personService;

    @Before
    public void setUp() {
        meetingRepository = new MeetingRepository();
        meetingRepository.setRepositoryFile(new File("data/test_meetings.json"));
        meetingRepository.recreateFile();
        personRepository = new PersonRepository();
        personRepository.setRepositoryFile(new File("data/test_meetings.json"));
        personRepository.recreateFile();
        personService = new PersonService(personRepository);
    }

    @Test
    public void addPersonToMeetingFirstTime() {
        String meetingName = "Test";
        Date startDate = new Date();
        MeetingBinding meetingBinding = new MeetingBinding(meetingName, "Jackson", "Described", MeetingCategory.SHORT,
                MeetingType.LIVE, startDate, TestUtils.addHoursToDate(startDate, 1));
        meetingRepository.createMeeting(meetingBinding);
        PersonBinding personBinding = new PersonBinding("Smith", new Date());
        boolean warned = personService.AddPersonToMeeting(meetingName, personBinding);
        List<MeetingBinding> meetingBindings = meetingRepository.getMeetings();
        int sizeAfterFirstInsert = MeetingBinding.findMeetingByName(meetingBindings, meetingName).
                getParticipants().size();
        assertFalse(warned);
        warned = personService.AddPersonToMeeting(meetingName, personBinding);
        assertFalse(warned);
        meetingBindings = meetingRepository.getMeetings();
        int sizeAfterSecondInsert = MeetingBinding.findMeetingByName(meetingBindings, meetingName).
        getParticipants().size();
        assertEquals(sizeAfterFirstInsert, sizeAfterSecondInsert);
    }

    @Test
    public void testMeetingIntersections() {
        String firstMeetingName = "first";
        String secondMeetingName = "second";
        Date startDate = new Date();
        MeetingBinding firstMeeting = new MeetingBinding(firstMeetingName, "Jackson", "Described",
                MeetingCategory.SHORT, MeetingType.LIVE, startDate, TestUtils.addHoursToDate(startDate, 10));
        MeetingBinding secondMeeting = new MeetingBinding(secondMeetingName, "Jackson", "Described",
                MeetingCategory.SHORT, MeetingType.LIVE, TestUtils.addHoursToDate(startDate, 4),
                TestUtils.addHoursToDate(startDate, 20));
        meetingRepository.createMeeting(firstMeeting);
        meetingRepository.createMeeting(secondMeeting);
        PersonBinding personBinding = new PersonBinding("Smith", new Date());
        boolean warned = personService.AddPersonToMeeting(firstMeetingName, personBinding);
        assertTrue(warned);
        warned = personService.AddPersonToMeeting(secondMeetingName, personBinding);
        assertTrue(warned);
    }

    @Test
    public void testDeleteResponsiblePersonFromMeeting() {
        String meetingName = "Test";
        String responsiblePerson = "Jackson";
        Date startDate = new Date();
        MeetingBinding meetingBinding = new MeetingBinding(meetingName, responsiblePerson, "Described",
                MeetingCategory.SHORT, MeetingType.LIVE, startDate, TestUtils.addHoursToDate(startDate, 10));
        meetingRepository.createMeeting(meetingBinding);
        PersonBinding personBinding = new PersonBinding(responsiblePerson, new Date());
        personService.AddPersonToMeeting(meetingName, personBinding);
        MeetingBinding meetingToRemoveFrom = MeetingBinding.
                findMeetingByName(meetingRepository.getMeetings(), meetingName);
        int startingParticipants = meetingToRemoveFrom.getParticipants().size();
        personService.removePersonFromMeeting(meetingName, responsiblePerson);
        meetingToRemoveFrom = MeetingBinding.
                findMeetingByName(meetingRepository.getMeetings(), meetingName);
        int endingParticipants = meetingToRemoveFrom.getParticipants().size();
        assertEquals(startingParticipants, endingParticipants);
    }

    @Test
    public void testDeleteNotResponsiblePersonFromMeeting() {
        String meetingName = "Test";
        String responsiblePerson = "Jackson";
        String notResponsiblePerson = responsiblePerson + "s";
        Date startDate = new Date();
        MeetingBinding meetingBinding = new MeetingBinding(meetingName, responsiblePerson, "Described",
                MeetingCategory.SHORT, MeetingType.LIVE, startDate, TestUtils.addHoursToDate(startDate, 10));
        meetingRepository.createMeeting(meetingBinding);
        PersonBinding personBinding = new PersonBinding(notResponsiblePerson, new Date());
        personService.AddPersonToMeeting(meetingName, personBinding);
        MeetingBinding meetingToRemoveFrom = MeetingBinding.
                findMeetingByName(meetingRepository.getMeetings(), meetingName);
        int startingParticipants = meetingToRemoveFrom.getParticipants().size();
        personService.removePersonFromMeeting(meetingName, notResponsiblePerson);
        meetingToRemoveFrom = MeetingBinding.
                findMeetingByName(meetingRepository.getMeetings(), meetingName);
        int endingParticipants = meetingToRemoveFrom.getParticipants().size();
        assertEquals(startingParticipants, endingParticipants + 1);
    }

}
