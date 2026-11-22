package java8;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.LockSupport;

public class ThreadExperiment {

    public record ExperimentResult(
            String mode,
            int tasks,
            long elapsedMs,
            int namedPlatformThreads,
            long memoryDeltaMb
    ) {
    }

    public static void demo() throws InterruptedException, ExecutionException, TimeoutException {
        ExperimentResult platform = runPlatform(400, 32, Duration.ofMillis(20));
        ExperimentResult virtual = runVirtual(400, Duration.ofMillis(20));

        System.out.println("Platform: " + platform);
        System.out.println("Virtual:  " + virtual);
        System.out.println("Это учебное наблюдение, не полноценный benchmark.");
    }

    public static ExperimentResult runPlatform(int tasks, int poolSize, Duration wait)
            throws InterruptedException, ExecutionException, TimeoutException {
        Runtime runtime = Runtime.getRuntime();
        long memoryBefore = usedMemory(runtime);
        ExecutorService executor = Executors.newFixedThreadPool(
                poolSize, ExecutorSupport.named("platform-exp"));
        long start = System.nanoTime();

        try {
            List<Future<Integer>> futures = submitTasks(executor, tasks, wait);
            int completed = sumResults(futures);
            return new ExperimentResult(
                    "platform",
                    completed,
                    elapsedMillis(start),
                    countNamedPlatformThreads("platform-exp"),
                    memoryDeltaMb(runtime, memoryBefore)
            );
        } finally {
            ExecutorSupport.shutdownAndAwait(executor);
        }
    }

    public static ExperimentResult runVirtual(int tasks, Duration wait)
            throws InterruptedException, ExecutionException, TimeoutException {
        Runtime runtime = Runtime.getRuntime();
        long memoryBefore = usedMemory(runtime);
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        long start = System.nanoTime();

        try {
            List<Future<Integer>> futures = submitTasks(executor, tasks, wait);
            int completed = sumResults(futures);
            return new ExperimentResult(
                    "virtual",
                    completed,
                    elapsedMillis(start),
                    countNamedPlatformThreads("platform-exp"),
                    memoryDeltaMb(runtime, memoryBefore)
            );
        } finally {
            ExecutorSupport.shutdownAndAwait(executor);
        }
    }

    private static List<Future<Integer>> submitTasks(
            ExecutorService executor,
            int tasks,
            Duration wait
    ) {
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < tasks; i++) {
            futures.add(executor.submit(() -> {
                block(wait);
                return 1;
            }));
        }
        return futures;
    }

    private static int sumResults(List<Future<Integer>> futures)
            throws InterruptedException, ExecutionException, TimeoutException {
        int completed = 0;
        for (Future<Integer> future : futures) {
            completed += future.get(5, TimeUnit.SECONDS);
        }
        return completed;
    }

    private static void block(Duration duration) {
        LockSupport.parkNanos(duration.toNanos());
    }

    private static long elapsedMillis(long startNanos) {
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);
    }

    private static long usedMemory(Runtime runtime) {
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private static long memoryDeltaMb(Runtime runtime, long before) {
        long delta = usedMemory(runtime) - before;
        return Math.max(0, delta / (1024 * 1024));
    }

    private static int countNamedPlatformThreads(String prefix) {
        return (int) Thread.getAllStackTraces().keySet().stream()
                .filter(thread -> !thread.isVirtual())
                .filter(thread -> thread.getName().startsWith(prefix))
                .count();
    }
}
