import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        OrderDirector director = new OrderDirector();

        //LUNCH ITEM
        LunchBuilder lunchBuilder = new LunchBuilder();
        director.constructBusinessLunch(lunchBuilder);
        Lunch businessLunch = lunchBuilder.getLunch();
        businessLunch.showLunch();

        // LUNCH BILL
        BillBuilder billBuilder = new BillBuilder();
        director.constructBusinessBill(billBuilder);
        Bill bill = billBuilder.getBill();
        bill.showBill();
    }
}

class PriceList {
    int bread = 5;
    int salt = 1;
    int pepper = 1;
    HashMap<FIRST_MEAL, Integer> fm = new HashMap<>();
    HashMap<SECOND_MEAL, Integer> sm = new HashMap<>();
    HashMap<THIRD_MEAL, Integer> tm = new HashMap<>();
    HashMap<ALCOHOL, Integer> alcohol = new HashMap<>();
    HashMap<DESSERT, Integer> desert = new HashMap<>();

    PriceList(){
        // Заполнение цен для FIRST_MEAL
        fm.put(FIRST_MEAL.BORSHCH, 20);
        fm.put(FIRST_MEAL.CHICKEN_SOUP, 18);
        fm.put(FIRST_MEAL.MUSHROOM_SOUP, 22);
        fm.put(FIRST_MEAL.FISH_SOUP, 25);
        fm.put(FIRST_MEAL.VEGETABLE_SOUP, 15);
        fm.put(FIRST_MEAL.TOMATO_SOUP, 17);
        fm.put(FIRST_MEAL.NOTHING, 0);

        // Заполнение цен для SECOND_MEAL
        sm.put(SECOND_MEAL.STEAK, 50);
        sm.put(SECOND_MEAL.CHICKEN_CUTLET, 35);
        sm.put(SECOND_MEAL.FISH_FILET, 40);
        sm.put(SECOND_MEAL.VEGETABLE_STIR_FRY, 25);
        sm.put(SECOND_MEAL.PASTA, 30);
        sm.put(SECOND_MEAL.NOTHING, 0);

        // Заполнение цен для THIRD_MEAL
        tm.put(THIRD_MEAL.PASTRY, 12);
        tm.put(THIRD_MEAL.CHEESECAKE, 15);
        tm.put(THIRD_MEAL.NOTHING, 0);

        // Заполнение цен для ALCOHOL
        alcohol.put(ALCOHOL.WINE, 35);
        alcohol.put(ALCOHOL.BEER, 10);
        alcohol.put(ALCOHOL.VODKA, 50);
        alcohol.put(ALCOHOL.WHISKEY, 60);
        alcohol.put(ALCOHOL.NOTHING, 0);

        // Заполнение цен для DESERT
        desert.put(DESSERT.ICE_CREAM, 8);
        desert.put(DESSERT.CAKE, 12);
        desert.put(DESSERT.PIE, 10);
        desert.put(DESSERT.CUPCAKE, 9);
        desert.put(DESSERT.BROWNIE, 11);
        desert.put(DESSERT.NOTHING, 0);
    }
}

enum FIRST_MEAL {
    BORSHCH,
    CHICKEN_SOUP,
    MUSHROOM_SOUP,
    FISH_SOUP,
    VEGETABLE_SOUP,
    TOMATO_SOUP,
    NOTHING
}

enum SECOND_MEAL {
    STEAK,
    CHICKEN_CUTLET,
    FISH_FILET,
    VEGETABLE_STIR_FRY,
    PASTA,
    NOTHING
}

enum THIRD_MEAL {
    PASTRY,
    CHEESECAKE,
    NOTHING
}

enum ALCOHOL {
    WINE,
    BEER,
    VODKA,
    WHISKEY,
    NOTHING
}

enum DESSERT {
    ICE_CREAM,
    CAKE,
    PIE,
    CUPCAKE,
    BROWNIE,
    NOTHING
}



