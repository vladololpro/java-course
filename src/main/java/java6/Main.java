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
        System.out.println();

        System.out.println("=== Задание 4: Collectors и summary object ===");
        LevelSummarizer summarizer = new LevelSummarizer();
        summarizer.demo();
        System.out.println();

        System.out.println("=== Задание 5: Optional как результат ===");
        OptionalDemo optionalDemo = new OptionalDemo();
        optionalDemo.demo();
        System.out.println();
        
        System.out.println("=== Задание 6: Собственный collector ===");
        CustomCollectorDemo collectorDemo = new CustomCollectorDemo();
        collectorDemo.demo();
    }
}