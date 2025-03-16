import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

// REPRESENTING USER MICROSERVICE
public class Main {
    public static void main(String[] args) {
        TaskService taskService = new AuthenticatedTaskProxy();
        taskService.authenticate("secret-token-123");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Получить задачу по ID");
            System.out.println("2. Создать задачу");
            System.out.println("3. Удалить задачу");
            System.out.println("4. Выход");
            System.out.print("Ваш выбор: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            if (choice == 4) break;

            switch (choice) {
                case 1:
                    System.out.print("Введите ID задачи: ");
                    int readId = scanner.nextInt();
                    System.out.println(taskService.getTask(readId));
                    break;
                case 2:
                    System.out.print("Введите описание задачи: ");
                    String task = scanner.nextLine();
                    System.out.println(taskService.createTask(task));
                    break;
                case 3:
                    System.out.print("Введите ID задачи для удаления: ");
                    int deleteId = scanner.nextInt();
                    System.out.println("Удаление успешно: " + taskService.deleteTask(deleteId));
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        }
        scanner.close();
    }
}

// REPRESENTING INTERFACE FOR TASK MICROSERVICE
interface TaskService {
    String getTask(int taskId);         // Получить задачу по ID
    String createTask(String task);     // Создать новую задачу
    boolean deleteTask(int taskId);
    boolean authenticate(String token);// Удалить задачу
}

// REPRESENTING TASK MICROSERVICE
class RealTaskService implements TaskService {
    private HashMap<Integer, String> tasks = new HashMap<>();
    private int taskCounter = 2;

    public RealTaskService() {
        // Инициализация тестовыми данными
        tasks.put(1, "Задача 1: Написать код (создано: " + LocalDateTime.now() + ")");
        tasks.put(2, "Задача 2: Протестировать приложение (создано: " + LocalDateTime.now() + ")");
    }

    @Override
    public String getTask(int taskId) {
        return tasks.getOrDefault(taskId, "Задача с ID " + taskId + " не найдена");
    }

    @Override
    public String createTask(String task) {
        int newTaskId = ++taskCounter;
        tasks.put(newTaskId, task + " (создано: " + LocalDateTime.now() + ")");
        return "Задача создана с ID: " + newTaskId;
    }

    @Override
    public boolean deleteTask(int taskId) {
        if (tasks.containsKey(taskId)) {
            tasks.remove(taskId);
            return true;
        }
        return false;
    }

    // Микросервис открыт и не имеет аутентификации
    @Override
    public boolean authenticate(String token) {
        return true;
    }
}

// REPRESENTING PROXY FOR TASK MICROSERVICE
class AuthenticatedTaskProxy implements TaskService {
    private RealTaskService realService;
    private String validToken;
    private boolean authenticated;

    public AuthenticatedTaskProxy() {
        this.realService = new RealTaskService();
        validToken = "secret-token-123";
        authenticated = false;
    }

    // Аутентификация с токеном
    @Override
    public boolean authenticate(String token) {
        if (validToken.equals(token)) {
            authenticated = true;
            log("Успешная аутентификация с токеном: " + token);
            return true;
        } else {
            authenticated = false;
            log("Ошибка аутентификации: неверный токен");
            return false;
        }
    }

    // Логирование
    private void log(String message) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println("[LOG] [" + time + "] " + message);
    }

    @Override
    public String getTask(int taskId) {
        if (!authenticated) {
            return "Доступ запрещен: требуется авторизация";
        }
        log("Запрос задачи с ID: " + taskId);
        return realService.getTask(taskId);
    }

    @Override
    public String createTask(String task) {
        if (!authenticated) {
            return "Доступ запрещен: требуется авторизация";
        }
        log("Создание задачи: " + task);
        return realService.createTask(task);
    }

    @Override
    public boolean deleteTask(int taskId) {
        if (!authenticated) {
            log("Попытка удаления задачи без авторизации: ID " + taskId);
            return false;
        }
        log("Удаление задачи с ID: " + taskId);
        return realService.deleteTask(taskId);
    }
}