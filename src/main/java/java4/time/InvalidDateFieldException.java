package java4.time;

/* Исключение для невалидных полей даты. */
public class InvalidDateFieldException extends RuntimeException {
    private final String fieldName;
    private final String inputValue;

    public InvalidDateFieldException(String fieldName, String inputValue, String message) {
        super(message);
        this.fieldName = fieldName;
        this.inputValue = inputValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getInputValue() {
        return inputValue;
    }
}