class Lunch {
    private int bread;
    private boolean salt;
    private boolean pepper;
    private FIRST_MEAL firstMeal;
    private SECOND_MEAL secondMeal;
    private THIRD_MEAL thirdMeal;
    private DESSERT dessert;
    private ALCOHOL alcohol;

    public Lunch(int bread, ALCOHOL alcohol, DESSERT dessert, THIRD_MEAL thirdMeal, SECOND_MEAL secondMeal, FIRST_MEAL firstMeal, boolean pepper, boolean salt) {
        this.bread = bread;
        this.alcohol = alcohol;
        this.dessert = dessert;
        this.thirdMeal = thirdMeal;
        this.secondMeal = secondMeal;
        this.firstMeal = firstMeal;
        this.pepper = pepper;
        this.salt = salt;
    }

    public void showLunch() {
        System.out.println("\n=== Ваш ланч ===");
        System.out.println("Хлеб: " + (bread > 0 ? bread + " кусочков" : "Нет хлеба"));
        System.out.println("Соль: " + (salt ? "Да" : "Нет"));
        System.out.println("Перец: " + (pepper ? "Да" : "Нет"));
        System.out.println("Первое блюдо: " + firstMeal);
        System.out.println("Второе блюдо: " + secondMeal);
        System.out.println("Третье блюдо: " + thirdMeal);
        System.out.println("Десерт: " + dessert);
        System.out.println("Алкоголь: " + alcohol);
    }
}


class Bill {
    private int bread;
    private boolean salt;
    private boolean pepper;
    private FIRST_MEAL firstMeal;
    private SECOND_MEAL secondMeal;
    private THIRD_MEAL thirdMeal;
    private DESSERT dessert;
    private ALCOHOL alcohol;
    private PriceList priceList;

    public Bill(int bread, ALCOHOL alcohol, DESSERT dessert, THIRD_MEAL thirdMeal, SECOND_MEAL secondMeal, FIRST_MEAL firstMeal, boolean pepper, boolean salt) {
        this.bread = bread;
        this.alcohol = alcohol;
        this.dessert = dessert;
        this.thirdMeal = thirdMeal;
        this.secondMeal = secondMeal;
        this.firstMeal = firstMeal;
        this.pepper = pepper;
        this.salt = salt;
        this.priceList = new PriceList();
    }

    public void showBill() {
        int total = 0;

        System.out.println("\n=== Ваш чек ===");
        System.out.println("Хлеб: " + bread + " кусочков - " + (bread * priceList.bread) + " рублей");
        total += bread * priceList.bread;
        if (salt) {
            System.out.println("Соль: Да - " + priceList.salt + " рублей");
            total += priceList.salt;
        }
        if (pepper) {
            System.out.println("Перец: Да - " + priceList.pepper + " рублей");
            total += priceList.pepper;
        }
        System.out.println("Первое блюдо: " + firstMeal + " - " + priceList.fm.get(firstMeal) + " рублей");
        total += priceList.fm.get(firstMeal);
        System.out.println("Второе блюдо: " + secondMeal + " - " + priceList.sm.get(secondMeal) + " рублей");
        total += priceList.sm.get(secondMeal);
        System.out.println("Третье блюдо: " + thirdMeal + " - " + priceList.tm.get(thirdMeal) + " рублей");
        total += priceList.tm.get(thirdMeal);
        System.out.println("Десерт: " + dessert + " - " + priceList.desert.get(dessert) + " рублей");
        total += priceList.desert.get(dessert);
        System.out.println("Алкоголь: " + alcohol + " - " + priceList.alcohol.get(alcohol) + " рублей");
        total += priceList.alcohol.get(alcohol);
        System.out.println("\nИтого: " + total + " рублей");
    }
}

// COMMON BUILDER
interface OrderBuilder {
    void bread(int bread);
    void salt(boolean salt);
    void pepper(boolean pepper);
    void firstMeal(FIRST_MEAL firstMeal);
    void secondMeal(SECOND_MEAL secondMeal);
    void thirdMeal(THIRD_MEAL thirdMeal);
    void desert(DESSERT dessert);
    void alcohol(ALCOHOL alcohol);
}

