package java4.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

/* Сервис для расчета цены и возврата. */
public class PricingService {

    public Money calculatePrice(CourseOffering offering, Instant purchaseInstant) {
        Money price = offering.getBasePrice();
        
        if (offering.isEarlyBird(purchaseInstant)) {
            price = price.subtract(offering.getEarlyBirdDiscount());
        }
        return price;
    }

    public Money calculateRefund(CourseOffering offering, Money pricePaid) {
        int remaining = offering.getRemainingSessions();
        int total = offering.getTotalSessions();

        if (remaining == 0) {
            return new Money(BigDecimal.ZERO, pricePaid.getCurrency());
        }
        if (remaining == total) {
            return pricePaid;
        }

        BigDecimal ratio = new BigDecimal(remaining).divide(new BigDecimal(total), 4, RoundingMode.HALF_UP);
        return pricePaid.percentage(ratio.multiply(new BigDecimal("100")));
    }

    public void run() {
        System.out.println("=== 1. Подготовка данных ===");
        Money basePrice = new Money(new BigDecimal("10000.00"), "RUB");
        Money earlyDiscount = new Money(new BigDecimal("2000.00"), "RUB");
        
        Instant start = Instant.parse("2024-10-15T14:00:00Z");
        Instant earlyDeadline = Instant.parse("2024-10-10T23:59:59Z");
        
        CourseOffering offering = new CourseOffering("Java Course", start, 
            java.time.ZoneId.of("Europe/Moscow"), basePrice, earlyDeadline, earlyDiscount, 10, 4);

        System.out.println("Вебинар: " + offering);
        System.out.println("Зона организатора: " + offering.getStartInOrganizerZone());

        System.out.println("\n=== 2. Время в разных зонах ===");
        System.out.println("Москва:   " + offering.getStartInUserZone(java.time.ZoneId.of("Europe/Moscow")));
        System.out.println("Токио:    " + offering.getStartInUserZone(java.time.ZoneId.of("Asia/Tokyo")));
        System.out.println("Нью-Йорк: " + offering.getStartInUserZone(java.time.ZoneId.of("America/New_York")));

        System.out.println("\n=== 3. Граница early-bird ===");
        Instant before = Instant.parse("2024-10-09T10:00:00Z");
        Instant after = Instant.parse("2024-10-11T10:00:00Z");
        
        PricingService service = new PricingService();
        System.out.println("До дедлайна: " + service.calculatePrice(offering, before));
        System.out.println("После дедлайна: " + service.calculatePrice(offering, after));

        System.out.println("\n=== 4. Возврат пропорционально оставшимся занятиям ===");
        Money paid = service.calculatePrice(offering, before);
        Money refund = service.calculateRefund(offering, paid);
        System.out.println("Оплачено: " + paid);
        System.out.println("Осталось занятий: " + offering.getRemainingSessions() + " из " + offering.getTotalSessions());
        System.out.println("Возврат:  " + refund);

        System.out.println("\n=== 5. Защита типов ===");
        System.out.println("Instant - для хранения момента (UTC)");
        System.out.println("ZonedDateTime - для отображения пользователю");
        System.out.println("BigDecimal - для денег (без double)");
    }
}