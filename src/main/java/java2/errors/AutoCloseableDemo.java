package java2.errors;

/* Тестовый ресурс, который логирует свои вызовы. */
class TestResource implements AutoCloseable {
    private final String name;
    private final boolean failOnClose;

    public TestResource(String name, boolean failOnClose) {
        this.name = name;
        this.failOnClose = failOnClose;
        System.out.println("  [OPEN] " + name);
    }

    public void use() {
        System.out.println("  [USE] " + name);
    }

    @Override
    public void close() {
        System.out.println("  [CLOSE] " + name);
        if (failOnClose) {
            throw new RuntimeException("Ошибка при закрытии " + name);
        }
    }
}

public class AutoCloseableDemo {
    public void run() {
        System.out.println("1. Успешное выполнение:");
        try (TestResource res = new TestResource("Resource-1", false)) {
            res.use();
        }

        System.out.println("\n2. Исключение в работе:");
        try (TestResource res = new TestResource("Resource-2", false)) {
            res.use();
            throw new IllegalStateException("Ошибка в работе!");
        } catch (Exception e) {
            System.out.println("  [CATCH] " + e.getMessage());
        }

        System.out.println("\n3. Ошибка и в работе, и в close (suppressed):");
        try (TestResource res = new TestResource("Resource-3", true)) {
            res.use();
            throw new IllegalStateException("Главная ошибка в работе!");
        } catch (Exception e) {
            System.out.println("  [CATCH] Основное: " + e.getMessage());
            for (Throwable suppressed : e.getSuppressed()) {
                System.out.println("  [SUPPRESSED] " + suppressed.getMessage());
            }
        }
    }
}