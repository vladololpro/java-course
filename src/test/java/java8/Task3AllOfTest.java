package java8;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task3AllOfTest {

    @Test
    @Timeout(10)
    void failFastKeepsInputOrder() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(
                3, ExecutorSupport.named("test-batch-order"));
        try {
            List<String> result = new BatchCourseLoader()
                    .loadAllOrFail(List.of(1L, 2L, 3L), executor)
                    .get(3, TimeUnit.SECONDS);

            assertEquals(List.of("JAVA-1", "WEB-1", "SQL-1"), result);
        } finally {
            ExecutorSupport.shutdownAndAwait(executor);
        }
    }

    @Test
    @Timeout(10)
    void failFastPropagatesError() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(
                3, ExecutorSupport.named("test-batch-error"));
        try {
            ExecutionException exception = assertThrows(
                    ExecutionException.class,
                    () -> new BatchCourseLoader()
                            .loadAllOrFail(List.of(1L, 99L), executor)
                            .get(3, TimeUnit.SECONDS));

            assertEquals("курс не найден", exception.getCause().getMessage());
        } finally {
            ExecutorSupport.shutdownAndAwait(executor);
        }
    }

    @Test
    @Timeout(10)
    void partialResultKeepsOrderAndErrors() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(
                3, ExecutorSupport.named("test-batch-partial"));
        try {
            BatchCourseLoader.CourseBatchResult result = new BatchCourseLoader()
                    .loadWithErrors(List.of(1L, 99L, 3L), executor)
                    .get(3, TimeUnit.SECONDS);

            assertEquals(List.of("JAVA-1", "SQL-1"), result.values());
            assertEquals(1, result.errors().size());
            assertTrue(result.errors().get(0).contains("Курс 99"));
        } finally {
            ExecutorSupport.shutdownAndAwait(executor);
        }
    }
}
