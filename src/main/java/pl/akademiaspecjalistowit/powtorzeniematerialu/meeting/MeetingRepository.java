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

    public List<Meeting> findByEmail(String email) {
        List<Meeting> allMeetings = findAll();
        List<Meeting> findMeetings = new ArrayList<>();
        for (Meeting meeting : allMeetings) {
            if (meeting.getParticipantEmail().contains(email)) {
                findMeetings.add(meeting);
            }
        }if (findMeetings.isEmpty()){
            throw new MeetingException("Not found meeting");
        }
        return findMeetings;
    }
}
