import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class APIFakeRequestBookTest {
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
        UserDatabase.INSTANCE.reset();
        outputStream = new ByteArrayOutputStream();
    }

    @Test
    public void testRequestBookWrongHash() {
        Assertions.assertThrows(BookDoesntExistException.class, () -> api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587363",
                api.login("testUser1", "testUser1").getId()));
    }

    @Test
    public void testRequestBookWrongUser() {
        Assertions.assertThrows(UserDoesntExistException.class, () -> api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364"
                , "0"));
    }

    @Test
    public void testRequestBookAlreadyLoaned() {
        Assertions.assertThrows(BookAlreadyLoanedException.class, () -> {
            System.setIn(new ByteArrayInputStream("1\n".getBytes()));
            api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                    api.login("testUser1", "testUser1").getId());
            api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                    api.login("testUser1", "testUser1").getId());
        });
    }

    @Test
    public void testRequestBookBlockedUSer() throws UserIsNotActiveException, EmptyUsernameException,
            UserDoesntExistException, EmptyPasswordException, IncorrectPasswordException,
            BookAlreadyLoanedException, BookDoesntExistException, RenewLimitExceeded, InvalidHashException {
        System.setOut(new PrintStream(outputStream));
        api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                api.login("testUser3", "testUser3").getId());
        Assertions.assertEquals(("Your account is currently blocked, you are unable to request the book")
                        .replace("\r", "").replace("\n", ""),
                outputStream.toString().replace("\r", "").replace("\n", ""));

    }

    @Test
    public void testRequestBookAcceptEULA() throws UserIsNotActiveException, EmptyUsernameException,
            UserDoesntExistException, EmptyPasswordException, IncorrectPasswordException,
            BookAlreadyLoanedException, BookDoesntExistException, RenewLimitExceeded, InvalidHashException {
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        System.setOut(new PrintStream(outputStream));
        User user = api.login("testUser1", "testUser1");
        api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364"
                , user.getId());

        Assertions.assertEquals(("This is 'Filiquarian' terms of responsibility." +
                        "Choose an option" +
                        "1. Accept" +
                        "2. Decline" +
                        "You have accepted the EULA")
                        .replace("\r", "").replace("\n", ""),
                outputStream.toString().replace("\r", "").replace("\n", ""));

        for (Loan loan : user.getLoanList()) {
            if(loan.getBookHash().equals("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364"))
                return;
        }

        Assertions.fail();
    }

    @Test
    public void testRequestBookDeclineEULA() throws UserIsNotActiveException, EmptyUsernameException,
            UserDoesntExistException, EmptyPasswordException, IncorrectPasswordException,
            BookAlreadyLoanedException, BookDoesntExistException, RenewLimitExceeded, InvalidHashException {
        System.setIn(new ByteArrayInputStream("2\n".getBytes()));
        System.setOut(new PrintStream(outputStream));
        api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364"
                , api.login("testUser1", "testUser1").getId());

        Assertions.assertEquals(("This is 'Filiquarian' terms of responsibility." +
                        "Choose an option1. Accept2. DeclineCannot request book without accepting EULA")
                        .replace("\r", "").replace("\n", ""),
                outputStream.toString().replace("\r", "").replace("\n", ""));
    }

    @Test
    public void testRequestBookWrongChoice() throws UserIsNotActiveException, EmptyUsernameException,
            UserDoesntExistException, EmptyPasswordException, IncorrectPasswordException,
            BookAlreadyLoanedException, BookDoesntExistException, RenewLimitExceeded, InvalidHashException {
        System.setIn(new ByteArrayInputStream("3\n".getBytes()));
        System.setOut(new PrintStream(outputStream));
        api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364"
                , api.login("testUser1", "testUser1").getId());

        Assertions.assertEquals(("This is 'Filiquarian' terms of responsibility." +
                        "Choose an option1. Accept2. DeclinePlease choose a valid option")
                        .replace("\r", "").replace("\n", ""),
                outputStream.toString().replace("\r", "").replace("\n", ""));
    }

    @AfterEach
    public void cleanUpEach() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }
}
