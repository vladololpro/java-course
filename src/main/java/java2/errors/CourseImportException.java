package java2.errors;

/* Бизнес-исключение уровня импорта. */
public class CourseImportException extends Exception {
    private final int recordNumber;

    public CourseImportException(int recordNumber, String message, Throwable cause) {
        super("Import failed at record " + recordNumber + ": " + message, cause);
        this.recordNumber = recordNumber;
    }

    public int getRecordNumber() { return recordNumber; }
}