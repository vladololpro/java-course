package java4.numbers;

import java.math.BigDecimal;
import java.math.RoundingMode;

/* Класс для демонстрации распределения скидки с точным округлением. */
public class DiscountAllocator {

    public void run() {
        System.out.println("=== Распределение скидки 10.00 на 3 позиции ===");
        
        BigDecimal totalDiscount = new BigDecimal("10.00");
        int positions = 3;

        BigDecimal[] discounts = allocate(totalDiscount, positions);

        BigDecimal sumOfDiscounts = BigDecimal.ZERO;
        for (int i = 0; i < discounts.length; i++) {
            System.out.println("Позиция " + (i + 1) + ": скидка " + discounts[i]);
            sumOfDiscounts = sumOfDiscounts.add(discounts[i]);
        }

        System.out.println("Сумма скидок: " + sumOfDiscounts);
        System.out.println("Ожидалось:   " + totalDiscount);
        System.out.println("Совпадает:   " + (sumOfDiscounts.compareTo(totalDiscount) == 0));
    }

    private BigDecimal[] allocate(BigDecimal total, int count) {
        BigDecimal[] result = new BigDecimal[count];
        
        BigDecimal base = total.divide(new BigDecimal(count), 2, RoundingMode.DOWN);
        
        BigDecimal allocated = base.multiply(new BigDecimal(count));
        BigDecimal remainder = total.subtract(allocated);

        for (int i = 0; i < count; i++) {
            result[i] = base;
        }
        
        result[0] = result[0].add(remainder);

        return result;
    }
}