import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        String text = "Hi, my name is Dmitry, nice to meet you!";
        String[] images = new String[]{"myProfilePhoto", "myDog", "myCat", "myProfilePhoto"};

        Data dataToRender = new Data(text, images);

        Renderer renderer = new Renderer(dataToRender);

        renderer.prepareData();
        renderer.render();
    }
}

// DATA FOR RENDERING
class Data{
    private String text;
    private String[] images;

    Data(String text, String[] images){
        this.text = text;
        this.images = images;
    }

    public String getText() {
        return text;
    }

    public String[] getImages() {
        return images;
    }
}

// FLYWEIGHT INTERFACE
interface RenderingItemFlyweight {
    void display(int x, int y, String color, int fontSize, int rotation, int spaceNext); // Отображает символ с координатами
}

// FLYWEIGHT FOR CHARACTER
class CharacterFlyweight implements RenderingItemFlyweight {
    private char character; // Внутреннее состояние (символ)

    public CharacterFlyweight(char character) {
        this.character = character;
    }

    @Override
    public void display(int x, int y, String color, int fontSize, int rotation, int spaceNext) {
        System.out.println("Символ '" + character + "' отображен на позиции (" + x + ", " + y + ")" +
                ", цвета " + color + ", с размером шрифта " + fontSize + ", с углом поворота " +
                rotation + ", с расстоянием до следующего item " + spaceNext);
    }
}

// FLYWEIGHT FOR IMAGE
class ImageFlyweight implements RenderingItemFlyweight {
    private String texture; // Внутреннее состояние (символ)

    public ImageFlyweight(String texture) {
        this.texture = texture;
    }

    @Override
    public void display(int x, int y, String color, int size, int rotation, int spaceNext) {
        System.out.println("Картинка " + texture + " отображена на позиции (" + x + ", " + y + ")"+
                ", цвета " + color + ", с размером шрифта " + size + ", с углом поворота " +
                rotation + ", с расстоянием до следующего item " + spaceNext);
    }
}

//EXTERNAL STATE CLASS FOR RENDERING ITEM
class ExtrinsicRenderingItemState {
    private RenderingItemFlyweight flyweight;

    private int x;
    private int y;
    private String color;
    private int size;
    private int rotation;
    private int spaceNext;

    public ExtrinsicRenderingItemState(
            RenderingItemFlyweight flyweight,
            int x,
            int y,
            String color,
            int size,
            int rotation,
            int spaceNext
    ) {
        this.flyweight = flyweight;
        this.x = x;
        this.y = y;
        this.color = color;
        this.size = size;
        this.rotation = rotation;
        this.spaceNext = spaceNext;
    }

    public void display()
    {
        flyweight.display(x, y, color, size, rotation, spaceNext);
    }
}

// FLYWEIGHT FACTORY
class RenderingItemsFlyweightFactory {
    private HashMap<Character, CharacterFlyweight> flyweightsCharacters = new HashMap<>();
    private HashMap<String, ImageFlyweight> flyweightsImages = new HashMap<>();

    RenderingItemsFlyweightFactory() {}

    public CharacterFlyweight getFlyweightCharacter(char character) {
        if (!flyweightsCharacters.containsKey(character)) {
            System.out.println("Создан новый легковес для символа '" + character + "'");
            flyweightsCharacters.put(character, new CharacterFlyweight(character));
        } else {
            System.out.println("Возвращен существующий легковес для символа '" + character + "'");
        }
        return flyweightsCharacters.get(character);
    }

    public ImageFlyweight getFlyweightImage(String texture) {
        if (!flyweightsImages.containsKey(texture)) {
            System.out.println("Создан новый легковес для символа '" + texture + "'");
            flyweightsImages.put(texture, new ImageFlyweight(texture));
        } else {
            System.out.println("Возвращен существующий легковес для символа '" + texture + "'");
        }
        return flyweightsImages.get(texture);
    }
}

// CLASS FOR RENDERING DIFFERENT ITEMS
class Renderer {
    private RenderingItemsFlyweightFactory factory;
    private Data data;
    private ArrayList<ExtrinsicRenderingItemState> extrinsicRenderingItemStates = new ArrayList<>();

    public Renderer(Data data) {
        this.factory = new RenderingItemsFlyweightFactory();
        this.data = data;
    }

    public void prepareData()
    {
        prepareText();
        prepareImages();
    }

    public void render() {
        for (ExtrinsicRenderingItemState item : extrinsicRenderingItemStates) {
            item.display();
        }
    }

    private void prepareText() {
        String text = data.getText();

        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            CharacterFlyweight flyweight = factory.getFlyweightCharacter(character);
            // Внешнее состояние
            ExtrinsicRenderingItemState state = new ExtrinsicRenderingItemState(
                    flyweight,
                    i,
                    Math.max(7, i),
                    "Black",
                    14,
                    90,
                    5
            );
            extrinsicRenderingItemStates.add(state);
        }
    }

    private void prepareImages() {
        String[] textures = data.getImages();

        for (int i = 0; i < textures.length; i++) {
            String texture = textures[i];
            ImageFlyweight flyweight = factory.getFlyweightImage(texture);
            // Внешнее состояние
            ExtrinsicRenderingItemState state = new ExtrinsicRenderingItemState(
                    flyweight,
                    i,
                    300,
                    "Transparent",
                    200,
                    (int)(Math.random()*20),
                    10+i
            );
            extrinsicRenderingItemStates.add(state);
        }
    }
}