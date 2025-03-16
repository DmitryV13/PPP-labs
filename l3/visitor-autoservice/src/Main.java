import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Car(1.6));
        vehicles.add(new Truck(8.0));
        vehicles.add(new Motorcycle(false));
        vehicles.add(new Car(3.0));
        vehicles.add(new Motorcycle(true));

        MaintenanceCostVisitor costVisitor = new MaintenanceCostVisitor();
        InspectionTimeVisitor timeVisitor = new InspectionTimeVisitor();

        // Расчет стоимости обслуживания
        System.out.println("Calculating maintenance costs for all vehicles:");
        for (Vehicle vehicle : vehicles) {
            vehicle.accept(costVisitor);
        }
        System.out.printf("\nTotal maintenance cost for all vehicles: $%.2f%n", costVisitor.getTotalCost());

        // Расчет времени осмотра для всех транспортных средств
        System.out.println("\nCalculating inspection times for all vehicles:");
        for (Vehicle vehicle : vehicles) {
            vehicle.accept(timeVisitor);
        }
        System.out.printf("\nTotal inspection time for all vehicles: %.1f hours%n", timeVisitor.getTotalTime());

        // Дополнительно: Применяем посетителей только к мотоциклам
        System.out.println("\nCalculating costs and times for motorcycles only:");
        costVisitor = new MaintenanceCostVisitor(); // Сбрасываем предыдущие результаты
        timeVisitor = new InspectionTimeVisitor();
        for (Vehicle vehicle : vehicles) {
            if (vehicle instanceof Motorcycle) {
                vehicle.accept(costVisitor);
                vehicle.accept(timeVisitor);
            }
        }
        System.out.printf("\nTotal maintenance cost for motorcycles: $%.2f%n", costVisitor.getTotalCost());
        System.out.printf("Total inspection time for motorcycles: %.1f hours%n", timeVisitor.getTotalTime());
    }
}

// ELEMENT INTERFACE
interface Vehicle {
    void accept(VehicleVisitor visitor);
}

// CONCRETE ELEMENT
class Car implements Vehicle {
    private int wheelCount = 4;
    private double engineVolume; // в литрах

    public Car(double engineVolume) {
        this.engineVolume = engineVolume;
    }

    public int getWheelCount() { return wheelCount; }
    public double getEngineVolume() { return engineVolume; }

    @Override
    public void accept(VehicleVisitor visitor) {
        visitor.visit(this);
    }
}

// CONCRETE ELEMENT
class Truck implements Vehicle {
    private int wheelCount = 6;
    private double cargoCapacity; // в тоннах

    public Truck(double cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    public int getWheelCount() { return wheelCount; }
    public double getCargoCapacity() { return cargoCapacity; }

    @Override
    public void accept(VehicleVisitor visitor) {
        visitor.visit(this);
    }
}

// CONCRETE ELEMENT
class Motorcycle implements Vehicle {
    private int wheelCount = 2;
    private boolean hasSidecar;

    public Motorcycle(boolean hasSidecar) {
        this.hasSidecar = hasSidecar;
    }

    public int getWheelCount() { return wheelCount; }
    public boolean hasSidecar() { return hasSidecar; }

    @Override
    public void accept(VehicleVisitor visitor) {
        visitor.visit(this);
    }
}

// VISITOR INTERFACE
interface VehicleVisitor {
    void visit(Car car);
    void visit(Truck truck);
    void visit(Motorcycle motorcycle);
}

// VISITOR FOR COST CALCULATION
class MaintenanceCostVisitor implements VehicleVisitor {
    private double totalCost;

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public void visit(Car car) {
        // Базовая стоимость + стоимость за объем двигателя + за колеса
        double cost = 50.0 + (car.getEngineVolume() * 20.0) + (car.getWheelCount() * 10.0);
        totalCost += cost;
        System.out.printf("Car maintenance cost: $%.2f (Engine: %.1fL, Wheels: %d)%n",
                cost, car.getEngineVolume(), car.getWheelCount());
    }

    @Override
    public void visit(Truck truck) {
        // Базовая стоимость + стоимость за грузоподъемность + за колеса
        double cost = 100.0 + (truck.getCargoCapacity() * 30.0) + (truck.getWheelCount() * 15.0);
        totalCost += cost;
        System.out.printf("Truck maintenance cost: $%.2f (Cargo: %.1ft, Wheels: %d)%n",
                cost, truck.getCargoCapacity(), truck.getWheelCount());
    }

    @Override
    public void visit(Motorcycle motorcycle) {
        // Базовая стоимость + доплата за коляску + за колеса
        double cost = 30.0 + (motorcycle.hasSidecar() ? 20.0 : 0.0) + (motorcycle.getWheelCount() * 8.0);
        totalCost += cost;
        System.out.printf("Motorcycle maintenance cost: $%.2f (Sidecar: %b, Wheels: %d)%n",
                cost, motorcycle.hasSidecar(), motorcycle.getWheelCount());
    }
}

// VISITOR FOR TIME CALCULATION
class InspectionTimeVisitor implements VehicleVisitor {
    private double totalTime; // в часах

    public double getTotalTime() {
        return totalTime;
    }

    @Override
    public void visit(Car car) {
        // Базовое время + время на двигатель + на колеса
        double time = 1.0 + (car.getEngineVolume() * 0.5) + (car.getWheelCount() * 0.25);
        totalTime += time;
        System.out.printf("Car inspection time: %.1f hours (Engine: %.1fL, Wheels: %d)%n",
                time, car.getEngineVolume(), car.getWheelCount());
    }

    @Override
    public void visit(Truck truck) {
        // Базовое время + время на грузоподъемность + на колеса
        double time = 2.0 + (truck.getCargoCapacity() * 0.3) + (truck.getWheelCount() * 0.4);
        totalTime += time;
        System.out.printf("Truck inspection time: %.1f hours (Cargo: %.1ft, Wheels: %d)%n",
                time, truck.getCargoCapacity(), truck.getWheelCount());
    }

    @Override
    public void visit(Motorcycle motorcycle) {
        // Базовое время + доп время за коляску + на колеса
        double time = 0.5 + (motorcycle.hasSidecar() ? 0.5 : 0.0) + (motorcycle.getWheelCount() * 0.2);
        totalTime += time;
        System.out.printf("Motorcycle inspection time: %.1f hours (Sidecar: %b, Wheels: %d)%n",
                time, motorcycle.hasSidecar(), motorcycle.getWheelCount());
    }
}
