import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите тип службы для отправки сообщения:");
        System.out.println("1 - SMS служба");
        System.out.println("2 - Email служба");

        int choice = scanner.nextInt();
        scanner.nextLine();

        MessageManager messageManager;

        if (choice == 1) {
            messageManager = new SMSMessageManager();
            messageManager.setProperty("signalStrength", 3);

        } else if (choice == 2) {
            messageManager = new EmailMessageManager();
            messageManager.setProperty("smtpConnectionEstablished", 1);
        } else {
            System.out.println("Неверный выбор!");
            return;
        }

        messageManager.addMessage("last message!");
        messageManager.addMessage("Hello World!");
        messageManager.addMessage("long time no see you! how are you?");

        MessageSender sender = messageManager.createMessageSender();

        if(sender != null && sender.prepared()) {
            sender.send(messageManager.getMessage());
        }
    }
}

// MESSAGE SENDER
abstract class MessageSender {
    protected HashMap<String, String> properties = new HashMap<>();

    abstract void send(String message);

    abstract boolean prepared();

    public void setProperty(String key, String value) {
        properties.put(key, value);
    }
}

// MESSAGE SENDER FOR SMS
class SMSMessageSender extends MessageSender {

    @Override
    public void send(String message) {
        if(!prepared()) {
            System.out.println("------------------------------------");
            System.out.println("Phone number is empty!!!");
            return;
        }
        System.out.println("------------------------------------");
        System.out.println("Сообщение доставить по номеру: " + properties.get("phoneNumber"));
        System.out.println("Отправка SMS: " + message);
    }

    @Override
    public boolean prepared() {
        return properties.get("phoneNumber") != null;
    }

    SMSMessageSender(HashMap<String, String> properties) {
        this.properties = properties;
    }
}

// MESSAGE SENDER FOR EMAIL
class EmailMessageSender extends MessageSender {

    @Override
    public void send(String message) {
        if(!prepared()) {
            System.out.println("------------------------------------");
            System.out.println("Properties are not prepared!!!");
            return;
        }
        System.out.println("------------------------------------");
        System.out.println("Сообщение от: " + properties.get("from"));
        System.out.println("Кому: " + properties.get("to"));
        System.out.println("Отправка Email: " + message);
    }

    @Override
    public boolean prepared() {
        return properties.get("from") != null && properties.get("to") != null;
    }

    EmailMessageSender(HashMap<String, String> properties) {
        this.properties = properties;
    }
}

// MESSAGE MANAGER
abstract class MessageManager {
    protected List<String> messages = new ArrayList<>();
    protected HashMap<String, Integer> properties = new HashMap<>();

    public abstract MessageSender createMessageSender();

    public String processMessage(int index) {
        return messages.get(index).trim().toLowerCase();
    }

    public void processMessageInplace(int index) {
        messages.set(index, messages.get(index).trim().toLowerCase());
    }

    public boolean isMessageValid(String message) {
        return message != null && !message.isEmpty();
    }

    public boolean isMessageValid(int index) {
        return messages.get(index) != null && !messages.get(index).isEmpty();
    }

    public void setProperty(String key, int value) {
        properties.put(key, value);
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public String getMessage() {
        if(!prepareMessageForSending()) {
            return "";
        }
        return messages.get(messages.size() - 1);
    }

    private boolean prepareMessageForSending() {
        String processedMessage = processMessage(messages.size()-1);

        if (isMessageValid(processedMessage)) {
            System.out.println("------------------------------------");
            System.out.println("Сообщение подготовлено.");
            return true;
        } else {
            System.out.println("------------------------------------");
            System.out.println("Ошибка: Сообщение некорректно.");
            return false;
        }
    }
}

// MESSAGE MANAGER FOR SMS
class SMSMessageManager extends MessageManager {

    @Override
    public MessageSender createMessageSender() {
        if(properties.get("signalStrength") < 2) {
            System.out.println("ATTENTION!!! Signal is too weak.");
        }
        HashMap<String, String> senderProperties = new HashMap<>();
        senderProperties.put("phoneNumber", "079461826");
        return properties.get("signalStrength") > 1 ? new SMSMessageSender(senderProperties) : null;
    }
}

// MESSAGE MANAGER FOR EMAIL
class EmailMessageManager extends MessageManager {
    @Override
    public MessageSender createMessageSender() {
        if(properties.get("smtpConnectionEstablished")<1) {
            System.out.println("ATTENTION!!! Connection not established.");
        }
        HashMap<String, String> senderProperties = new HashMap<>();
        senderProperties.put("from", "TestServiceMail@mail.ru");
        senderProperties.put("to", "TestUserMail@mail.ru");
        return properties.get("smtpConnectionEstablished")>0 ? new EmailMessageSender(senderProperties) : null;
    }
}

