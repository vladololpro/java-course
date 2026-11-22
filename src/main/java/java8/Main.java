package java8;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Задание 1. Callable и Future ===");
        CallableFutureDemo.demo();

        System.out.println();
        System.out.println("=== Задание 2. Последовательное преобразование ===");
        AsyncCourseService.demo();

        System.out.println();
        System.out.println("=== Задание 3. allOf с сохранением результатов ===");
        BatchCourseLoader.demo();

        System.out.println();
        System.out.println("=== Задание 4. Правильный executor ===");
        ExecutorPolicyDemo.demo();

        System.out.println();
        System.out.println("=== Задание 5. Platform и virtual threads ===");
        ThreadExperiment.demo();

        System.out.println();
        System.out.println("=== Задание 6. Общий deadline ===");
        DeadlineRunner.demo();
    }
}
