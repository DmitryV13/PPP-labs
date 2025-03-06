import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        WeatherStation station = new WeatherStation();

        TemperatureDisplay indoorDisplay = new TemperatureDisplay("Indoor Display");
        TemperatureDisplay outdoorDisplay = new TemperatureDisplay("Outdoor Display");
        TemperatureLogger logger = new TemperatureLogger();
        HumidityMonitor humidityMonitor = new HumidityMonitor();

        // Подписываем наблюдателей
        station.addObserver(indoorDisplay);
        station.addObserver(outdoorDisplay);
        station.addObserver(logger);
        station.addObserver(humidityMonitor);

        // Имитируем изменения
        station.setTemperature(22.5f);
        System.out.println();

        station.setHumidity(60.0f);
        System.out.println();

        station.setTemperature(18.0f);
        System.out.println();

        station.setTemperature(18.0f);
        System.out.println();

        station.setHumidity(55.0f);
        System.out.println();

        System.out.println("\nRemoving Outdoor Display...");
        station.removeObserver(outdoorDisplay);

        station.setTemperature(25.0f);
        System.out.println();

        station.setHumidity(65.0f);
        System.out.println();

    }
}

// OBSERVER INTERFACE
interface WeatherObserver {
    void update(WeatherSubject subject);
}

// SUBJECT INTERFACE
interface WeatherSubject {
    void addObserver(WeatherObserver observer);
    void removeObserver(WeatherObserver observer);
    void notifyObservers();
    float getTemperature();
    float getHumidity();
}

// CONCRETE SUBJECT
class WeatherStation implements WeatherSubject {
    private List<WeatherObserver> observers;
    private float temperature;
    private float humidity;

    public WeatherStation() {
        this.observers = new ArrayList<>();
        this.temperature = 20.0f;
        this.humidity = 50.0f;
    }

    @Override
    public void addObserver(WeatherObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(WeatherObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (WeatherObserver observer : observers) {
            observer.update(this);
        }
    }

    public void setTemperature(float newTemperature) {
        this.temperature = newTemperature;
        System.out.println("Subject - Temperature changed to: " + temperature + "°C");
        notifyObservers();
    }

    public void setHumidity(float newHumidity) {
        this.humidity = newHumidity;
        System.out.println("Subject - Humidity changed to: " + humidity + "%");
        notifyObservers();
    }

    @Override
    public float getTemperature() {
        return temperature;
    }

    @Override
    public float getHumidity() {
        return humidity;
    }
}

// CONCRETE OBSERVER
class TemperatureDisplay implements WeatherObserver {
    private String displayName;

    public TemperatureDisplay(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public void update(WeatherSubject subject) {
        if (subject instanceof WeatherStation) {
            WeatherStation sensor = (WeatherStation) subject;
            float temperature = sensor.getTemperature();
            float humidity = sensor.getHumidity();
            System.out.println("Observer - " + displayName + " shows: Temperature = " + temperature);
        }
    }
}

// CONCRETE OBSERVER
class TemperatureLogger implements WeatherObserver {
    private float lastLoggedTemperature = Float.NaN;

    @Override
    public void update(WeatherSubject subject) {
        if (subject instanceof WeatherStation) {
            float currentTemperature = ((WeatherStation) subject).getTemperature();
            if (currentTemperature != lastLoggedTemperature) {
                System.out.println("Observer - Logging temperature: " + currentTemperature + "°C");
                lastLoggedTemperature = currentTemperature;
            }
        }
    }
}

// CONCRETE OBSERVER
class HumidityMonitor implements WeatherObserver {
    private float lastMonitoredHumidity = Float.NaN;

    @Override
    public void update(WeatherSubject subject) {
        if (subject instanceof WeatherStation) {
            float currentHumidity = ((WeatherStation) subject).getHumidity();
            if (currentHumidity != lastMonitoredHumidity) {
                System.out.println("Observer - Monitoring humidity: " + currentHumidity + "%");
                lastMonitoredHumidity = currentHumidity;
            }
        }
    }
}