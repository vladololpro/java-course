package java1;

import java1.task07.FlakySender;
import java1.task07.MetricsSender;
import java1.task07.NotificationSender;
import java1.task07.RetrySender;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task07Test {

    @Test
    void retrySender_repeatsUntilSuccess() {
        AtomicInteger calls = new AtomicInteger();
        NotificationSender target = message -> calls.incrementAndGet();
        RetrySender retry = new RetrySender(new FlakySender(target, 2), 3);

        try (OutputCapture ignored = new OutputCapture()) {
            retry.send("hello");
        }

        assertEquals(1, calls.get());
    }

    @Test
    void retrySender_throwsAfterAllAttempts() {
        AtomicInteger calls = new AtomicInteger();
        NotificationSender target = message -> calls.incrementAndGet();
        RetrySender retry = new RetrySender(new FlakySender(target, 5), 2);

        try (OutputCapture ignored = new OutputCapture()) {
            IllegalStateException error =
                assertThrows(IllegalStateException.class, () -> retry.send("hello"));

            assertEquals("all attempts failed", error.getMessage());
            assertNotNull(error.getCause());
            assertEquals("temporary failure", error.getCause().getMessage());
            assertEquals(0, calls.get());
        }
    }

    @Test
    void metricsSender_countsCalls() {
        AtomicInteger calls = new AtomicInteger();
        MetricsSender sender = new MetricsSender(message -> calls.incrementAndGet());

        sender.send("one");
        sender.send("two");

        assertEquals(2, sender.count());
        assertEquals(2, calls.get());
    }
}
