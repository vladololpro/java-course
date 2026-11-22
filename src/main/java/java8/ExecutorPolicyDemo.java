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

public class ExecutorPolicyDemo {

    public static void demo() throws InterruptedException, ExecutionException,
            TimeoutException {
        demonstrateStarvation();
        demonstrateSeparateExecutors();
        demonstrateVirtualThreads();
    }

    private static void demonstrateStarvation()
            throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService bad = Executors.newSingleThreadExecutor(ExecutorSupport.named("starvation"));
        try {
            Future<?> blocking = bad.submit(() -> block(Duration.ofMillis(200)));
            long start = System.nanoTime();
            Future<String> cpu = bad.submit(() -> "CPU-" + cpuWork(500_000));

            String result = cpu.get(2, TimeUnit.SECONDS);
            long waited = elapsedMillis(start);
            blocking.get(2, TimeUnit.SECONDS);

            System.out.println("Starvation: " + result + ", CPU ждал ~" + waited + " мс");
        } finally {
            ExecutorSupport.shutdownAndAwait(bad);
        }
    }

    private static void demonstrateSeparateExecutors()
            throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService cpu = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors(),
                ExecutorSupport.named("cpu"));
        ExecutorService io = Executors.newFixedThreadPool(8, ExecutorSupport.named("io"));
        try {
            Future<Long> cpuResult = cpu.submit(() -> cpuWork(2_000_000));
            Future<String> ioThread = io.submit(() -> {
                String name = Thread.currentThread().getName();
                block(Duration.ofMillis(50));
                return name;
            });

            System.out.println("CPU-результат: " + cpuResult.get(5, TimeUnit.SECONDS));
            System.out.println("Поток I/O: " + ioThread.get(5, TimeUnit.SECONDS));
        } finally {
            ExecutorSupport.shutdownAndAwait(io);
            ExecutorSupport.shutdownAndAwait(cpu);
        }
    }

    private static void demonstrateVirtualThreads()
            throws InterruptedException, ExecutionException, TimeoutException {
        try (ExecutorService virtualIo = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<String>> futures = new ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                int number = i;
                futures.add(virtualIo.submit(() -> {
                    block(Duration.ofMillis(20));
                    return "io-" + number;
                }));
            }

            List<String> results = new ArrayList<>();
            for (Future<String> future : futures) {
                results.add(future.get(2, TimeUnit.SECONDS));
            }
            System.out.println("Virtual I/O задач выполнено: " + results.size());
        }
    }

    static long cpuWork(int iterations) {
        long sum = 0;
        for (int i = 0; i < iterations; i++) {
            sum += i % 7;
        }
        return sum;
    }

    private static void block(Duration duration) {
        LockSupport.parkNanos(duration.toNanos());
    }

    private static long elapsedMillis(long startNanos) {
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);
    }
}
