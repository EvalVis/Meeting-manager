package com.programmersdiary.meetingmanager.services;

import com.programmersdiary.meetingmanager.models.MeetingBinding;
import com.programmersdiary.meetingmanager.models.MeetingCategory;
import com.programmersdiary.meetingmanager.models.MeetingSearchBinding;
import com.programmersdiary.meetingmanager.models.MeetingType;
import com.programmersdiary.meetingmanager.repositories.MeetingRepository;
import com.programmersdiary.meetingmanager.testutils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MeetingServiceTests {

    MeetingRepository meetingRepository;

    MeetingService meetingService;

    @BeforeEach
    public void setUp() {
        meetingRepository = new MeetingRepository();
        meetingRepository.setRepositoryFile(new File("test_meetings.json"));
        meetingRepository.recreateFile();
        meetingService = new MeetingService(meetingRepository);
    }

    @Test
    public void testDeleteMeetingNotResponsible() {
        String meetingName = "test";
        Date startDate = new Date();
        MeetingBinding meetingBinding = new MeetingBinding(meetingName, "Jackson", "Described", MeetingCategory.SHORT,
                MeetingType.LIVE, startDate, TestUtils.addHoursToDate(startDate, 1));
        meetingService.createMeeting(meetingBinding);
        meetingService.deleteMeeting(meetingName, "Smith");
        List<MeetingBinding> meetingBindings = meetingRepository.getMeetings();
        meetingBinding = MeetingBinding.findMeetingByName(meetingBindings, meetingName);
        assertNotNull(meetingBinding);
    }

    @Test
    public void testDeleteMeetingResponsible() {
        String meetingName = "test";
        String responsiblePerson = "Smith";
        Date startDate = new Date();
        MeetingBinding meetingBinding = new MeetingBinding(meetingName, responsiblePerson, "Described", MeetingCategory.SHORT,
                MeetingType.LIVE, startDate, TestUtils.addHoursToDate(startDate, 1));
        meetingService.createMeeting(meetingBinding);
        meetingService.deleteMeeting(meetingName, responsiblePerson);
        List<MeetingBinding> meetingBindings = meetingRepository.getMeetings();
        meetingBinding = MeetingBinding.findMeetingByName(meetingBindings, meetingName);
        assertNull(meetingBinding);
    }

    @Test
    public void testEmptySearchQuery() {
        Date startDate = new Date();
        List<MeetingBinding> meetings = new ArrayList<>();
        meetings.add(new MeetingBinding("ABC", "Jackson",
                "Described", MeetingCategory.SHORT,
                MeetingType.LIVE, startDate, TestUtils.addHoursToDate(startDate, 1)));
        meetings.add(new MeetingBinding("DEF", "SMITH", "Described",
                MeetingCategory.SHORT,
                MeetingType.LIVE, startDate, TestUtils.addHoursToDate(startDate, 1)));
        for(MeetingBinding meeting: meetings) {
            meetingRepository.createMeeting(meeting);
        }
        MeetingSearchBinding meetingSearchBinding = new MeetingSearchBinding();
        int size = meetingService.filterMeetings(meetingSearchBinding).size();
        assertEquals(meetings.size(), size);
    }

    @Test
    public void testSearchForDescription() {
        Date startDate = new Date();
        List<MeetingBinding> meetings = new ArrayList<>();
        meetings.add(new MeetingBinding("ABC", "Jackson",
                "ABCDEFGHIJ", MeetingCategory.SHORT,
                MeetingType.LIVE, startDate, TestUtils.addHoursToDate(startDate, 1)));
        meetings.add(new MeetingBinding("DEF", "SMITH", "Described",
                MeetingCategory.SHORT, MeetingType.LIVE, startDate, TestUtils.addHoursToDate(startDate, 1)));
        for(MeetingBinding meeting: meetings) {
            meetingRepository.createMeeting(meeting);
        }
        String descriptionToSearchFor = meetings.get(0).getDescription().substring(2, 4);
        MeetingSearchBinding meetingSearchBinding = new MeetingSearchBinding();
        meetingSearchBinding.setDescriptionHas(descriptionToSearchFor);
        int size = meetingService.filterMeetings(meetingSearchBinding).size();
        assertEquals(1, size);
    }

    @Test
    public void testSearchForDateRange() {
        Date firstMeetingStart = new Date();
        int firstMeetingLengthInHours = 5;
        Date firstMeetingEnd = TestUtils.addHoursToDate(firstMeetingStart, firstMeetingLengthInHours);
        Date secondMeetingStart = new Date();
        int secondMeetingLengthInHours = 32;
        Date secondMeetingEnd = TestUtils.addHoursToDate(secondMeetingStart, secondMeetingLengthInHours);
        List<MeetingBinding> meetings = new ArrayList<>();
        meetings.add(new MeetingBinding("ABC", "Jackson",
                "ABCDEFGHIJ", MeetingCategory.SHORT,
                MeetingType.LIVE, firstMeetingStart, firstMeetingEnd));
        meetings.add(new MeetingBinding("DEF", "SMITH", "Described",
                MeetingCategory.SHORT, MeetingType.LIVE, secondMeetingStart, secondMeetingEnd));
        for(MeetingBinding meeting: meetings) {
            meetingRepository.createMeeting(meeting);
        }
        Date firstSearchEndDate = TestUtils.addHoursToDate(firstMeetingStart, firstMeetingLengthInHours + 2);
        MeetingSearchBinding firstMeetingSearchBinding = new MeetingSearchBinding();
        firstMeetingSearchBinding.setDateTo(firstSearchEndDate);
        int size = meetingService.filterMeetings(firstMeetingSearchBinding).size();
        assertEquals(1, size);
        Date secondSearchEndDate = TestUtils.addHoursToDate(firstMeetingStart, firstMeetingLengthInHours + 200);
        MeetingSearchBinding secondMeetingSearchBinding = new MeetingSearchBinding();
        secondMeetingSearchBinding.setDateTo(secondSearchEndDate);
        size = meetingService.filterMeetings(secondMeetingSearchBinding).size();
        assertEquals(2, size);
    }

}
