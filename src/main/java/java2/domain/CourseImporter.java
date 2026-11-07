package java2.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import java2.errors.CourseImportException;
import java2.errors.ErrorCode;

/* Импортер курсов из Reader с проверкой ошибок. */
public class CourseImporter {

    public List<String> importCourses(Reader reader) throws CourseImportException {
        List<String> result = new ArrayList<>();
        int processedRecords = 0;

        try (BufferedReader br = new BufferedReader(reader)) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (!line.contains(":")) {
                    throw new CourseImportException(
                        lineNumber,
                        processedRecords,
                        ErrorCode.IMPORT_MALFORMED_RECORD,
                        "Malformed record",
                        null
                    );
                }
                result.add(line);
                processedRecords++;
            }
        } catch (IOException e) {
            throw new CourseImportException(
                0,
                processedRecords,
                ErrorCode.IMPORT_IO_ERROR,
                "I/O error: " + e.getMessage(),
                e
            );
        }
        return result;
    }
}