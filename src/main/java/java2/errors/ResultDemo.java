package java2.errors;

import java.util.Optional;

public class ResultDemo {

    public void run() {
        System.out.println("1. findCourse (Optional - отсутствие это норма):");
        System.out.println("  Найден: " + findCourse(10L).map(c -> "Course " + c).orElse("Не найден"));

        System.out.println("\n2. calculatePrice (Unchecked - баг в коде):");
        try {
            calculatePrice(-100);
        } catch (IllegalArgumentException e) {
            System.out.println("  Ошибка: " + e.getMessage());
        }
    }

    private Optional<String> findCourse(long id) {
        if (id > 0) return Optional.of("Java");
        return Optional.empty();
    }

    private int calculatePrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной: " + price);
        }
        return price;
    }
}