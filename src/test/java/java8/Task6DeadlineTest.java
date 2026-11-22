package java8;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task6DeadlineTest {

    @Test
    @Timeout(10)
    void stagesFitIntoCommonDeadline() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(
                2, ExecutorSupport.named("test-deadline-ok"));
        try {
            String result = DeadlineRunner.runWithDeadline(executor, Duration.ofMillis(300));
            assertEquals("первая + вторая", result);
        } finally {
            ExecutorSupport.shutdownAndAwait(executor);
        }
    }

    @Test
    @Timeout(10)
    void exceededDeadlineThrowsTimeout() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(
                2, ExecutorSupport.named("test-deadline-timeout"));
        try {
            assertThrows(
                    TimeoutException.class,
                    () -> DeadlineRunner.runWithDeadline(executor, Duration.ofMillis(30)));
        } finally {
            ExecutorSupport.shutdownAndAwait(executor);
        }
    }

    @Test
    @Timeout(10)
    void cancelledTaskReleasesResource() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor(
                ExecutorSupport.named("test-deadline-cancel"));
        try {
            DeadlineRunner.TimedTask task =
                    new DeadlineRunner.TimedTask("долгая", Duration.ofMillis(500));
            Future<String> future = executor.submit(task);

            assertThrows(TimeoutException.class, () -> future.get(30, TimeUnit.MILLISECONDS));
            assertTrue(future.cancel(true));
            assertTrue(task.awaitFinished(1, TimeUnit.SECONDS));
            assertTrue(task.isClosed());
        } finally {
            ExecutorSupport.shutdownAndAwait(executor);
        }
    }
}
