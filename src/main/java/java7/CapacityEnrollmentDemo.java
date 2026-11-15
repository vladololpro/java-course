package java7;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CapacityEnrollmentDemo {

    private static final int ATTEMPTS = 20;
    private static final int THREADS = 10;
    private static final int CAPACITY = 5;

    public static void demo() {
        run("synchronized + HashSet", new SynchronizedCapacityEnrollment(CAPACITY));
        run("AtomicInteger + ConcurrentHashMap", new AtomicCapacityEnrollment(CAPACITY));
    }

    private static void run(String name, CapacityEnrollment enrollment) {
        ExecutorService executor = Executors.newFixedThreadPool(
                THREADS,
                runnable -> new Thread(runnable, "capacity-enrollment-worker")
        );
        CountDownLatch start = new CountDownLatch(1);
        AtomicInteger successes = new AtomicInteger();

        try {
            for (long studentId = 1; studentId <= ATTEMPTS; studentId++) {
                long id = studentId;
                executor.submit(() -> {
                    try {
                        start.await();
                        if (enrollment.enroll(id)) {
                            successes.incrementAndGet();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }

            start.countDown();
            executor.shutdown();

            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                throw new IllegalStateException("Зачисление не завершилось за отведённое время");
            }

            System.out.printf(
                    "%s: capacity=%d, успешных зачислений=%d, записано=%d%n",
                    name,
                    enrollment.capacity(),
                    successes.get(),
                    enrollment.enrolledCount()
            );
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Демонстрация прервана");
        } finally {
            executor.shutdownNow();
        }
    }
}
