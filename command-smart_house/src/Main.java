import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        // Создаем устройства
        Light livingRoomLight = new Light();
        Thermostat thermostat = new Thermostat();

        // Создаем команды
        Command lightOn = new LightOnCommand(livingRoomLight);
        Command lightOff = new LightOffCommand(livingRoomLight);
        Command setTemp25 = new ThermostatSetCommand(thermostat, 25);
        Command setTemp18 = new ThermostatSetCommand(thermostat, 18);

        // Создаем контроллер
        SmartHomeController controller = new SmartHomeController();

        // Добавляем команды в очередь
        System.out.println("Adding commands to queue:");
        controller.addCommandToQueue(lightOn);      // Включаем свет
        controller.addCommandToQueue(setTemp25);    // Устанавливаем 25°C
        controller.addCommandToQueue(lightOff);     // Выключаем свет
        controller.addCommandToQueue(setTemp18);    // Устанавливаем 18°C

        // Удаляем следующую команду из очереди
        System.out.println("\nRemoving next command from queue:");
        controller.removeNextCommandFromQueue();

        // Ждем завершения выполнения всех команд
        try {
            Thread.sleep(15000); // Даем время на выполнение всех команд (4 команды * 3 сек + запас)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nUndoing commands:");
        controller.undoLastCommand();            // Отмена: температура 18°C
        controller.undoLastCommand();            // Отмена: свет выключен
        controller.undoLastCommand();            // Отмена: температура 25°C

        System.out.println("\nRedoing commands:");
        controller.redoCommand();                // Повтор: температура 25°C
        controller.redoCommand();                // Повтор: свет выключен
    }
}

// COMMAND INTERFACE
interface Command {
    void execute();
    void undo();
}

// RECIEVER
class Light {
    private boolean isOn = false;

    public void turnOn() {
        isOn = true;
        System.out.println("Light is ON");
    }

    public void turnOff() {
        isOn = false;
        System.out.println("Light is OFF");
    }

    public boolean isOn() {
        return isOn;
    }
}

// RECIEVER
class Thermostat {
    private int temperature = 20;

    public void setTemperature(int temp) {
        temperature = temp;
        System.out.println("Thermostat set to " + temp + "°C");
    }

    public int getTemperature() {
        return temperature;
    }
}

// CONCRETE COMMAND
class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn();
    }

    @Override
    public void undo() {
        light.turnOff();
    }
}

// CONCRETE COMMAND
class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOff();
    }

    @Override
    public void undo() {
        light.turnOn();
    }
}

// CONCRETE COMMAND
class ThermostatSetCommand implements Command {
    private Thermostat thermostat;
    private int newTemp;
    private int previousTemp;

    public ThermostatSetCommand(Thermostat thermostat, int temp) {
        this.thermostat = thermostat;
        this.newTemp = temp;
    }

    @Override
    public void execute() {
        previousTemp = thermostat.getTemperature();
        thermostat.setTemperature(newTemp);
    }

    @Override
    public void undo() {
        thermostat.setTemperature(previousTemp);
    }
}

// INVOKER
class SmartHomeController {
    private List<Command> commandHistory = new ArrayList<>();
    private int currentCommandIndex = -1;
    private Queue<Command> commandQueue = new LinkedList<>();
    private Thread commandProcessor;
    private boolean isRunning = true;

    public SmartHomeController() {
        // Запускаем поток для обработки очереди команд
        commandProcessor = new Thread(() -> {
            while (isRunning) {
                Command command = commandQueue.poll();
                if (command != null) {
                    executeCommand(command);
                    try {
                        Thread.sleep(3000); // Задержка 3 секунды перед следующей командой
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Command processor interrupted");
                    }
                } else {
                    try {
                        Thread.sleep(100); // Короткая пауза, если очередь пуста
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        commandProcessor.start();
    }

    public void addCommandToQueue(Command command) {
        System.out.println("Command added to queue");
        commandQueue.add(command);
    }

    private void executeCommand(Command command) {
        command.execute();
        // Удаляем команды после текущей позиции, если они есть
        if (currentCommandIndex < commandHistory.size() - 1) {
            commandHistory.subList(currentCommandIndex + 1, commandHistory.size()).clear();
        }
        commandHistory.add(command);
        currentCommandIndex++;
    }

    public void undoLastCommand() {
        if (currentCommandIndex >= 0) {
            commandHistory.get(currentCommandIndex).undo();
            currentCommandIndex--;
        } else {
            System.out.println("Nothing to undo");
        }
    }

    public void redoCommand() {
        if (currentCommandIndex < commandHistory.size() - 1) {
            currentCommandIndex++;
            commandHistory.get(currentCommandIndex).execute();
        } else {
            System.out.println("Nothing to redo");
        }
    }

    public void shutdown() {
        isRunning = false;
        if (commandProcessor != null) {
            commandProcessor.interrupt();
        }
    }

    public void removeNextCommandFromQueue() {
        if (!commandQueue.isEmpty()) {
            Command removedCommand = commandQueue.poll();
            System.out.println("Removed next command from queue: " + removedCommand.getClass().getSimpleName());
        } else {
            System.out.println("Queue is empty, nothing to remove");
        }
    }
}