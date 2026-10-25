package java2.errors;

public class ExceptionTranslationDemo {
    public void run() {
        try {
            importRecord("JAVA-101", 1);
            importRecord("INVALID_NUMBER", 2);
        } catch (CourseImportException e) {
            System.out.println("Ошибка: " + e.getMessage());
            System.out.println("Запись №: " + e.getRecordNumber());
            System.out.println("Причина: " + e.getCause().getClass().getSimpleName());
        }
    }

    private void importRecord(String input, int recordNumber) throws CourseImportException {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new CourseImportException(recordNumber, "Invalid number format", e);
        }
    }
}