package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import java.util.*;
import java.time.LocalDateTime;

public class MeetingService {
    private static MeetingService meetingService;
    private MeetingRepository meetingRepository;

    public static synchronized MeetingService getMeetingService() {
        if (meetingService == null) {
            meetingService = new MeetingService();
        }
        return meetingService;
    }

    private MeetingService() {
        meetingRepository = MeetingRepository.getMeetingRepository();
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

    public List<Meeting> getMeetingByEmail(String email) {
        return meetingRepository.findByEmail(email);
    }
}
