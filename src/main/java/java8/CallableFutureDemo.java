package java8;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CallableFutureDemo {

    public static void demo() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newFixedThreadPool(2, ExecutorSupport.named("future"));
        try {
            Future<?> runnable = executor.submit(
                    () -> System.out.println("Runnable: результат не возвращается"));
            System.out.println("Future от Runnable: " + runnable.get(2, TimeUnit.SECONDS));

            Future<Integer> callable = executor.submit(() -> sum(1, 100));
            System.out.println("Future от Callable: " + callable.get(2, TimeUnit.SECONDS));

            Future<Integer> failed = executor.submit(() -> {
                throw new IOException("проверенное исключение");
            });
            try {
                failed.get(2, TimeUnit.SECONDS);
            } catch (ExecutionException e) {
                System.out.println("Ошибка вычисления: "
                        + e.getCause().getClass().getSimpleName() + ": " + e.getCause().getMessage());
            }

            InterruptibleSum task = new InterruptibleSum(1_000_000);
            Future<Integer> longTask = executor.submit(task);
            try {
                longTask.get(50, TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                System.out.println("Timeout: результат не получен за 50 мс");
                System.out.println("Отмена задачи: " + longTask.cancel(true));
                System.out.println("Задача завершилась: " + task.awaitFinished(1, TimeUnit.SECONDS));
                System.out.println("Ресурс закрыт после отмены: " + task.isClosed());
            }
        } finally {
            ExecutorSupport.shutdownAndAwait(executor);
        }
    }

    static int sum(int from, int to) {
        int result = 0;
        for (int i = from; i <= to; i++) {
            result += i;
        }
        return result;
    }

    static final class InterruptibleSum implements Callable<Integer> {
        private final int iterations;
        private volatile boolean closed;
        private final CountDownLatch finished = new CountDownLatch(1);

        InterruptibleSum(int iterations) {
            this.iterations = iterations;
        }

        @Override
        public Integer call() throws InterruptedException {
            try {
                int result = 0;
                for (int i = 0; i < iterations; i++) {
                    result += i;
                    Thread.sleep(1);
                }
                return result;
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
