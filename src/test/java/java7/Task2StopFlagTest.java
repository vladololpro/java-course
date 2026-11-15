package java7;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Task2StopFlagTest {

    @Test
    @Timeout(5)
    void volatileFlagStopsWorker() throws Exception {
        StopFlagWorker worker = new StopFlagWorker();

        try {
            worker.start();
            assertTrue(worker.awaitStarted(2, TimeUnit.SECONDS), "Worker не запустился");

            worker.stop();

            assertTrue(worker.awaitStopped(2, TimeUnit.SECONDS), "Worker не увидел stop=true");
        } finally {
            worker.close();
        }
    }
}
