package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import java.util.*;
import java.time.LocalDateTime;
import java.util.*;

public class MeetingService {

    private MeetingRepository meetingRepository;

    public MeetingService() {
        meetingRepository = new MeetingRepository();
    }

    public Meeting createNewMeeting(String meetingName, String meetingDateTimeString, Set<String> participantEmail,
                                    String meetingDuration) {
        Meeting meeting = new Meeting(meetingName, meetingDateTimeString, participantEmail, meetingDuration);
        List<Meeting> allCreatedMeetings = meetingRepository.findAll();
        LocalDateTime newMeetingStartTime = meeting.getDateAndTime();
        LocalDateTime newMeetingEndTime = newMeetingStartTime.plus(meeting.getMeetingDuration());

        for (Meeting existingMeeting : allCreatedMeetings) {
            Set<String> commonParticipants = new HashSet<>(existingMeeting.getParticipantEmail());
            commonParticipants.retainAll(participantEmail);

            if (!commonParticipants.isEmpty()) {
                LocalDateTime existingMeetingStartTime = existingMeeting.getDateAndTime();
                LocalDateTime existingMeetingEndTime = existingMeetingStartTime.plus(existingMeeting.getMeetingDuration());

                if (existingMeetingStartTime.isBefore(newMeetingEndTime) && existingMeetingEndTime.isAfter(newMeetingStartTime)) {
                    throw new MeetingException("Kolidujące spotkanie już istnieje.");
                }
            }
        }
        meetingRepository.save(meeting);
        return meeting;
    }

    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    public void deleteMeetingById(UUID meetingId) {
        Map<Long, Meeting> meetings = meetingRepository.getMeetings();
        Iterator<Map.Entry<Long, Meeting>> iterator = meetings.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, Meeting> entry = iterator.next();
            if (entry.getValue().getMeetingId().equals(meetingId)) {
                iterator.remove();
            }
        }
    }
}
