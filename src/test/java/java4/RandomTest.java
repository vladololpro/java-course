package java4;

import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.random.RandomGenerator;
import static org.junit.jupiter.api.Assertions.*;

class RandomTest {

    @Test
    void testSeedDeterminism() {
        RandomGenerator r1 = new Random(42);
        RandomGenerator r2 = new Random(42);
        
        assertEquals(r1.nextInt(100), r2.nextInt(100));
        assertEquals(r1.nextInt(100), r2.nextInt(100));
    }
}