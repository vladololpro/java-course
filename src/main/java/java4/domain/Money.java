package java4.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* Неизменяемый класс для работы с деньгами. */
public final class Money {

    private final BigDecimal amount;
    private final String currency;

    public Money(BigDecimal amount, String currency) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    private void checkCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Нельзя смешивать валюты: " + this.currency + " и " + other.currency);
        }
    }

    public Money add(Money other) {
        checkCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money subtract(Money other) {
        checkCurrency(other);
        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    public Money percentage(BigDecimal percent) {
        BigDecimal result = this.amount.multiply(percent).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        return new Money(result, this.currency);
    }

    public List<Money> allocation(int parts) {
        if (parts <= 0) throw new IllegalArgumentException("Количество частей должно быть > 0");
        List<Money> result = new ArrayList<>();
        
        BigDecimal base = this.amount.divide(new BigDecimal(parts), 2, RoundingMode.DOWN);
        BigDecimal totalAllocated = base.multiply(new BigDecimal(parts));
        BigDecimal remainder = this.amount.subtract(totalAllocated);
        
        for (int i = 0; i < parts; i++) {
            if (i == 0) {
                result.add(new Money(base.add(remainder), this.currency));
            } else {
                result.add(new Money(base, this.currency));
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return this.currency.equals(money.currency) && this.amount.compareTo(money.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount.stripTrailingZeros(), currency);
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }

    public void run() {
        System.out.println("=== 1. Создание и округление ===");
        Money price = new Money(new BigDecimal("199.999"), "RUB");
        System.out.println("Создано: " + price + " (округлено)");

        System.out.println("\n=== 2. Сложение и вычитание ===");
        Money a = new Money(new BigDecimal("100.00"), "RUB");
        Money b = new Money(new BigDecimal("50.50"), "RUB");
        System.out.println(a + " + " + b + " = " + a.add(b));
        System.out.println(a + " - " + b + " = " + a.subtract(b));

        System.out.println("\n=== 3. Проценты (скидка 10%) ===");
        Money discount = a.percentage(new BigDecimal("10"));
        System.out.println("10% от " + a + " = " + discount);

        System.out.println("\n=== 4. Распределение (100 RUB на 3 части) ===");
        List<Money> parts = a.allocation(3);
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < parts.size(); i++) {
            System.out.println("Часть " + (i + 1) + ": " + parts.get(i));
            sum = sum.add(parts.get(i).getAmount());
        }
        System.out.println("Сумма частей: " + sum + " RUB");

        System.out.println("\n=== 5. Запрет смешения валют ===");
        Money usd = new Money(new BigDecimal("100.00"), "USD");
        try {
            a.add(usd);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        System.out.println("\n=== 6. Содержательное равенство ===");
        Money m1 = new Money(new BigDecimal("100.0"), "RUB");
        Money m2 = new Money(new BigDecimal("100.00"), "RUB");
        System.out.println(m1 + " equals " + m2 + " : " + m1.equals(m2));
    }
}