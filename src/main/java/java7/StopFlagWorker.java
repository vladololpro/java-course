package java7;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class StopFlagWorker implements AutoCloseable {

    private final AtomicLong workCount = new AtomicLong();
    private final CountDownLatch started = new CountDownLatch(1);
    private final CountDownLatch stopped = new CountDownLatch(1);

    private volatile boolean stop;

    private Thread thread;

    public void start() {
        if (thread != null) {
            throw new IllegalStateException("Worker уже запущен");
        }

        thread = new Thread(() -> {
            started.countDown();
            try {
                while (!stop) {
                    workCount.incrementAndGet();
                    Thread.onSpinWait();
                }
            } finally {
                stopped.countDown();
            }
        }, "stop-flag-worker");
        thread.start();
    }

    public boolean awaitStarted(long timeout, TimeUnit unit) throws InterruptedException {
        return started.await(timeout, unit);
    }

    public void stop() {
        stop = true;
    }

    public boolean awaitStopped(long timeout, TimeUnit unit) throws InterruptedException {
        return stopped.await(timeout, unit);
    }

    public long workCount() {
        return workCount.get();
    }

    @Override
    public void close() {
        stop();
        if (thread != null) {
            thread.interrupt();
        }
    }

    public static void demo() {
        StopFlagWorker worker = new StopFlagWorker();

        try {
            worker.start();
            if (!worker.awaitStarted(1, TimeUnit.SECONDS)) {
                throw new IllegalStateException("Worker не запустился");
            }

            System.out.println("Worker запущен, флаг остановки пока false");
            worker.stop();

            if (!worker.awaitStopped(2, TimeUnit.SECONDS)) {
                throw new IllegalStateException("Worker не увидел stop=true");
            }

            System.out.println("Worker остановлен, итераций: " + worker.workCount());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Демонстрация прервана");
        } finally {
            worker.close();
        }
    }
}
