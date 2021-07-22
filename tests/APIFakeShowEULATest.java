import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class APIFakeShowEULATest {
    static API api = APIFake.INSTANCE;

    @Test
    public void testShowEULABookNull() {
        Assertions.assertThrows(NullParameterException.class, () -> api.showEULA(null));
    }

    @Test
    public void testShowEULA() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("0\n".getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setIn(inputStream);
        System.setOut(new PrintStream(outputStream));
        Assertions.assertDoesNotThrow(() -> api.showEULA(new Ebook("The Art Of War", "Filiquarian", 110,
                "1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", 1000, ".pdf")));
        Assertions.assertEquals("This is 'Filiquarian' terms of responsibility." +
                "Choose an option" +
                "1. Accept" +
                "2. Decline" +
                "Please choose a valid option".replace("\r", ""), outputStream.toString()
                .replace("\r", "")
                .replace("\n", ""));
    }
}
