import org.junit.jupiter.api.*;

import java.io.*;
import java.util.ArrayList;

public class APIFakeTest {
    static API api;
    static InputStream originalIn = null;
    static PrintStream originalOut = null;
    static ByteArrayOutputStream outputStream = null;

    @BeforeAll
    public static void setUp() {
        api = APIFake.INSTANCE;
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
    public void testGetBookDataBase() throws MissingTitleException, IncorrectPageNumber, MissingPublisherException,
            InvalidFileSizeException, InvalidFileExtension, InvalidHashException {
        ArrayList<Ebook> bookList = new ArrayList<>();
        bookList.add(new Ebook("The Art Of War", "Filiquarian", 110,
                "1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", 1000, ".pdf"));
        bookList.add(new Ebook("Quantum Physics for Hippies", "Independent", 175,
                "2A72ED4386065E4ABE91AF5682A7D310BF280798B06DE1DC18D457AC80A931C4", 2000, ".epub"));
        bookList.add(new Ebook("Java For Dummies", "For Dummies", 504,
                "16BC9EF7327955D701A1EBF7E8A1CA413E92A22A0C6F4F865CFF9D46380B5F7C", 3000, ".pdf"));
        bookList.add(new Ebook("Design Patterns: Elements of Reusable Object-Oriented Software",
                "Addison-Wesley Professional", 416,
                "EB1BBEAEDCB50107A55AC049B62384B6A0BCFBF5E2988BEF9536FC5C27404D5A", 6000, ".epub"));
        bookList.add(new Ebook("The Pragmatic Programmer", "Addison-Wesley Professional",
                352,
                "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 4000, ".pdf"));
        bookList.add(new Ebook("Blockchain For Dummies", "For Dummies", 256,
                "54C2495510FAE68DE0196C365C1D3626041C5BDDBA70CEF16885F5EC2FC66883", 5000, ".epub"));

        ArrayList<Ebook> receivedList = api.getBookDataBase();
        Assertions.assertNotNull(receivedList);
        Assertions.assertEquals(bookList.size(), receivedList.size());

        boolean exists;
        for (Ebook ebook : bookList) {
            System.out.println(ebook.getTitle());
            exists = false;
            for(Ebook ebook2 : receivedList) {
                if(ebook.getHash().equals(ebook2.getHash())) {
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
