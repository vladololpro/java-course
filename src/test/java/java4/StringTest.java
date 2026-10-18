package java4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringTest {

    @Test
    void testStringIdentity() {
        String a = "Java";
        String b = "Java";
        String c = new String("Java");
        
        assertTrue(a == b, "Литералы должны быть в пуле");
        assertFalse(a == c, "new String создает объект в куче");
        assertEquals(a, c, "equals сравнивает значения");
    }

    @Test
    void testStringBuilderSpeed() {
        int count = 100;
        StringBuilder sb = new StringBuilder();
        String result1 = "";
        for (int i = 0; i < count; i++) {
            sb.append(i);
            result1 += i;
        }
        assertEquals(result1, sb.toString(), "Результаты сборки должны совпадать");
    }
}