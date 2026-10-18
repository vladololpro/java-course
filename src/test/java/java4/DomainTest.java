package java4;

import org.junit.jupiter.api.Test;
import java4.domain.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import static org.junit.jupiter.api.Assertions.*;

class DomainTest {

    @Test
    void testCourseTitleNormalization() {
        CourseTitle title = new CourseTitle("  Java   Basics  ");
        assertEquals("Java Basics", title.getDisplayValue());
        assertEquals("java basics", title.getSearchKey());
        
        CourseTitle title2 = new CourseTitle(" java   basics ");
        assertEquals(title.getSearchKey(), title2.getSearchKey(), "Ключи поиска совпадают");
    }

    @Test
    void testMoney() {
        Money m1 = new Money(new BigDecimal("100.00"), "RUB");
        Money m2 = new Money(new BigDecimal("50.50"), "RUB");
        
        assertEquals(new BigDecimal("150.50"), m1.add(m2).getAmount());
        
        Money usd = new Money(new BigDecimal("10.00"), "USD");
        assertThrows(IllegalArgumentException.class, () -> m1.add(usd));
    }

    @Test
    void testCourseOfferingAndPricing() {
        Money base = new Money(new BigDecimal("10000.00"), "RUB");
        Money discount = new Money(new BigDecimal("2000.00"), "RUB");
        Instant start = Instant.parse("2024-10-15T14:00:00Z");
        Instant deadline = Instant.parse("2024-10-10T23:59:59Z");
        
        CourseOffering offering = new CourseOffering("Java", start, ZoneId.of("Europe/Moscow"), 
            base, deadline, discount, 10, 4);
            
        PricingService service = new PricingService();
        
        // Покупка до дедлайна
        Instant before = Instant.parse("2024-10-09T10:00:00Z");
        Money priceWithDiscount = service.calculatePrice(offering, before);
        assertEquals(new BigDecimal("8000.00"), priceWithDiscount.getAmount());
        
        // Возврат (осталось 6 из 10 = 60%)
        Money refund = service.calculateRefund(offering, priceWithDiscount);
        assertEquals(new BigDecimal("4800.00"), refund.getAmount());
    }
}