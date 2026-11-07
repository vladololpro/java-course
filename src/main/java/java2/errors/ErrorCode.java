package java2.errors;

public final class ErrorCode {

    public static final String INVALID_STUDENT_OR_COURSE = "INVALID_STUDENT_OR_COURSE";
    public static final String COURSE_ARCHIVED = "COURSE_ARCHIVED";
    public static final String COURSE_CLOSED = "COURSE_CLOSED";
    public static final String ALREADY_ENROLLED = "ALREADY_ENROLLED";
    public static final String PREREQUISITE_MISSING = "PREREQUISITE_MISSING";
    public static final String NO_SEATS = "NO_SEATS";
    public static final String IMPORT_MALFORMED_RECORD = "IMPORT_MALFORMED_RECORD";
    public static final String IMPORT_IO_ERROR = "IMPORT_IO_ERROR";

    private ErrorCode() {
    }
}