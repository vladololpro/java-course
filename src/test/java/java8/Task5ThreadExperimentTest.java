package java8;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task5ThreadExperimentTest {

    @Test
    @Timeout(20)
    void platformExperimentCompletesAndCountsNamedThreads() throws Exception {
        ThreadExperiment.ExperimentResult result =
                ThreadExperiment.runPlatform(100, 8, Duration.ofMillis(5));

        assertEquals(100, result.tasks());
        assertEquals(8, result.namedPlatformThreads());
        assertTrue(result.elapsedMs() >= 0);
    }

    @Test
    @Timeout(20)
    void virtualExperimentCompletesWithoutNamedPlatformThreads() throws Exception {
        ThreadExperiment.ExperimentResult result =
                ThreadExperiment.runVirtual(100, Duration.ofMillis(5));

        assertEquals(100, result.tasks());
        assertEquals(0, result.namedPlatformThreads());
        assertTrue(result.elapsedMs() >= 0);
    }
}
