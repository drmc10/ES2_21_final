import org.junit.jupiter.api.*;

import java.io.*;
import java.util.ArrayList;

public class APIFake {
    static API api;
    static InputStream originalIn = null;
    static PrintStream originalOut = null;
    static ByteArrayOutputStream outputStream = null;

    @BeforeAll
    public static void setUp() {
        api = Server.INSTANCE;
        originalIn = System.in;
        originalOut = System.out;
    }

    @BeforeEach
    public void eachSetUp() {
        BookDatabase.INSTANCE.reset();
        UserDatabase.INSTANCE.reset();
        outputStream = new ByteArrayOutputStream();
    }

    @Test
    public void testGetBookDataBase() throws MissingTitleException, IncorrectPageNumber, MissingPublisherException {
        ArrayList<Ebook> bookList = new ArrayList<>();
        bookList.add(new Epub("The Art Of War", "Filiquarian", 110));
        bookList.add(new Pdf("Quantum Physics for Hippies", "Independent", 175));
        bookList.add(new Epub("Java For Dummies", "For Dummies", 504));
        bookList.add(new Pdf("Design Patterns: Elements of Reusable Object-Oriented Software", "Addison-Wesley Professional", 416));
        bookList.add(new Epub("The Pragmatic Programmer", "Addison-Wesley Professional", 352));
        bookList.add(new Pdf("Blockchain For Dummies", "For Dummies", 256));

        ArrayList<Ebook> receivedList = api.getBookDataBase();
        Assertions.assertNotNull(receivedList);
        Assertions.assertEquals(bookList.size(), receivedList.size());

        boolean exists;
        for (Ebook ebook : bookList) {
            System.out.println(ebook.getTitle());
            exists = false;
            for(Ebook ebook2 : receivedList) {
                if(ebook.getHash() == ebook2.getHash()) {
                    exists = true;
                    Assertions.assertEquals(ebook.getTitle(), ebook2.getTitle());
                    Assertions.assertEquals(ebook.getPublisher(), ebook2.getPublisher());
                    Assertions.assertEquals(ebook.getSize(), ebook2.getSize());
                    Assertions.assertEquals(ebook.getPagesNumber(), ebook2.getPagesNumber());
                }
            }

            if(!exists)
                Assertions.fail();
        }
    }

    @Test
    public void testBlockUserWrongId() {
        Assertions.assertThrows(UserDoesntExistException.class, () -> api.blockUser("-1"));
    }

    @Test
    public void testBlockUserOk() {
        Assertions.assertDoesNotThrow(() -> {
            User user = api.login("testUser1", "testUser1");
            api.blockUser(user.getId());
            Assertions.assertTrue(user.isInactive());
        });
    }

    @AfterEach
    public void cleanUpEach() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }
}
