package com.programmersdiary.meetingmanager.services;

import com.programmersdiary.meetingmanager.models.MeetingBinding;
import com.programmersdiary.meetingmanager.models.MeetingCategory;
import com.programmersdiary.meetingmanager.models.MeetingType;
import com.programmersdiary.meetingmanager.models.PersonBinding;
import com.programmersdiary.meetingmanager.repositories.MeetingRepository;
import com.programmersdiary.meetingmanager.repositories.PersonRepository;
import com.programmersdiary.meetingmanager.testutils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Date;
import java.util.List;

public class PersonServiceTests {

    private MeetingRepository meetingRepository;
    private PersonService personService;

    @BeforeEach
    public void setUp() {
        meetingRepository = new MeetingRepository();
        meetingRepository.setRepositoryFile(new File("test_meetings.json"));
        meetingRepository.recreateFile();
        PersonRepository personRepository = new PersonRepository();
        personRepository.setRepositoryFile(new File("test_meetings.json"));
        personRepository.recreateFile();
        personService = new PersonService(personRepository);
    }

    @Test
    public void addPersonToMeetingTwoTimes() {
        String meetingName = "Test";
        Date startDate = new Date();
        MeetingBinding meetingBinding = new MeetingBinding(meetingName, "Jackson", "Described", MeetingCategory.SHORT,
                MeetingType.LIVE, startDate, TestUtils.addHoursToDate(startDate, 1));
        meetingRepository.createMeeting(meetingBinding);
        PersonBinding personBinding = new PersonBinding("Smith", new Date());
        boolean warned = personService.addPersonToMeeting(meetingName, personBinding);
        assertFalse(warned);
        List<MeetingBinding> meetingBindings = meetingRepository.getMeetings();
        MeetingBinding foundMeeting = MeetingBinding.findMeetingByName(meetingBindings, meetingName);
        assertNotNull(foundMeeting);
        int sizeAfterFirstInsert = foundMeeting.getParticipants().size();
        warned = personService.addPersonToMeeting(meetingName, personBinding);
        assertFalse(warned);
        meetingBindings = meetingRepository.getMeetings();
        foundMeeting = MeetingBinding.findMeetingByName(meetingBindings, meetingName);
        assertNotNull(foundMeeting);
        int sizeAfterSecondInsert = foundMeeting.getParticipants().size();
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
        boolean warned = personService.addPersonToMeeting(firstMeetingName, personBinding);
        assertTrue(warned);
        warned = personService.addPersonToMeeting(secondMeetingName, personBinding);
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
        personService.addPersonToMeeting(meetingName, personBinding);
        MeetingBinding meetingToRemoveFrom = MeetingBinding.
                findMeetingByName(meetingRepository.getMeetings(), meetingName);
        assertNotNull(meetingToRemoveFrom);
        int startingParticipants = meetingToRemoveFrom.getParticipants().size();
        personService.removePersonFromMeeting(meetingName, responsiblePerson);
        meetingToRemoveFrom = MeetingBinding.findMeetingByName(meetingRepository.getMeetings(), meetingName);
        assertNotNull(meetingToRemoveFrom);
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
        personService.addPersonToMeeting(meetingName, personBinding);
        MeetingBinding meetingToRemoveFrom = MeetingBinding.
                findMeetingByName(meetingRepository.getMeetings(), meetingName);
        assertNotNull(meetingToRemoveFrom);
        int startingParticipants = meetingToRemoveFrom.getParticipants().size();
        personService.removePersonFromMeeting(meetingName, notResponsiblePerson);
        meetingToRemoveFrom = MeetingBinding.
                findMeetingByName(meetingRepository.getMeetings(), meetingName);
        assertNotNull(meetingToRemoveFrom);
        int endingParticipants = meetingToRemoveFrom.getParticipants().size();
        assertEquals(startingParticipants, endingParticipants + 1);
    }

}
