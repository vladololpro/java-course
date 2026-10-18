package java4.numbers;

import java.math.BigDecimal;

/* Класс для демонстрации создания BigDecimal и расчета стоимости. */
public class DecimalDemo {

    public void run() {
        System.out.println("=== 1. Создание BigDecimal ===");
        
        BigDecimal fromDouble = new BigDecimal(0.1);
        System.out.println("new BigDecimal(0.1): " + fromDouble);
        
        BigDecimal fromValueOf = BigDecimal.valueOf(0.1);
        System.out.println("BigDecimal.valueOf(0.1): " + fromValueOf);
        
        BigDecimal fromString = new BigDecimal("0.1");
        System.out.println("new BigDecimal(\"0.1\"): " + fromString);

        System.out.println("\n=== 2. Расчет стоимости трех курсов ===");
        BigDecimal price1 = new BigDecimal("199.99");
        BigDecimal price2 = new BigDecimal("299.99");
        BigDecimal price3 = new BigDecimal("149.99");

        BigDecimal total = price1.add(price2).add(price3);
        System.out.println("Стоимость 1: " + price1);
        System.out.println("Стоимость 2: " + price2);
        System.out.println("Стоимость 3: " + price3);
        System.out.println("Итого: " + total);

        System.out.println("\n=== 3. Ловушка double ===");
        double d1 = 0.1;
        double d2 = 0.2;
        System.out.println("0.1 + 0.2 = " + (d1 + d2));
        System.out.println("(Для сравнения через BigDecimal: " + new BigDecimal("0.1").add(new BigDecimal("0.2")) + ")");
    }
}