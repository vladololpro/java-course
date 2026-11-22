package java8;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.LockSupport;

public class BatchCourseLoader {

    private static final Map<Long, String> COURSES = Map.of(
            1L, "JAVA-1",
            2L, "WEB-1",
            3L, "SQL-1"
    );

    public record CourseBatchResult(List<String> values, List<String> errors) {
    }

    private record Outcome(String value, String error) {
    }

    public CompletableFuture<List<String>> loadAllOrFail(
            List<Long> courseIds,
            ExecutorService executor
    ) {
        List<CompletableFuture<String>> futures = courseIds.stream()
                .map(id -> CompletableFuture.supplyAsync(() -> load(id), executor))
                .toList();

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new))
                .thenApply(ignored -> futures.stream()
                        .map(CompletableFuture::join)
                        .toList());
    }

    public CompletableFuture<CourseBatchResult> loadWithErrors(
            List<Long> courseIds,
            ExecutorService executor
    ) {
        List<CompletableFuture<Outcome>> futures = courseIds.stream()
                .map(id -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return new Outcome(load(id), null);
                    } catch (RuntimeException e) {
                        return new Outcome(null, "Курс " + id + ": " + e.getMessage());
                    }
                }, executor))
                .toList();

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new))
                .thenApply(ignored -> {
                    List<String> values = new ArrayList<>();
                    List<String> errors = new ArrayList<>();
                    for (Outcome outcome : futures.stream()
                            .map(CompletableFuture::join)
                            .toList()) {
                        if (outcome.error() == null) {
                            values.add(outcome.value());
                        } else {
                            errors.add(outcome.error());
                        }
                    }
                    return new CourseBatchResult(List.copyOf(values), List.copyOf(errors));
                });
    }

    public static void demo() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newFixedThreadPool(3, ExecutorSupport.named("batch-io"));
        try {
            BatchCourseLoader loader = new BatchCourseLoader();

            List<String> all = loader.loadAllOrFail(List.of(1L, 2L, 3L), executor)
                    .get(3, TimeUnit.SECONDS);
            System.out.println("Fail-fast, всё загружено: " + all);

            List<Long> mixed = List.of(1L, 99L, 3L);
            try {
                loader.loadAllOrFail(mixed, executor).get(3, TimeUnit.SECONDS);
            } catch (ExecutionException e) {
                System.out.println("Fail-fast, ошибка: " + e.getCause().getMessage());
            }

            CourseBatchResult partial = loader.loadWithErrors(mixed, executor)
                    .get(3, TimeUnit.SECONDS);
            System.out.println("Частичный результат: " + partial.values()
                    + ", ошибки: " + partial.errors());
        } finally {
            ExecutorSupport.shutdownAndAwait(executor);
        }
    }

    private static String load(long id) {
        LockSupport.parkNanos(Duration.ofMillis(5).toNanos());
        String course = COURSES.get(id);
        if (course == null) {
            throw new IllegalArgumentException("курс не найден");
        }
        return course;
    }
}
