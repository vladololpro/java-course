package java3;

import java3.task10.BrokenKey;
import java3.task10.StableKey;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task10Test {

    @Test
    void stableKey_remainsAvailableAfterNonKeyFieldChange() {
        StableKey key = new StableKey(1, "before");
        Set<StableKey> set = new HashSet<>();
        set.add(key);

        key.setTag("after");

        assertTrue(set.contains(key));
        assertEquals(1, set.size());
    }

    @Test
    void brokenKey_isLostAfterHashParticipatingFieldChange() {
        BrokenKey key = new BrokenKey(1, "before");
        Set<BrokenKey> set = new HashSet<>();
        set.add(key);

        assertTrue(set.contains(key));

        key.setTag("after");

        assertFalse(set.contains(key));
        assertEquals(1, set.size());
    }

    @Test
    void stableKey_worksAsHashMapKey() {
        StableKey key = new StableKey(1, "before");
        Map<StableKey, String> map = new HashMap<>();
        map.put(key, "value");

        key.setTag("after");

        assertEquals("value", map.get(key));
    }
}
