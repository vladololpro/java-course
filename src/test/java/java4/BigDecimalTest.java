package java4;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.math.RoundingMode;
import static org.junit.jupiter.api.Assertions.*;

class BigDecimalTest {

    @Test
    void testBigDecimalCreation() {
        BigDecimal fromDouble = new BigDecimal(0.1);
        BigDecimal fromString = new BigDecimal("0.1");
        
        assertNotEquals(fromDouble, fromString, "Из double получается погрешность");
        assertEquals(fromString, BigDecimal.valueOf(0.1), "valueOf работает через строку");
    }

    @Test
    void testRounding() {
        BigDecimal value = new BigDecimal("10.00");
        BigDecimal[] parts = new BigDecimal[3];
        
        BigDecimal base = value.divide(new BigDecimal(3), 2, RoundingMode.DOWN);
        BigDecimal remainder = value.subtract(base.multiply(new BigDecimal(3)));
        
        parts[0] = base.add(remainder);
        parts[1] = base;
        parts[2] = base;
        
        assertEquals(new BigDecimal("3.34"), parts[0]);
        assertEquals(new BigDecimal("3.33"), parts[1]);
        
        BigDecimal sum = parts[0].add(parts[1]).add(parts[2]);
        assertEquals(0, sum.compareTo(value), "Сумма частей должна быть равна исходной");
    }
}