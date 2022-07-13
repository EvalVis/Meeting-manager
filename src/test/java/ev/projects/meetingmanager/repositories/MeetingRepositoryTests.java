package ev.projects.meetingmanager.repositories;

import ev.projects.meetingmanager.models.MeetingBinding;
import ev.projects.meetingmanager.models.MeetingCategory;
import ev.projects.meetingmanager.models.MeetingType;
import ev.projects.meetingmanager.testutils.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MeetingRepositoryTests {

    private MeetingRepository meetingRepository;

    @Before
    public void setUp() {
        meetingRepository = new MeetingRepository();
        meetingRepository.setRepositoryFile(new File("data/test_meetings.json"));
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
