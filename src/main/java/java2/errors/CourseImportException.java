package java2.errors;

/* Бизнес-исключение уровня импорта. */
public class CourseImportException extends Exception {
    private final int recordNumber;
    private final int processedRecords;
    private final String errorCode;

    public CourseImportException(int recordNumber, String message, Throwable cause) {
        this(recordNumber, 0, ErrorCode.IMPORT_MALFORMED_RECORD, message, cause);
    }

    public CourseImportException(int recordNumber, int processedRecords, String errorCode, String message, Throwable cause) {
        super("Import failed at record " + recordNumber + ": " + message, cause);
        this.recordNumber = recordNumber;
        this.processedRecords = processedRecords;
        this.errorCode = errorCode;
    }

    public int getRecordNumber() { return recordNumber; }
    public int getProcessedRecords() { return processedRecords; }
    public String getErrorCode() { return errorCode; }
}