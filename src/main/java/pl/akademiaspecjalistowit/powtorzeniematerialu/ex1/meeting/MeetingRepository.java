package pl.akademiaspecjalistowit.powtorzeniematerialu.ex1.meeting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
