import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите уровень игры:");
        System.out.println("1 - Горная местность");
        System.out.println("2 - Летняя местность");
        System.out.print("Введите номер уровня: ");

        int choice = scanner.nextInt();

        EquipmentFactory factory;

        if (choice == 1) {
            factory = new MountainFactory();
        } else if (choice == 2) {
            factory = new SummerFactory();
        } else {
            System.out.println("Некорректный выбор! Программа завершена.");
            scanner.close();
            return;
        }
        System.out.println();

        Player player = new Player(factory);
        player.performOneMoveAndShot();

        scanner.close();
    }
}

interface Transport {
    void move();
}

interface Weapon {
    void use();
}

interface Clothes {
    void show();
}

class WinterClothes implements Clothes {
    @Override
    public void show() {
        System.out.println("ИСПОЛЬЗУЕТСЯ ОДЕЖДА ДЛЯ ЗАСНЕЖЕННОЙ МЕСТНОСТИ!!!");
        System.out.println("---пуховик и штаны из шерсти лисы---");
    }
}

class SummerClothes implements Clothes {
    @Override
    public void show() {
        System.out.println("ИСПОЛЬЗУЕТСЯ ОДЕЖДА ДЛЯ ТЕПЛОЙ МЕСТНОСТИ!!!");
        System.out.println("---футболка и шорты---");
    }
}

class Snowmobile implements Transport {
    @Override
    public void move() {
        System.out.println("ИСПОЛЬЗУЕТСЯ ТРАНСПОРТ ДЛЯ ПЕРЕМЕЩЕНИЯ ПО СНЕГУ!!!");
        System.out.println("---снегоход едет по снегу---");
    }
}

class Pickup implements Transport {
    @Override
    public void move() {
        System.out.println("ИСПОЛЬЗУЕТСЯ ТРАНСПОРТ ДЛЯ ПЕРЕСЕЧЕННОЙ МЕСТНОСТИ!!!");
        System.out.println("---пикап едет по пляжу---");
    }
}

class MountainRifle implements Weapon {
    @Override
    public void use() {
        System.out.println("ВНИМАНИЕ!!! ИСПОЛЬЗУЕТСЯ ОРУЖИЕ ДАЛЬНЕГО БОЯ!!!");
        System.out.println("---использовано горное ружье на открытой местности---");
    }
}

class MachineGun implements Weapon {
    @Override
    public void use() {
        System.out.println("ВНИМАНИЕ!!! ИСПОЛЬЗУЕТСЯ ОРУЖИЕ НА СРЕДНЕЙ ДИСТАНЦИИ!!!");
        System.out.println("---использован пулемет---");
    }
}


interface EquipmentFactory {
    Transport createTransport();
    Weapon createWeapon();
    Clothes createClothes();
}


class MountainFactory implements EquipmentFactory {
    @Override
    public Transport createTransport() {
        return new Snowmobile();
    }

    @Override
    public Weapon createWeapon() {
        return new MountainRifle();
    }

    @Override
    public Clothes createClothes() {
        return new WinterClothes();
    }
}

class SummerFactory implements EquipmentFactory {
    @Override
    public Transport createTransport() {
        return new Pickup();  // Или можно вернуть SummerCar
    }

    @Override
    public Weapon createWeapon() {
        return new MachineGun();
    }

    @Override
    public Clothes createClothes() {
        return new SummerClothes();
    }
}

class Player {
    private Transport transport;
    private Weapon weapon;
    private Clothes clothes;

    public Player(EquipmentFactory factory) {
        this.transport = factory.createTransport();
        this.weapon = factory.createWeapon();
        this.clothes = factory.createClothes();
    }

    public void performOneMoveAndShot() {
        clothes.show();
        transport.move();
        weapon.use();
    }
}

