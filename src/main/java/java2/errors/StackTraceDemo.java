package java2.errors;

/* Класс для демонстрации чтения stack trace. */
public class StackTraceDemo {

    public void run() {
        try {
            methodA(0);
        } catch (ArithmeticException e) {
            printTrace(e);
        }

        System.out.println("\nПосле исправления причины:");
        System.out.println("Результат: " + methodA(5));
    }

    private void printTrace(ArithmeticException e) {
        System.out.println("Тип: " + e.getClass().getName());
        System.out.println("Сообщение: " + e.getMessage());
        System.out.println("Где возникло (первый элемент): " + e.getStackTrace()[0]);
        System.out.println("Цепочка вызовов:");
        for (StackTraceElement element : e.getStackTrace()) {
            System.out.println("  at " + element);
        }
    }

    private int methodA(int divisor) {
        return methodB(divisor);
    }

    private int methodB(int divisor) {
        return methodC(divisor);
    }

    private int methodC(int divisor) {
        return methodD(divisor);
    }

    private int methodD(int divisor) {
        return 10 / divisor;
    }
}