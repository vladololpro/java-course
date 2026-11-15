package java7;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Задание 1. Потерянные инкременты ===");
        LostIncrementDemo.demo();

        System.out.println();
        System.out.println("=== Задание 2. Флаг остановки ===");
        StopFlagWorker.demo();

        System.out.println();
        System.out.println("=== Задание 3. Реестр активных сессий ===");
        SessionRegistry registry = new SessionRegistry();
        SessionRegistry.ActiveSession session =
                new SessionRegistry.ActiveSession(1, 101, "JAVA-101");

        System.out.println("Первое создание: " + registry.createIfAbsent(session));
        System.out.println("Повторное создание: " + registry.createIfAbsent(session));
        System.out.println("Активных сессий: " + registry.activeCount());
        System.out.println("Найденная сессия: " + registry.find(1).orElseThrow());

        System.out.println();
        System.out.println("=== Задание 4. Producer-consumer ===");

        List<EnrollmentPipeline.EnrollmentRequest> requests = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            requests.add(new EnrollmentPipeline.EnrollmentRequest(i, 100 + i, "JAVA-101"));
        }

        try {
            List<EnrollmentPipeline.ProcessedRequest> processed =
                    new EnrollmentPipeline().process(requests, 3, 2, 3);

            System.out.println("Обработано заявок: " + processed.size());
            System.out.println("Worker-потоки: " + processed.stream()
                    .map(EnrollmentPipeline.ProcessedRequest::worker)
                    .distinct()
                    .sorted()
                    .toList());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Обработка прервана");
        }

        System.out.println();
        System.out.println("=== Задание 5. Детерминированный тест гонки ===");
        System.out.println("Проверяется в Task5DeterministicRaceTest (CountDownLatch + JUnit).");

        System.out.println();
        System.out.println("=== Задание 6. Ёмкость курса ===");
        CapacityEnrollmentDemo.demo();
    }
}
