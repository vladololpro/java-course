package java8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class ExecutorSupport {

    private ExecutorSupport() {
    }

    public static ThreadFactory named(String prefix) {
        AtomicInteger number = new AtomicInteger();
        return runnable -> new Thread(runnable, prefix + "-" + number.incrementAndGet());
    }

    public static void shutdownAndAwait(ExecutorService executor) throws InterruptedException {
        executor.shutdown();
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
    }
}
