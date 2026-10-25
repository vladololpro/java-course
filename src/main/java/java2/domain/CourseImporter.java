package java2.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/* Импортер курсов из Reader с проверкой ошибок. */
public class CourseImporter {

    public List<String> importCourses(Reader reader) throws IOException {
        List<String> result = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(reader)) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (!line.contains(":")) {
                    throw new IOException("Malformed record at line " + lineNumber);
                }
                result.add(line);
            }
        }
        return result;
    }
}