// LUNCH BUILDER
class LunchBuilder implements OrderBuilder {
    private int bread;
    private boolean salt;
    private boolean pepper;
    private FIRST_MEAL firstMeal;
    private SECOND_MEAL secondMeal;
    private THIRD_MEAL thirdMeal;
    private DESSERT dessert;
    private ALCOHOL alcohol;


    @Override
    public void bread(int bread) {
        this.bread = bread;
    }

    @Override
    public void salt(boolean salt) {
        this.salt = salt;
    }

    @Override
    public void pepper(boolean pepper) {
        this.pepper = pepper;
    }

    @Override
    public void firstMeal(FIRST_MEAL firstMeal) {
        this.firstMeal = firstMeal;
    }

    @Override
    public void secondMeal(SECOND_MEAL secondMeal) {
        this.secondMeal = secondMeal;
    }

    @Override
    public void thirdMeal(THIRD_MEAL thirdMeal) {
        this.thirdMeal = thirdMeal;
    }

    @Override
    public void desert(DESSERT dessert) {
        this.dessert = dessert;
    }

    @Override
    public void alcohol(ALCOHOL alcohol) {
        this.alcohol = alcohol;
    }

    public Lunch getLunch(){
        return new Lunch(bread, alcohol, dessert, thirdMeal, secondMeal, firstMeal, pepper, salt);
    }
}

// BILL BUILDER
class BillBuilder implements OrderBuilder {
    private int bread;
    private boolean salt;
    private boolean pepper;
    private FIRST_MEAL firstMeal;
    private SECOND_MEAL secondMeal;
    private THIRD_MEAL thirdMeal;
    private DESSERT dessert;
    private ALCOHOL alcohol;

    @Override
    public void bread(int bread) {
        this.bread = bread;
    }

    @Override
    public void salt(boolean salt) {
        this.salt = salt;
    }

    @Override
    public void pepper(boolean pepper) {
        this.pepper = pepper;
    }

    @Override
    public void firstMeal(FIRST_MEAL firstMeal) {
        this.firstMeal = firstMeal;
    }

    @Override
    public void secondMeal(SECOND_MEAL secondMeal) {
        this.secondMeal = secondMeal;
    }

    @Override
    public void thirdMeal(THIRD_MEAL thirdMeal) {
        this.thirdMeal = thirdMeal;
    }

    @Override
    public void desert(DESSERT dessert) {
        this.dessert = dessert;
    }

    @Override
    public void alcohol(ALCOHOL alcohol) {
        this.alcohol = alcohol;
    }

    public Bill getBill(){
        return new Bill(bread, alcohol, dessert, thirdMeal, secondMeal, firstMeal, pepper, salt);
    }
}


//DIRECTOR
class OrderDirector {
    public void constructBusinessLunch(OrderBuilder builder) {
        builder.bread(5);
        builder.salt(true);
        builder.pepper(true);
        builder.firstMeal(FIRST_MEAL.CHICKEN_SOUP);
        builder.secondMeal(SECOND_MEAL.STEAK);
        builder.thirdMeal(THIRD_MEAL.CHEESECAKE);
        builder.desert(DESSERT.BROWNIE);
        builder.alcohol(ALCOHOL.WHISKEY);
    }

    public void constructBusinessBill(OrderBuilder builder) {
        builder.bread(5);
        builder.salt(true);
        builder.pepper(true);
        builder.firstMeal(FIRST_MEAL.CHICKEN_SOUP);
        builder.secondMeal(SECOND_MEAL.STEAK);
        builder.thirdMeal(THIRD_MEAL.CHEESECAKE);
        builder.desert(DESSERT.BROWNIE);
        builder.alcohol(ALCOHOL.WHISKEY);
    }
}

