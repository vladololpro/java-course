package java7;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task6CapacityEnrollmentTest {

    @Test
    @Timeout(10)
    void bothVariantsRespectCapacityUnderRace() throws Exception {
        assertCapacityUnderRace(new SynchronizedCapacityEnrollment(7), 7);
        assertCapacityUnderRace(new AtomicCapacityEnrollment(7), 7);
    }

    @Test
    @Timeout(10)
    void duplicateStudentTakesOnlyOnePlace() throws Exception {
        assertDuplicateUnderRace(new SynchronizedCapacityEnrollment(3));
        assertDuplicateUnderRace(new AtomicCapacityEnrollment(3));
    }

    @Test
    void rejectedEnrollmentDoesNotChangeState() {
        for (CapacityEnrollment enrollment : List.of(
                new SynchronizedCapacityEnrollment(1),
                new AtomicCapacityEnrollment(1)
        )) {
            assertTrue(enrollment.enroll(1));
            assertFalse(enrollment.enroll(2));
            assertFalse(enrollment.enroll(1));

            assertEquals(1, enrollment.enrolledCount());
            assertTrue(enrollment.isEnrolled(1));
            assertFalse(enrollment.isEnrolled(2));
        }
    }

    private void assertCapacityUnderRace(CapacityEnrollment enrollment, int capacity) throws Exception {
        int attempts = 64;
        int threads = 16;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch start = new CountDownLatch(1);
        AtomicInteger successes = new AtomicInteger();

        try {
            for (long studentId = 1; studentId <= attempts; studentId++) {
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

            assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS));
            assertEquals(capacity, successes.get());
            assertEquals(capacity, enrollment.enrolledCount());
        } finally {
            executor.shutdownNow();
        }
    }

    private void assertDuplicateUnderRace(CapacityEnrollment enrollment) throws Exception {
        int attempts = 32;
        int threads = 8;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch start = new CountDownLatch(1);
        AtomicInteger successes = new AtomicInteger();

        try {
            for (int i = 0; i < attempts; i++) {
                executor.submit(() -> {
                    try {
                        start.await();
                        if (enrollment.enroll(42)) {
                            successes.incrementAndGet();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }

            start.countDown();
            executor.shutdown();

            assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS));
            assertEquals(1, successes.get());
            assertEquals(1, enrollment.enrolledCount());
            assertTrue(enrollment.isEnrolled(42));
        } finally {
            executor.shutdownNow();
        }
    }
}
