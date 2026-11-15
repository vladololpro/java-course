package java7;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LostIncrementDemo {

    public static final int DEFAULT_THREADS = 16;
    public static final int DEFAULT_INCREMENTS = 20_000;

    public static class UnsafeCounter {
        private int value;

        public void increment() {
            value++;
        }

        public int value() {
            return value;
        }
    }

    public static class StepCounter {
        private int value;

        public void increment() {
            int current = value;
            int next = current + 1;
            value = next;
        }

        public int value() {
            return value;
        }
    }

    public static class SynchronizedCounter {
        private int value;

        public synchronized void increment() {
            value++;
        }

        public synchronized int value() {
            return value;
        }
    }

    public static class SynchronizedBlockCounter {
        private int value;
        private final Object monitor = new Object();

        public void increment() {
            synchronized (monitor) {
                value++;
            }
        }

        public int value() {
            synchronized (monitor) {
                return value;
            }
        }
    }

    public static int runUnsafe(int threads, int increments) throws InterruptedException {
        UnsafeCounter counter = new UnsafeCounter();
        runConcurrently(threads, increments, counter::increment);
        return counter.value();
    }

    public static int runStepCounter(int threads, int increments) throws InterruptedException {
        StepCounter counter = new StepCounter();
        runConcurrently(threads, increments, counter::increment);
        return counter.value();
    }

    public static int runSynchronized(int threads, int increments) throws InterruptedException {
        SynchronizedCounter counter = new SynchronizedCounter();
        runConcurrently(threads, increments, counter::increment);
        return counter.value();
    }

    public static int runSynchronizedBlock(int threads, int increments) throws InterruptedException {
        SynchronizedBlockCounter counter = new SynchronizedBlockCounter();
        runConcurrently(threads, increments, counter::increment);
        return counter.value();
    }

    private static void runConcurrently(int threads, int increments, Runnable action) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(
                threads,
                runnable -> new Thread(runnable, "lost-increment-worker")
        );
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threads);

        try {
            for (int i = 0; i < threads; i++) {
                executor.submit(() -> {
                    try {
                        start.await();
                        for (int j = 0; j < increments; j++) {
                            action.run();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        done.countDown();
                    }
                });
            }

            start.countDown();
            if (!done.await(15, TimeUnit.SECONDS)) {
                throw new IllegalStateException("Потоки не успели завершить работу");
            }
        } finally {
            executor.shutdownNow();
        }
    }

    public static void demo() {
        int expected = DEFAULT_THREADS * DEFAULT_INCREMENTS;

        try {
            int unsafe = runUnsafe(DEFAULT_THREADS, DEFAULT_INCREMENTS);
            int synchronizedValue = runSynchronized(DEFAULT_THREADS, DEFAULT_INCREMENTS);
            int synchronizedBlockValue = runSynchronizedBlock(DEFAULT_THREADS, DEFAULT_INCREMENTS);

            System.out.println("Ожидаемое значение: " + expected);
            System.out.println("Без синхронизации: " + unsafe
                    + " (потеряно " + (expected - unsafe) + ")");
            System.out.println("synchronized-метод: " + synchronizedValue);
            System.out.println("synchronized-блок: " + synchronizedBlockValue);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Демонстрация прервана");
        }
    }
}
