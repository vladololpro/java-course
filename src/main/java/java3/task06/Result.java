package java3.task06;

/* Generic-контейнер результата: либо значение типа T (успех),
 * либо сообщение об ошибке (провал). */
public class Result<T> {

    private final T value;
    private final String message;
    private final boolean success;

    private Result(T value, String message, boolean success) {
        this.value = value;
        this.message = message;
        this.success = success;
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(value, null, true);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(null, message, false);
    }

    public boolean isSuccess() {
        return success;
    }

    public T value() {
        return value;
    }

    public String message() {
        return message;
    }
}