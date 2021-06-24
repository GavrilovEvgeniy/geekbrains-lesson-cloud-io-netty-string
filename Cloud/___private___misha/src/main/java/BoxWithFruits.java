import java.util.ArrayList;

public class BoxWithFruits {

    static ArrayList<Fruit> box1 = new ArrayList<Fruit>();
    static ArrayList<Fruit> box2 = new ArrayList<Fruit>();
    static ArrayList<Fruit> currentbox = new ArrayList<Fruit>();

    private static void putFruitInBox(Fruit someFruit, ArrayList<Fruit> box) {

        if (box.isEmpty()) {
            box.add(someFruit);

            System.out.println("В пустую коробку кладем " + someFruit + ".");

        } else {
            String boxType = box.get(0).type;
            if (someFruit.type.equals(boxType)) {
                box.add(someFruit);

                System.out.println("В коробку " + boxType + " кладем " + someFruit + ".");

            } else System.out.println("Ошибка! Эта коробка для " + boxType + ".");
        }
    }

    private static float getWeight(ArrayList<Fruit> box) {
        float oneWeight = box.get(0).weight;
        return oneWeight * box.size();
    }

    private static boolean compareBoxWithCurrent(ArrayList<Fruit> box) {
        float weight1 = getWeight(box);
        float weight2 = getWeight(currentbox);
        return (weight1 == weight2);
    }

    private static void throwFruitsFromCurrentBox(ArrayList<Fruit> bbox) {

        if (currentbox.isEmpty()) {
            System.out.println("Текущая коробка пуста.");
            return;
        }

        String currentType = currentbox.get(0).type;
        String newType = bbox.get(0).type;

        if ((currentType.equals(newType)) || (bbox.isEmpty())) {
            while (!currentbox.isEmpty())
            {
                bbox.add(currentbox.get(0));
                System.out.println("Перенесли " + currentbox.get(0) + ".");
                currentbox.remove(0);
                System.out.println("В текущей коробке остаются " + currentbox + ".");
                System.out.println("В наполняемой коробке " + bbox + ".");
            }
        }
    }

    public static void main(String[] args) {
        Fruit a1 = new Apple();
        Fruit a2 = new Apple();
        Fruit a3 = new Apple();

        Fruit o1 = new Orange();
        Fruit o2 = new Orange();
        Fruit o3 = new Orange();
        Fruit o4 = new Orange();

        putFruitInBox(a1, box1);
        putFruitInBox(a2, box1);
        putFruitInBox(o1, box1);
        putFruitInBox(a3, box1);

        putFruitInBox(o1, box2);
        putFruitInBox(o2, box2);
        putFruitInBox(o3, box2);
        putFruitInBox(a1, box2);
        putFruitInBox(o4, box2);

        float w1 = getWeight(box1);
        System.out.println("Вес первой коробки " + w1);
        float w2 = getWeight(box2);
        System.out.println("Вес второй коробки " + w2);

        putFruitInBox(a1, currentbox);
        putFruitInBox(a2, currentbox);
        putFruitInBox(a3, currentbox);

        boolean comp = compareBoxWithCurrent(box1);
        if (!comp)
            System.out.println("Вес не совпадает с текущей коробкой.");
        else System.out.println("Вес совпадает с текущей коробкой.");

        System.out.println("В наполняемой коробке " + box1 + ".");
        currentbox.clear();
        throwFruitsFromCurrentBox(box1);
        putFruitInBox(new Apple(), currentbox);
        putFruitInBox(new Apple(), currentbox);
        throwFruitsFromCurrentBox(box1);
    }
}
