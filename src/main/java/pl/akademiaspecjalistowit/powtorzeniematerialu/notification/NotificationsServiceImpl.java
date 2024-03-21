package pl.akademiaspecjalistowit.powtorzeniematerialu.notification;

import java.util.Set;

public class NotificationsServiceImpl implements NotificationService {
    @Override
    public void notifyParticipants(Set<String> participants) {
        participants.forEach(participant -> System.out.println(participants + " invited for meeting"));
    }
}
