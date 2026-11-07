package java3;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public final class OutputCapture implements AutoCloseable {

    private final PrintStream original = System.out;
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    public OutputCapture() {
        System.setOut(new PrintStream(buffer));
    }

    public String text() {
        return buffer.toString();
    }

    @Override
    public void close() {
        System.setOut(original);
    }
}
