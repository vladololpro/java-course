package java7;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task1LostIncrementTest {

    @Test
    @Timeout(30)
    void unsafeCounterLosesIncrements() throws Exception {
        int threads = 16;
        int increments = 20_000;
        int expected = threads * increments;
        boolean lostIncrements = false;

        for (int round = 1; round <= 10 && !lostIncrements; round++) {
            int actual = LostIncrementDemo.runUnsafe(threads, increments);
            assertTrue(actual <= expected, "Счётчик не должен превышать ожидаемое значение");
            lostIncrements = actual < expected;
        }

        assertTrue(lostIncrements, "Ожидалась потеря инкрементов хотя бы в одном раунде");
    }

    @Test
    @Timeout(30)
    void stepCounterLosesIncrements() throws Exception {
        int threads = 16;
        int increments = 20_000;
        int expected = threads * increments;
        boolean lostIncrements = false;

        for (int round = 1; round <= 10 && !lostIncrements; round++) {
            int actual = LostIncrementDemo.runStepCounter(threads, increments);
            assertTrue(actual <= expected, "Счётчик не должен превышать ожидаемое значение");
            lostIncrements = actual < expected;
        }

        assertTrue(lostIncrements, "Ожидалась потеря инкрементов хотя бы в одном раунде");
    }

    @Test
    @Timeout(20)
    void synchronizedCounterKeepsExactValue() throws Exception {
        int threads = 8;
        int increments = 10_000;

        int actual = LostIncrementDemo.runSynchronized(threads, increments);

        assertEquals(threads * increments, actual);
    }

    @Test
    @Timeout(20)
    void synchronizedBlockCounterKeepsExactValue() throws Exception {
        int threads = 8;
        int increments = 10_000;

        int actual = LostIncrementDemo.runSynchronizedBlock(threads, increments);

        assertEquals(threads * increments, actual);
    }
}
