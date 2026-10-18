package java4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IntegerPolicyTest {

    @Test
    void testIntegerCache() {
        Integer a = 100;
        Integer b = 100;
        Integer c = 1000;
        Integer d = 1000;

        assertTrue(a == b, "Числа в кэше (-128..127) имеют одинаковую ссылку");
        assertFalse(c == d, "Числа вне кэша имеют разные ссылки");
        assertTrue(c.equals(d), "equals сравнивает значения");
    }

    @Test
    void testNullUnboxing() {
        Integer empty = null;
        assertThrows(NullPointerException.class, () -> {
            int result = empty + 5;
        });
    }
}