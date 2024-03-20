package pl.akademiaspecjalistowit.powtorzeniematerialu.app;

import pl.akademiaspecjalistowit.powtorzeniematerialu.meeting.Meeting;
import pl.akademiaspecjalistowit.powtorzeniematerialu.meeting.MeetingService;
import pl.akademiaspecjalistowit.powtorzeniematerialu.meeting.MeetingServiceImpl;
import pl.akademiaspecjalistowit.powtorzeniematerialu.notification.NotificationService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MeetingWithNotificationService implements MeetingService {
    private MeetingService meetingService;
    private NotificationService notificationService;

    public MeetingWithNotificationService(MeetingService meetingService, NotificationService notificationService) {
        this.meetingService = meetingService;
        this.notificationService = notificationService;
    }

    @Override
    public Meeting createNewMeeting(String meetingName, String meetingDateTimeString, Set<String> participantEmail, String meetingDuration) {
        meetingService.createNewMeeting(meetingName, meetingDateTimeString, participantEmail, meetingDuration);
        notificationService.notifyParticipants(ne);
    }

    @Override
    public void deleteMeetingById(UUID meetingId) {

    }

    @Override
    public List<Meeting> getAllMeetings() {
        return null;
    }

    @Override
    public List<Meeting> getMeetingByEmail(String email) {
        return null;
    }
}
