package pl.akademiaspecjalistowit.powtorzeniematerialu.app;

import java.util.*;

import pl.akademiaspecjalistowit.powtorzeniematerialu.meeting.Meeting;
import pl.akademiaspecjalistowit.powtorzeniematerialu.meeting.MeetingRepository;
import pl.akademiaspecjalistowit.powtorzeniematerialu.meeting.MeetingService;

public class MeetingApp {

    private MeetingService meetingService;


    public MeetingApp() {
        MeetingRepository meetingRepository = MeetingRepository.getMeetingRepository(new HashMap<>());
        this.meetingService = MeetingService.getMeetingService(meetingRepository);
    }

    public void run() {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        try {
            while (!exit) {
                exit = printMenu(scanner);
            }
        } catch (Exception e) {
            System.out.println("Wystąpił problem, przepraszamy: " + e);
            run();
        }

    }

    private boolean printMenu(Scanner scanner) {

        System.out.println("\n Dostępne komendy: ");
        System.out.println("1) Nowe spotkanie");
        System.out.println("2) Pokaż wszystkie spotkania");
        System.out.println("3) Usuń spotkanie");
        System.out.println("4) Pokaż spotkania dla użutkownika");
        System.out.print("Wpisz komendę: ");
        String command = scanner.nextLine();

        switch (command) {
            case "1":
                createMeeting(scanner);
                break;
            case "2":
                showMeetings();
                break;
            case "3":
                deleteMeeting(scanner);
                break;
            case "4":
                shoeMeetingForEmail(scanner);
                break;
            case "exit":
                System.out.println("Zamykanie aplikacji...");
                return true;
            default:
                System.out.println("Nieznana komenda. Spróbuj ponownie.");
                break;
        }
        return false;
    }

    private void showMeetings() {
        List<Meeting> allMeetings = meetingService.getAllMeetings();
        if (allMeetings.isEmpty()) {
            System.out.println("Brak spotkań");
            return;
        }
        for (Meeting meeting : allMeetings) {
            System.out.println(meeting);
        }
    }

    private void createMeeting(Scanner scanner) {
        System.out.println("Tworzenie nowego spotkania...");
        System.out.println("Podaj nazwę spotkania: ");
        String meetingName = scanner.nextLine();

        System.out.println("Podaj datę i godzinę spotkania (w formacie DD:MM:RRRR HH:MM): ");
        String meetingDateTimeString = scanner.nextLine();

        System.out.println("Podaj długość trwania spotkania (w formacie HH:MM) ");
        String meetingDuration = scanner.nextLine();

        Set<String> participantEmail = new HashSet<>();
        boolean stop = false;
        while (!stop) {
            System.out.println("Podaj email uczestnika do zaprosznia: ");
            participantEmail.add(scanner.nextLine());
            System.out.println("Chcesz dodać więcej uczestników?: (T/N)");
            String decission = scanner.nextLine();
            if (decission.equalsIgnoreCase("N")) {
                stop = true;
            }
        }

        Meeting newMeeting =
                meetingService.createNewMeeting(meetingName, meetingDateTimeString, participantEmail, meetingDuration);

        System.out.println("Spotkanie " + newMeeting + " zostało utworzone.");
    }

    private void deleteMeeting(Scanner scanner) {
        System.out.println("Podaj Id: ");
        UUID inputId = UUID.fromString(scanner.nextLine());
        meetingService.deleteMeetingById(inputId);
    }

    private void shoeMeetingForEmail(Scanner scanner) {
        System.out.println("Podaj Email użytkownika: ");
        String meetEmail = scanner.nextLine();
        List<Meeting> allMeetings = meetingService.getMeetingByEmail(meetEmail);
        for (Meeting allMeeting : allMeetings) {
            System.out.println(allMeeting);
        }
    }
}
