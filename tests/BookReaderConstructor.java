import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BookReaderConstructor {
    @Test
    public void testBookReaderBookNull() {
        Assertions.assertThrows(BookDoesntExistException.class, () -> new BookReader(null));
    }
}
