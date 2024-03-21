package pl.akademiaspecjalistowit.powtorzeniematerialu.notification;

import java.util.Set;

public interface NotificationService {
    void notifyParticipants(Set<String> participants);
}
