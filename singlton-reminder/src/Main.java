import java.util.*;
import java.util.Timer;
import java.util.TimerTask;

// MAIN PROGRAM
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReminderManager manager = ReminderManager.getInstance();
        ReminderUser user = new ReminderUser();

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Добавить напоминание через администратора системы");
            System.out.println("2. Показать напоминания");
            System.out.println("3. Добавить напоминание через другой пользователя");
            System.out.println("4. Выйти");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Введите текст напоминания: ");
                    String message = scanner.nextLine();
                    System.out.print("Через сколько секунд напомнить? ");
                    int seconds = scanner.nextInt();
                    manager.addReminder(message, seconds);
                    break;
                case 2:
                    manager.showReminders();
                    break;
                case 3:
                    System.out.print("Введите текст напоминания (другой класс): ");
                    String userMessage = scanner.nextLine();
                    System.out.print("Через сколько секунд напомнить? ");
                    int userSeconds = scanner.nextInt();
                    user.createReminder(userMessage, userSeconds);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Некорректный выбор.");
            }
        }
    }
}

// NOTIFICATION
class Reminder {
    private String message;
    private Date time;

    public Reminder(String message, Date time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public Date getTime() {
        return time;
    }
}

// USER
class ReminderUser {
    private final ReminderManager manager;

    public ReminderUser() {
        this.manager = ReminderManager.getInstance();
    }

    public void createReminder(String message, int seconds) {
        manager.addReminder(message, seconds);
    }

    public void displayReminders() {
        manager.showReminders();
    }
}

// SINGLTON MANAGER
class ReminderManager {
    private static ReminderManager instance;
    private final List<Reminder> reminders;
    private final Timer timer;

    private ReminderManager() {
        reminders = new ArrayList<>();
        timer = new Timer(true);
    }

    public static synchronized ReminderManager getInstance() {
        if (instance == null) {
            instance = new ReminderManager();
        }
        return instance;
    }

    public void addReminder(String message, int secondsFromNow) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, secondsFromNow);
        Date reminderTime = calendar.getTime();

        Reminder reminder = new Reminder(message, reminderTime);
        reminders.add(reminder);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\n\n=============================================");
                System.out.println("\n[Напоминание] " + message);
                System.out.println("=============================================\n\n");
                reminders.remove(reminder);
            }
        }, reminderTime);

        System.out.println("Напоминание добавлено: " + message + " через " + secondsFromNow + " секунд.");
    }

    public void showReminders() {
        if (reminders.isEmpty()) {
            System.out.println("Нет активных напоминаний.");
            return;
        }

        System.out.println("\nАктивные напоминания:");
        for (Reminder reminder : reminders) {
            System.out.println("- " + reminder.getMessage() + " в " + reminder.getTime());
        }
    }
}
