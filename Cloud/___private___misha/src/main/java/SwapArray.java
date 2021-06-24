import java.util.ArrayList;
import java.util.Arrays;

public class SwapArray {
    static String[] daysWeek = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    static ArrayList<String> weekDays = new ArrayList<String>();

    public static void swap(int fE, int sE) {
        String tempS = daysWeek[fE];
        daysWeek[fE] = daysWeek[sE];
        daysWeek[sE] = tempS;
    }

    public static void convert2Arraylist(String[] arr) {
        weekDays.addAll(Arrays.asList(daysWeek));
    }

    public static void main(String[] args) {
        int firstElem = 2;
        int secondElem = 5;
        System.out.println("Исходный массив");
        System.out.println(Arrays.toString(daysWeek));

        swap(firstElem, secondElem);

        System.out.println("Переставленный массив");
        System.out.println(Arrays.toString(daysWeek));
        swap(firstElem, secondElem);

        convert2Arraylist(daysWeek);

        System.out.println("Массив как ArrayList");
        System.out.println(weekDays);

    }


}
