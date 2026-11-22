package java8;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task2AsyncCompositionTest {

    @Test
    @Timeout(10)
    void buildCardRecordsStageThreads() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(
                2, ExecutorSupport.named("test-card"));
        try {
            BlockingQueue<AsyncCourseService.CardTrace> traces = new ArrayBlockingQueue<>(1);
            new AsyncCourseService().buildCard(1, executor, traces::add)
                    .get(3, TimeUnit.SECONDS);

            AsyncCourseService.CardTrace trace = traces.poll(1, TimeUnit.SECONDS);
            assertNotNull(trace);
            assertTrue(trace.card().contains("JAVA-1"));
            assertTrue(trace.loadThread().startsWith("test-card-"));
            assertNotNull(trace.formatThread());
            assertNotNull(trace.printThread());
        } finally {
            ExecutorSupport.shutdownAndAwait(executor);
        }
    }

    @Test
    @Timeout(10)
    void enrollmentsFutureIsFlat() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(
                2, ExecutorSupport.named("test-enrollments"));
        try {
            List<AsyncCourseService.Enrollment> enrollments = new AsyncCourseService()
                    .loadUserEnrollments(101, executor)
                    .get(3, TimeUnit.SECONDS);

            assertEquals(2, enrollments.size());
            assertEquals("JAVA-1", enrollments.get(0).courseCode());
        } finally {
            ExecutorSupport.shutdownAndAwait(executor);
        }
    }
}
