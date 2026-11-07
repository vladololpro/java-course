package java2.errors;

/* Демонстрация порядка try-catch-finally. */
public class TryCatchFinallyDemo {
    public void run() {
        System.out.println("1. Возврат из try:");
        System.out.println("Результат: " + returnFromTry());

        System.out.println("\n2. Исключение в try, ловится в catch:");
        System.out.println("Результат: " + returnFromCatch());

        System.out.println("\n3. return в finally скрывает исключение (антипаттерн):");
        System.out.println("Результат: " + hideException());

        System.out.println("\n4. То же исключение без return в finally:");
        try {
            throwFromTry();
        } catch (RuntimeException e) {
            System.out.println("Поймано снаружи: " + e.getMessage());
        }
    }

    private String returnFromTry() {
        try {
            System.out.println("  try");
            return "try";
        } finally {
            System.out.println("  finally");
        }
    }

    private String returnFromCatch() {
        try {
            System.out.println("  try");
            throw new RuntimeException("test");
        } catch (RuntimeException e) {
            System.out.println("  catch");
            return "catch";
        } finally {
            System.out.println("  finally");
        }
    }

    private String hideException() {
        try {
            System.out.println("  try");
            throw new RuntimeException("Исходное исключение");
        } finally {
            System.out.println("  finally");
            return "finally";
        }
    }

    private void throwFromTry() {
        try {
            System.out.println("  try");
            throw new RuntimeException("Исходное исключение");
        } finally {
            System.out.println("  finally");
        }
    }
}