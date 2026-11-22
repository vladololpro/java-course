package java8;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task1CallableFutureTest {

    @Test
    @Timeout(10)
    void callableReturnsValueAndRunnableReturnsNull() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor(
                ExecutorSupport.named("test-future"));
        try {
            Future<Integer> callable = executor.submit(() -> CallableFutureDemo.sum(1, 10));
            assertEquals(55, callable.get(2, TimeUnit.SECONDS));

            Future<?> runnable = executor.submit(() -> {
            });
            assertNull(runnable.get(2, TimeUnit.SECONDS));
        } finally {
            ExecutorSupport.shutdownAndAwait(executor);
        }
    }

    @Test
    @Timeout(10)
    void executionExceptionKeepsCheckedCause() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor(
                ExecutorSupport.named("test-future-error"));
        try {
            Future<Integer> failed = executor.submit(() -> {
                throw new IOException("проверенное исключение");
            });

            ExecutionException exception = assertThrows(
                    ExecutionException.class,
                    () -> failed.get(2, TimeUnit.SECONDS));

            assertInstanceOf(IOException.class, exception.getCause());
            assertEquals("проверенное исключение", exception.getCause().getMessage());
        } finally {
            ExecutorSupport.shutdownAndAwait(executor);
        }
    }

    @Test
    @Timeout(10)
    void timeoutCancelsTaskAndClosesResource() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor(
                ExecutorSupport.named("test-future-cancel"));
        try {
            CallableFutureDemo.InterruptibleSum task =
                    new CallableFutureDemo.InterruptibleSum(100_000);
            Future<Integer> future = executor.submit(task);

            assertThrows(TimeoutException.class, () -> future.get(30, TimeUnit.MILLISECONDS));
            assertTrue(future.cancel(true));
            assertTrue(task.awaitFinished(1, TimeUnit.SECONDS));
            assertTrue(task.isClosed());
        } finally {
            ExecutorSupport.shutdownAndAwait(executor);
        }
    }
}
