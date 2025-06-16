package com.programmersdiary.meetingmanager.repositories;

import com.programmersdiary.meetingmanager.models.MeetingBinding;
import com.programmersdiary.meetingmanager.models.MeetingCategory;
import com.programmersdiary.meetingmanager.models.MeetingType;
import com.programmersdiary.meetingmanager.testutils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MeetingRepositoryTests {

    private MeetingRepository meetingRepository;

    @BeforeEach
    public void setUp() {
        meetingRepository = new MeetingRepository();
        meetingRepository.setRepositoryFile(new File("test_meetings.json"));
        meetingRepository.recreateFile();
    }

    @Test
    public void testCreateMeeting() {
        List<MeetingBinding> meetingBindings = meetingRepository.getMeetings();
        assertNotNull(meetingBindings);
        int startingSize = meetingBindings.size();
        Date startDate = new Date();
        MeetingBinding meetingBinding = new MeetingBinding("Test", "Jackson", "Described", MeetingCategory.SHORT,
                MeetingType.LIVE, startDate, TestUtils.addHoursToDate(startDate, 1));
        meetingRepository.createMeeting(meetingBinding);
        meetingBindings = meetingRepository.getMeetings();
        assertNotNull(meetingBindings);
        int endingSize = meetingRepository.getMeetings().size();
        assertEquals(endingSize, startingSize + 1);
    }

}
