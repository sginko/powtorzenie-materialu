package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import java.util.*;
import java.time.LocalDateTime;

public interface MeetingService {
    Meeting createNewMeeting(String meetingName, String meetingDateTimeString, Set<String> participantEmail,
                             String meetingDuration);

    List<Meeting> getAllMeetings();


    void deleteMeetingById(UUID meetingId);

    List<Meeting> getMeetingByEmail(String email);
}
