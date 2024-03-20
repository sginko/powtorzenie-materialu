package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import java.time.LocalDateTime;
import java.util.*;

public interface MeetingService {
    Meeting createNewMeeting(String meetingName, String meetingDateTimeString, Set<String> participantEmail,
                             String meetingDuration);

    void deleteMeetingById(UUID meetingId);

    List<Meeting> getAllMeetings();

    List<Meeting> getMeetingByEmail(String email);
}
