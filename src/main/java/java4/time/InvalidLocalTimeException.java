package java4.time;

/* Исключение для несуществующего локального времени (попадание в DST-gap). */
public class InvalidLocalTimeException extends RuntimeException {
    public InvalidLocalTimeException(String message) {
        super(message);
    }
}