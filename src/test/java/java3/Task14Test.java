package java3;

import java3.task14.LruCache;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task14Test {

    @Test
    void getRefreshesRecencyBeforeEviction() {
        LruCache<String, Integer> cache = new LruCache<>(3);
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);

        cache.get("A");
        cache.put("D", 4);

        assertFalse(cache.containsKey("B"));
        assertTrue(cache.containsKey("A"));
        assertTrue(cache.containsKey("C"));
        assertTrue(cache.containsKey("D"));
        assertEquals(3, cache.size());
    }

    @Test
    void repeatedPutUpdatesValueWithoutGrowingCache() {
        LruCache<String, Integer> cache = new LruCache<>(2);
        cache.put("X", 10);
        cache.put("Y", 20);
        cache.put("X", 11);

        assertEquals(11, cache.get("X"));
        assertEquals(20, cache.get("Y"));
        assertEquals(2, cache.size());
    }

    @Test
    void nullKeyAndValueAreRejected() {
        LruCache<String, Integer> cache = new LruCache<>(2);

        assertThrows(IllegalArgumentException.class, () -> cache.put(null, 1));
        assertThrows(IllegalArgumentException.class, () -> cache.put("A", null));
        assertThrows(IllegalArgumentException.class, () -> cache.get(null));
    }

    @Test
    void nonPositiveCapacityIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new LruCache<String, Integer>(0));
        assertThrows(IllegalArgumentException.class, () -> new LruCache<String, Integer>(-1));
    }
}
