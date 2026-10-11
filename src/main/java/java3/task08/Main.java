package java3.task08;

import java.util.ArrayList;
import java.util.List;

/* Точка входа для Задачи 8. */
public class Main {

    public static void main(String[] args) {

        System.out.println("=== max on Integer ===");
        List<Integer> numbers = new ArrayList<>();
        numbers.add(3);
        numbers.add(7);
        numbers.add(1);
        numbers.add(9);
        numbers.add(4);
        System.out.println("max: " + MaxUtils.max(numbers));

        System.out.println();
        System.out.println("=== max on String ===");
        List<String> words = new ArrayList<>();
        words.add("banana");
        words.add("apple");
        words.add("cherry");
        System.out.println("max: " + MaxUtils.max(words));

        System.out.println();
        System.out.println("=== max on Score ===");
        List<Score> scores = new ArrayList<>();
        scores.add(new Score(50));
        scores.add(new Score(80));
        scores.add(new Score(65));
        System.out.println("max: " + MaxUtils.max(scores));

        System.out.println();
        System.out.println("=== max on empty list ===");
        try {
            MaxUtils.max(new ArrayList<Integer>());
        } catch (IllegalArgumentException e) {
            System.out.println("on empty: " + e.getMessage());
        }
    }
}