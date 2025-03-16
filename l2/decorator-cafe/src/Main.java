

public class Main {
    public static void main(String[] args) {
        Picture naturePicture = new NaturePicture();

        System.out.println("Картина: " + naturePicture.draw());
        System.out.println("\n PRICE: " + naturePicture.showPrice());
        System.out.println();

        // Применяем декораторы поэтапно
        naturePicture = new ForestDecorator(naturePicture);

        System.out.println("Картина: " + naturePicture.draw());
        System.out.println("\n PRICE: " + naturePicture.showPrice());
        System.out.println();

        naturePicture = new FlowerDecorator(naturePicture);

        System.out.println("Картина: " + naturePicture.draw());
        System.out.println("\n PRICE: " + naturePicture.showPrice());
        System.out.println();

        naturePicture = new RabbitDecorator(naturePicture);

        System.out.println("Картина: " + naturePicture.draw());
        System.out.println("\n PRICE: " + naturePicture.showPrice());
        System.out.println();
    }
}

// COMPONENT - PICTURE
abstract class Picture {
    String description="";
    public int price;

    Picture(){}

    public abstract String draw();
    public abstract int showPrice();
}

// CONCRETE COMPONENT - NATURE PICTURE
class NaturePicture extends Picture {

    NaturePicture() {
        description="Simple field picture";
        price=5;
    }

    @Override
    public String draw() {
        return description;
    }

    @Override
    public int showPrice() {
        return price;
    }
}

// BASE DECORATOR
class BaseDecorator extends Picture {
    Picture picture;

    BaseDecorator(Picture picture) {
        this.picture = picture;
    }

    @Override
    public String draw() {
        return picture.draw();
    }

    @Override
    public int showPrice() {
        return picture.showPrice();
    }
}

// DECORATOR FOR FOREST
class ForestDecorator extends BaseDecorator {
    public ForestDecorator(Picture picture) {
        super(picture);
    }

    @Override
    public String draw() {
        return super.draw() + ", with forest \n" + drawForest();
    }

    private String drawForest()
    {
        return  "   ^  ^  ^   \n" +
                "  ^^^ ^^ ^^^  \n" +
                " ^^^^ ^ ^^^^ \n" +
                "   ||  ||  ||   \n";
    }

    @Override
    public int showPrice() {
        return super.showPrice() + 10;
    }
}

// DECORATOR FOR FLOWERS
class FlowerDecorator extends BaseDecorator {
    public FlowerDecorator(Picture picture) {
        super(picture);
    }

    @Override
    public String draw() {
        return super.draw() + ", with flowers \n" + drawFlowers();
    }

    private String drawFlowers() {
        return  "  @  @  @  \n" +
                "   |   |   |  \n" +
                "  @  @  @  \n" +
                "   |   |   |  \n";
    }

    @Override
    public int showPrice() {
        return super.showPrice() + 5;
    }
}

// DECORATOR FOR RABBITS
class RabbitDecorator extends BaseDecorator {
    public RabbitDecorator(Picture picture) {
        super(picture);
    }

    @Override
    public String draw() {
        return super.draw() + ", with rabbits \n" + drawRabbits();
    }

    private String drawRabbits() {
        return  "(\\(\\   (\\(\\  \n" +
                "( -.-)  (o.o) \n" +
                "o_(\")(\")  ( (\")(\") \n";
    }

    @Override
    public int showPrice() {
        return super.showPrice() + 7; // Зайцы добавляют 7 к цене
    }
}
