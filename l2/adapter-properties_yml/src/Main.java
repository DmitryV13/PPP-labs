import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        WebApplication webApplication = new WebApplication();

        WebApplicationAdapter adapter = new WebApplicationAdapter(webApplication);

        try {
            String propertiesFile = "resources/prop-files/name.properties";

            adapter.startApplication(propertiesFile);
            adapter.showApplicationProps();

            System.out.println("\n\n\n");
            System.out.println("Приложение запущено успешно!");
        } catch (IOException e) {
            System.err.println("Ошибка при запуске: " + e.getMessage());
        }
    }
}

// .properties LOADER
class PropertiesLoader {
    public Properties loadProperties(String filePath) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        }
        return properties;
    }
}

// INTERFACE FOR TARGET (USER)
interface WebApplicationAPI {
    void startApplication(String filePath) throws IOException;
    void showApplicationProps() throws IOException;
}

// ADAPTEE SERVICE
class WebApplication {
    String propertiesFilePath;

    WebApplication() {}

    void start(String filePath) throws IOException {
        propertiesFilePath = filePath;
        System.out.println("\n\n\n");
        System.out.println("==========================================================");
        System.out.println("==========================================================");
        System.out.println("====================|| STARTING APP ||====================");
        System.out.println("==========================================================");

        try (BufferedReader reader = new BufferedReader(new FileReader(propertiesFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("#")) { // Пропускаем пустые строки и комментарии
                    System.out.print(line + " || ");
                }
            }
            System.out.println();
        }

        System.out.println("==========================================================");

        LocalDateTime now = LocalDateTime.now();

        // Форматируем время в формате HH:mm:ss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        System.out.println("[LOG] [INFO] [" + now.format(formatter) + "] " +
                "----- Log 1-application successfully started");
        System.out.println("[LOG] [INFO] [" + now.format(formatter) + "] " +
                "----- Log 2-application performing some web actions");
        System.out.println("[LOG] [INFO] [" + now.format(formatter) + "] " +
                "----- Log 3-application performing some web actions");
        System.out.println("\n\n\n");
    }

    void showProperties() throws IOException
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(propertiesFilePath))) {
            System.out.println("==========================================================");
            System.out.println("=================|| APP PROPERTIES||======================");
            System.out.println("==========================================================");
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("#")) { // Пропускаем пустые строки и комментарии
                    System.out.println(line);
                }
            }
            System.out.println();
        }
    }
}

// ADAPTER FOR .yml
class WebApplicationAdapter implements WebApplicationAPI {
    private WebApplication webApplication;

    private PropertiesLoader propertiesReader;

    private String ymlFilesDirectory;

    public WebApplicationAdapter(WebApplication webApplication) {
        this.webApplication = webApplication;
        propertiesReader = new PropertiesLoader();
        ymlFilesDirectory = "resources/yml-files/";
    }

    public String writeToYaml(String filePath) throws IOException {
        Properties properties = propertiesReader.loadProperties(filePath);

        String yamlFileName = getFileNameWithoutExtension(filePath);
        String yamlFilePath = ymlFilesDirectory + yamlFileName + ".yaml";

        try (FileWriter writer = new FileWriter(yamlFilePath)) {
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                writer.write(key + ": " + value + "\n");
            }
        }
        return yamlFilePath;
    }

    public static String getFileNameWithoutExtension(String path) {
        File file = new File(path);
        String fileNameWithExt = file.getName();

        int lastDotIndex = fileNameWithExt.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileNameWithExt.substring(0, lastDotIndex);
        }
        return fileNameWithExt;
    }

    @Override
    public void startApplication(String filePath) throws IOException {
        String ymlFilePath = writeToYaml(filePath);
        webApplication.start(ymlFilePath);
    }

    @Override
    public void showApplicationProps() throws IOException {
        webApplication.showProperties();
    }
}