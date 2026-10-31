package java6;

/* Общая точка входа для заданий Java 6. */

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Задание 1: Сортировка и детерминизм ===");
        CourseSorter sorter = new CourseSorter();
        sorter.demo();
        System.out.println();

        System.out.println("=== Задание 2: flatMap и обратный индекс ===");
        TopicIndexer indexer = new TopicIndexer();
        indexer.demo();
        System.out.println();

        System.out.println("=== Задание 3: reduce с правильной identity ===");
        DurationReducer reducer = new DurationReducer();
        reducer.demo();
    }
}