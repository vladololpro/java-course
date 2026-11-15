package java7;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class EnrollmentPipeline {

    public record EnrollmentRequest(long requestId, long studentId, String courseCode) {
    }

    public record ProcessedRequest(long requestId, long studentId, String courseCode, String worker) {
    }

    private static final EnrollmentRequest POISON = new EnrollmentRequest(-1, -1, "__STOP__");

    private final AtomicInteger threadSequence = new AtomicInteger();

    public List<ProcessedRequest> process(
            List<EnrollmentRequest> requests,
            int queueCapacity,
            int producerCount,
            int consumerCount
    ) throws InterruptedException {
        if (queueCapacity <= 0) {
            throw new IllegalArgumentException("queueCapacity должен быть больше нуля");
        }
        if (producerCount <= 0 || consumerCount <= 0) {
            throw new IllegalArgumentException("Нужен хотя бы один producer и один consumer");
        }

        BlockingQueue<EnrollmentRequest> queue = new ArrayBlockingQueue<>(queueCapacity);
        ConcurrentLinkedQueue<ProcessedRequest> processed = new ConcurrentLinkedQueue<>();
        CountDownLatch producersDone = new CountDownLatch(producerCount);
        AtomicReference<Throwable> producerFailure = new AtomicReference<>();

        ExecutorService executor = Executors.newFixedThreadPool(
                producerCount + consumerCount,
                threadFactory()
        );

        try {
            int chunkSize = (requests.size() + producerCount - 1) / producerCount;

            for (int p = 0; p < producerCount; p++) {
                int from = Math.min(p * chunkSize, requests.size());
                int to = Math.min(from + chunkSize, requests.size());

                executor.submit(() -> {
                    try {
                        for (int i = from; i < to; i++) {
                            queue.put(requests.get(i));
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (RuntimeException e) {
                        producerFailure.compareAndSet(null, e);
                    } finally {
                        producersDone.countDown();
                    }
                });
            }

            for (int c = 0; c < consumerCount; c++) {
                executor.submit(() -> {
                    try {
                        while (true) {
                            EnrollmentRequest request = queue.take();
                            if (request == POISON) {
                                break;
                            }

                            processed.add(new ProcessedRequest(
                                    request.requestId(),
                                    request.studentId(),
                                    request.courseCode(),
                                    Thread.currentThread().getName()
                            ));
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }

            if (!producersDone.await(10, TimeUnit.SECONDS)) {
                throw new IllegalStateException("Producers не завершились за отведённое время");
            }

            Throwable failure = producerFailure.get();
            if (failure != null) {
                throw new IllegalStateException("Producer завершился с ошибкой", failure);
            }

            for (int c = 0; c < consumerCount; c++) {
                if (!queue.offer(POISON, 5, TimeUnit.SECONDS)) {
                    throw new IllegalStateException("Не удалось отправить сигнал остановки consumer");
                }
            }

            executor.shutdown();
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                throw new IllegalStateException("Workers не завершились за отведённое время");
            }
        } finally {
            executor.shutdownNow();
        }

        return List.copyOf(processed);
    }

    private ThreadFactory threadFactory() {
        return runnable -> new Thread(
                runnable,
                "enrollment-worker-" + threadSequence.incrementAndGet()
        );
    }

    public static int cpuBoundPoolSize() {
        return Math.max(1, Runtime.getRuntime().availableProcessors());
    }

    public static int blockingPoolSize(int parallelism, int waitToComputeRatio) {
        if (parallelism <= 0) {
            throw new IllegalArgumentException("parallelism должен быть больше нуля");
        }
        if (waitToComputeRatio < 0) {
            throw new IllegalArgumentException("waitToComputeRatio не может быть отрицательным");
        }

        return parallelism * (1 + waitToComputeRatio);
    }
}
