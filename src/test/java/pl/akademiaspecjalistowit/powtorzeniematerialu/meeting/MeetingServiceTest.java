package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import static org.assertj.core.api.Java6Assertions.assertThat;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MeetingServiceTest {

    private MeetingService meetingService;

    @BeforeEach
    void setUp() {
        meetingService = new MeetingService();
    }

    @Test
    void should_create_meeting_correctly() {
        // GIVEN
        String meetingName = "Test Meeting";
        String meetingDateTimeString = "01:01:2024 12:00";
        Set<String> participantEmails = new HashSet<>();
        participantEmails.add("test@example.com");
        String meetingDuration = "02:00";

        // WHEN
        Meeting result =
                meetingService.createNewMeeting(meetingName, meetingDateTimeString, participantEmails, meetingDuration);

        // THEN
        List<Meeting> allMeetings = meetingService.getAllMeetings();
        assertThat(allMeetings).contains(result);
    }

    @Test
    void making_overlapping_meetings_for_different_participants_is_possible() {
        // GIVEN
        String meetingName = "Test Meeting";
        String meetingDateTimeString = "01:01:2024 12:00";
        Set<String> participantEmails = Set.of("test123@example.com");
        String meetingDuration = "02:00";
        Meeting existingMeeting =
                meetingService.createNewMeeting(meetingName, meetingDateTimeString, participantEmails, meetingDuration);

        String overlappingMeetingName = "Test Meeting";
        String overlappingMeetingDateTimeString = "01:01:2024 12:10";
        Set<String> overlappingParticipantEmails = Set.of("test1234@example.com");
        String OverlappingMeetingDuration = "01:00";

        // WHEN
        Meeting overlappingMeeting = meetingService
                .createNewMeeting(overlappingMeetingName,
                        overlappingMeetingDateTimeString,
                        overlappingParticipantEmails,
                        OverlappingMeetingDuration);

        // THEN
        List<Meeting> allMeetings = meetingService.getAllMeetings();
        assertThat(allMeetings).hasSize(2);
    }

    @Test
    void making_overlapping_meetings_for_these_same_participants_is_not_possible() {
        // GIVEN
        String meetingName = "Test Meeting";
        String meetingDateTimeString = "01:01:2024 12:00";
        Set<String> participantEmails = Set.of("test123@example.com");
        String meetingDuration = "02:00";
        Meeting existingMeeting =
                meetingService.createNewMeeting(meetingName, meetingDateTimeString, participantEmails, meetingDuration);

        String overlappingMeetingName = "Test Meeting";
        String overlappingMeetingDateTimeString = "01:01:2024 12:10";
        Set<String> overlappingParticipantEmails = Set.of("test123@example.com");
        String OverlappingMeetingDuration = "01:00";

//         WHEN
        try {
            Meeting overlappingMeeting = meetingService
                    .createNewMeeting(overlappingMeetingName,
                            overlappingMeetingDateTimeString,
                            overlappingParticipantEmails,
                            OverlappingMeetingDuration);

            // THEN
        } catch (MeetingException e){
            System.out.printf("");;
        }
        List<Meeting> allMeetings = meetingService.getAllMeetings();
        assertThat(allMeetings).hasSize(1);
    }

    @Test
    void should_delete_meeting_by_id_correctly() {

        // GIVEN
        MeetingRepository meetingRepository = new MeetingRepository();
        Map<Long, Meeting> meetings = new HashMap<>();
        Meeting meeting = new Meeting("Meeting 1", "01:01:2024 10:00", Set.of("test123@example.com"), "01:00");
        meetings.put(1L, meeting);
        meetingRepository.save(meeting);
        MeetingService meetingService = new MeetingService();
        UUID meetingIdToDelete = meeting.getMeetingId();

        // WHEN
        meetingService.deleteMeetingById(meetingIdToDelete);

        // THEN
        assertThat(meetingService.getAllMeetings()).hasSize(0);
        assertThat(meetingService.getAllMeetings()).doesNotContain(meeting);
    }

    @Test
    void should_find_meeting_by_email_correctly() {

        // GIVEN
        List<Meeting> allMeetings = new ArrayList<>();
        Meeting meeting1 = new Meeting("Meeting 1", "01:01:2024 10:00", Set.of("test1@example.com"), "01:00");
        Meeting meeting2 = new Meeting("Meeting 2", "01:01:2024 12:00", Set.of("test2@example.com"), "01:00");
        Meeting meeting3 = new Meeting("Meeting 3", "01:01:2024 14:00", Set.of("test3@example.com"), "01:00");
        allMeetings.add(meeting1);
        allMeetings.add(meeting2);
        allMeetings.add(meeting3);

        // WHEN
        MeetingService meetingService = new MeetingService();
        try {
            List<Meeting> meetingsByEmail = meetingService.getMeetingByEmail("test2@example.com");
            assertThat(meetingsByEmail).contains(meeting2);
            // THEN
        } catch (MeetingException e) {
            System.out.printf("Not found ");
        }
    }
}