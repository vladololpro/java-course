package java8;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task4ExecutorPolicyTest {

    @Test
    @Timeout(10)
    void singleThreadExecutorStarvesCpuTask() throws Exception {
        ExecutorService bad = Executors.newSingleThreadExecutor(
                ExecutorSupport.named("test-starvation"));
        try {
            Future<?> blocking = bad.submit(() -> block(Duration.ofMillis(200)));
            long start = System.nanoTime();
            Future<String> cpu = bad.submit(() -> "CPU-" + ExecutorPolicyDemo.cpuWork(100_000));

            String result = cpu.get(2, TimeUnit.SECONDS);
            long waited = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
            blocking.get(2, TimeUnit.SECONDS);

            assertTrue(result.startsWith("CPU-"));
            assertTrue(waited >= 100, "CPU-задача должна подождать blocking-задачу в одном потоке");
        } finally {
            ExecutorSupport.shutdownAndAwait(bad);
        }
    }

    @Test
    @Timeout(10)
    void separateExecutorsKeepRolesApart() throws Exception {
        ExecutorService cpu = Executors.newSingleThreadExecutor(ExecutorSupport.named("test-cpu"));
        ExecutorService io = Executors.newSingleThreadExecutor(ExecutorSupport.named("test-io"));
        try {
            Future<Long> cpuResult = cpu.submit(() -> ExecutorPolicyDemo.cpuWork(100_000));
            Future<String> ioThread = io.submit(() -> {
                String name = Thread.currentThread().getName();
                block(Duration.ofMillis(50));
                return name;
            });

            assertTrue(cpuResult.get(2, TimeUnit.SECONDS) > 0);
            assertTrue(ioThread.get(2, TimeUnit.SECONDS).startsWith("test-io-"));
        } finally {
            ExecutorSupport.shutdownAndAwait(io);
            ExecutorSupport.shutdownAndAwait(cpu);
        }
    }

    @Test
    @Timeout(10)
    void virtualExecutorHandlesManyBlockingTasks() throws Exception {
        try (ExecutorService virtualIo = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Integer>> futures = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                futures.add(virtualIo.submit(() -> {
                    block(Duration.ofMillis(10));
                    return 1;
                }));
            }

            int completed = 0;
            for (Future<Integer> future : futures) {
                completed += future.get(2, TimeUnit.SECONDS);
            }
            assertEquals(50, completed);
        }
    }

    private static void block(Duration duration) {
        LockSupport.parkNanos(duration.toNanos());
    }
}
