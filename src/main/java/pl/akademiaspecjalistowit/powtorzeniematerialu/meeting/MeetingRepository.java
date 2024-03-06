package pl.akademiaspecjalistowit.powtorzeniematerialu.meeting;

import java.util.*;

public class MeetingRepository {

    private Map<Long, Meeting> meetings;

    public MeetingRepository() {
        meetings = new HashMap<>();
    }

    public void save(Meeting meeting) {
        meetings.put((long) meetings.size(), meeting);
    }

    public List<Meeting> findAll() {
        return meetings.values().stream().toList();
    }

    public void delete(Meeting meeting) {
        meetings.remove(meeting.getMeetingId());
    }

    public Meeting findById(UUID meetingId) {
        for (Meeting meeting : meetings.values()) {
            if (meeting.getMeetingId().equals(meetingId)) {
                return meeting;
            }
        }
        throw  new MeetingException("Not found meeting");
    }

    public void deleteById(UUID meetingId) {
        Meeting meeting = findById(meetingId);
        delete(meeting);
    }
}
