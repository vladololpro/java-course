package java2.errors;

/* Класс для демонстрации чтения stack trace. */
public class StackTraceDemo {

    public void run() {
        try {
            methodA();
        } catch (Exception e) {
            System.out.println("Тип: " + e.getClass().getName());
            System.out.println("Сообщение: " + e.getMessage());
            System.out.println("Где возникло (первый элемент): " + e.getStackTrace()[0]);
            System.out.println("Цепочка вызовов:");
            for (StackTraceElement element : e.getStackTrace()) {
                System.out.println("  at " + element);
            }
        }
    }

    private void methodA() {
        methodB();
    }

    private void methodB() {
        methodC();
    }

    private void methodC() {
        methodD();
    }

    private void methodD() {
        int result = 10 / 0;
    }
}