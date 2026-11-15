package java7;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task3SessionRegistryTest {

    @Test
    void createsFindsAndRemovesSession() {
        SessionRegistry registry = new SessionRegistry();
        SessionRegistry.ActiveSession session =
                new SessionRegistry.ActiveSession(10, 101, "JAVA-101");

        assertTrue(registry.createIfAbsent(session));
        assertFalse(registry.createIfAbsent(session));
        assertEquals(1, registry.activeCount());
        assertTrue(registry.find(10).isPresent());
        assertTrue(registry.remove(10));
        assertFalse(registry.find(10).isPresent());
        assertEquals(0, registry.activeCount());
    }

    @Test
    @Timeout(5)
    void onlyOneThreadCreatesSameSession() throws Exception {
        int threads = 12;
        SessionRegistry registry = new SessionRegistry();
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch ready = new CountDownLatch(threads);
        CountDownLatch start = new CountDownLatch(1);
        List<Future<Boolean>> results = new ArrayList<>();

        try {
            for (int i = 0; i < threads; i++) {
                results.add(executor.submit(() -> {
                    ready.countDown();
                    start.await();
                    return registry.createIfAbsent(
                            new SessionRegistry.ActiveSession(10, 101, "JAVA-101")
                    );
                }));
            }

            assertTrue(ready.await(2, TimeUnit.SECONDS), "Потоки не подготовились к старту");
            start.countDown();

            int winners = 0;
            for (Future<Boolean> result : results) {
                if (result.get(2, TimeUnit.SECONDS)) {
                    winners++;
                }
            }

            assertEquals(1, winners);
            assertEquals(1, registry.activeCount());
        } finally {
            executor.shutdownNow();
            assertTrue(executor.awaitTermination(2, TimeUnit.SECONDS));
        }
    }
}
