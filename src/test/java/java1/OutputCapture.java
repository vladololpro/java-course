package java1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

final class OutputCapture implements AutoCloseable {

    private final PrintStream original = System.out;
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    OutputCapture() {
        System.setOut(new PrintStream(buffer));
    }

    String text() {
        return buffer.toString();
    }

    @Override
    public void close() {
        System.setOut(original);
    }
}
