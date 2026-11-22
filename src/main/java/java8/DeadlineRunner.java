package java8;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DeadlineRunner {

    public static void demo() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newFixedThreadPool(2, ExecutorSupport.named("deadline"));
        try {
            System.out.println("Успех: " + runWithDeadline(executor, Duration.ofMillis(250)));

            try {
                runWithDeadline(executor, Duration.ofMillis(80));
            } catch (TimeoutException e) {
                System.out.println("Timeout: " + e.getMessage());
            }
        } finally {
            ExecutorSupport.shutdownAndAwait(executor);
        }
    }

    public static String runWithDeadline(ExecutorService executor, Duration budget)
            throws InterruptedException, ExecutionException, TimeoutException {
        long deadline = System.nanoTime() + budget.toNanos();

        TimedTask first = new TimedTask("первая", Duration.ofMillis(120));
        Future<String> firstFuture = executor.submit(first);
        String firstValue = await(firstFuture, first, deadline);

        TimedTask second = new TimedTask("вторая", Duration.ofMillis(40));
        Future<String> secondFuture = executor.submit(second);
        String secondValue = await(secondFuture, second, deadline);

        return firstValue + " + " + secondValue;
    }

    private static String await(Future<String> future, TimedTask task, long deadline)
            throws InterruptedException, ExecutionException, TimeoutException {
        try {
            return future.get(remainingNanos(deadline), TimeUnit.NANOSECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            task.awaitFinished(1, TimeUnit.SECONDS);
            throw new TimeoutException("Стадия не уложилась в общий бюджет");
        } catch (ExecutionException e) {
            future.cancel(true);
            task.awaitFinished(1, TimeUnit.SECONDS);
            throw e;
        }
    }

    private static long remainingNanos(long deadline) throws TimeoutException {
        long remaining = deadline - System.nanoTime();
        if (remaining <= 0) {
            throw new TimeoutException("Общий бюджет времени исчерпан");
        }
        return remaining;
    }

    static final class TimedTask implements Callable<String> {
        private final String name;
        private final Duration work;
        private volatile boolean closed;
        private final CountDownLatch finished = new CountDownLatch(1);

        TimedTask(String name, Duration work) {
            this.name = name;
            this.work = work;
        }

        @Override
        public String call() throws InterruptedException {
            try {
                Thread.sleep(work.toMillis());
                return name;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw e;
            } finally {
                closed = true;
                finished.countDown();
            }
        }

        boolean awaitFinished(long timeout, TimeUnit unit) throws InterruptedException {
            return finished.await(timeout, unit);
        }

        boolean isClosed() {
            return closed;
        }
    }
}
