import org.junit.jupiter.api.*;

import java.io.*;

public class APIFakeReadBookTest {
    static API api = null;
    static InputStream originalIn = null;
    static PrintStream originalOut = null;
    static ByteArrayInputStream inputStream = null;
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
    public void testReadbookUserIdEmpty() {
        Assertions.assertThrows(MissingUserIdException.class, () -> api.readBook("",
                new Loan("14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38",14)));
    }

    @Test
    public void testReadBookUserIdOk() {
        inputStream = new ByteArrayInputStream("0\n".getBytes());
        System.setIn(inputStream);
       Assertions.assertDoesNotThrow(() -> api.readBook(api.login("testUser1", "testUser1").getId(),
               new Loan("14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38",14)));
    }

    @Test
    public void testReadBookUserIdAbove36() {
        inputStream = new ByteArrayInputStream("0\n".getBytes());
        System.setIn(inputStream);
        Assertions.assertThrows(TooManyCharsException.class, () -> api.readBook("1234567890123456789012345678901234567",
                new Loan("14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38",14)));
    }

    @Test
    public void testReadBookUserIdBellow36() {
        inputStream = new ByteArrayInputStream("0\n".getBytes());
        System.setIn(inputStream);
        Assertions.assertThrows(TooFewCharsException.class, () -> api.readBook("12345678901234567890123456789012345",
                new Loan("14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38",14)));
    }

    @Test
    public void testReadBookUserNull() {
        inputStream = new ByteArrayInputStream("0\n".getBytes());
        System.setIn(inputStream);
        Assertions.assertThrows(NullParameterException.class, () -> api.readBook(
                null,
                new Loan("14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38" , 14)));
    }

    @Test
    public void testReadBookLoanNull() {
        inputStream = new ByteArrayInputStream("0\n".getBytes());
        System.setIn(inputStream);
        Assertions.assertThrows(NullParameterException.class, () -> api.readBook(
                null,
                new Loan("14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38" , 14)));
    }

    @Test
    public void testReadBookExitReader() throws UserIsNotActiveException, EmptyUsernameException,
            UserDoesntExistException, EmptyPasswordException, IncorrectPasswordException,
            BookAlreadyLoanedException, BookDoesntExistException, RenewLimitExceeded, InvalidHashException, NullParameterException,
            TooFewCharsException, TooManyCharsException, MissingUserIdException {
        User user = api.login("testUser2", "testUser2");
        inputStream = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(inputStream);

        api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", user.getId());
        Loan loan = null;
        for (Loan loan1 : user.getLoanList()) {
            if (loan1.getBookHash().equals("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364")) {
                loan = loan1;
                break;
            }
        }
        Assertions.assertNotNull(loan);

        inputStream = new ByteArrayInputStream("0\n".getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outputStream));
        api.readBook(user.getId(), loan);
        Assertions.assertEquals("You are now reading 'The Art Of War'." +
                "You are reading page number 1 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page" +
                "0. Exit reader", outputStream.toString()
                .replace("\r", "").replace("\n", ""));
    }

    @Test
    public void testReadBookBlockedUser() throws UserIsNotActiveException, EmptyUsernameException,
            UserDoesntExistException, EmptyPasswordException, IncorrectPasswordException,
            BookAlreadyLoanedException, BookDoesntExistException, RenewLimitExceeded, InvalidHashException, NullParameterException,
            TooFewCharsException, TooManyCharsException, MissingUserIdException {
        User user = api.login("testUser2", "testUser2");
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));

        api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", user.getId());
        Loan loan = null;
        for (Loan loan1 : user.getLoanList()) {
            if (loan1.getBookHash().equals("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364")) {
                loan = loan1;
                break;
            }
        }
        Assertions.assertNotNull(loan);

        api.blockUser(user.getId());
        Assertions.assertTrue(user.isInactive());

        System.setOut(new PrintStream(outputStream));
        api.readBook(user.getId(), loan);
        Assertions.assertEquals("Your account is currently blocked, you are unable to read the book", outputStream.toString()
                .replace("\r", "").replace("\n", ""));
    }

    @Test
    public void testReadBookExpiredLoan() throws UserIsNotActiveException, EmptyUsernameException,
            UserDoesntExistException, EmptyPasswordException, IncorrectPasswordException, NullParameterException,
            TooFewCharsException, TooManyCharsException, MissingUserIdException {
        User user = api.login("testUser1", "testUser1");
        Loan loan = null;
        for (Loan loan1 : user.getLoanList()) {
            if (loan1.getBookHash().equals("14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38")) {
                loan = loan1;
                break;
            }
        }
        Assertions.assertNotNull(loan);

        System.setOut(new PrintStream(outputStream));
        api.readBook(user.getId(), loan);
        Assertions.assertEquals("The book is no longer available to be read, please renew your loan first", outputStream.toString()
                .replace("\r", "").replace("\n", ""));
    }

    @Test
    public void testReadBookOk() throws UserIsNotActiveException, EmptyUsernameException,
            UserDoesntExistException, EmptyPasswordException, IncorrectPasswordException,
            BookAlreadyLoanedException, BookDoesntExistException, RenewLimitExceeded, InvalidHashException, NullParameterException,
            TooFewCharsException, TooManyCharsException, MissingUserIdException {
        User user = api.login("testUser2", "testUser2");
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));

        api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", user.getId());
        Loan loan = null;
        for (Loan loan1 : user.getLoanList()) {
            if (loan1.getBookHash().equals("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364")) {
                loan = loan1;
                break;
            }
        }
        Assertions.assertNotNull(loan);

        System.setIn(new ByteArrayInputStream("0\n".getBytes()));
        System.setOut(new PrintStream(outputStream));
        api.readBook(user.getId(), loan);
        Assertions.assertEquals("You are now reading 'The Art Of War'.You are reading page number 1 of 110" +
                "1. Next page2. Previous page3. Go to certain page0. Exit reader", outputStream.toString()
                .replace("\r", "").replace("\n", ""));
    }

    @Test
    public void testReadBookNextPageInputError() throws UserIsNotActiveException, EmptyUsernameException,
            UserDoesntExistException, EmptyPasswordException, IncorrectPasswordException,
            BookAlreadyLoanedException, BookDoesntExistException, RenewLimitExceeded, InvalidHashException, NullParameterException,
            TooFewCharsException, TooManyCharsException, MissingUserIdException {
        User user = api.login("testUser2", "testUser2");
        inputStream = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(inputStream);

        api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", user.getId());
        Loan loan = null;
        for (Loan loan1 : user.getLoanList()) {
            if (loan1.getBookHash().equals("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364")) {
                loan = loan1;
                break;
            }
        }
        Assertions.assertNotNull(loan);

        inputStream = new ByteArrayInputStream("3\n110\n1\n0\n".getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outputStream));
        api.readBook(user.getId(), loan);
        Assertions.assertEquals("You are now reading 'The Art Of War'." +
                "You are reading page number 1 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page" +
                "0. Exit reader" +
                "Please enter the page number to go to" +
                "You are reading page number 110 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page" +
                "0. Exit reader" +
                "You are already on the last page of the book" +
                "You are reading page number 110 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page" +
                "0. Exit reader", outputStream.toString()
                .replace("\r", "").replace("\n", ""));
    }

    @Test
    public void testReadBookNextPageInputOk() throws UserIsNotActiveException, EmptyUsernameException,
            UserDoesntExistException, EmptyPasswordException, IncorrectPasswordException,
            BookAlreadyLoanedException, BookDoesntExistException, RenewLimitExceeded, InvalidHashException, NullParameterException,
            TooFewCharsException, TooManyCharsException, MissingUserIdException {
        User user = api.login("testUser2", "testUser2");
        inputStream = new ByteArrayInputStream("1\n0\n".getBytes());
        System.setIn(inputStream);

        api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", user.getId());
        Loan loan = null;
        for (Loan loan1 : user.getLoanList()) {
            if (loan1.getBookHash().equals("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364")) {
                loan = loan1;
                break;
            }
        }
        Assertions.assertNotNull(loan);

        inputStream = new ByteArrayInputStream("1\n0\n".getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outputStream));
        api.readBook(user.getId(), loan);
        Assertions.assertEquals("You are now reading 'The Art Of War'." +
                "You are reading page number 1 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page0. Exit reader" +
                "You are reading page number 2 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page0. Exit reader", outputStream.toString()
                .replace("\r", "").replace("\n", ""));
    }

    @Test
    public void testReadBookPreviousPageInputError() throws UserIsNotActiveException, EmptyUsernameException,
            UserDoesntExistException, EmptyPasswordException, IncorrectPasswordException,
            BookAlreadyLoanedException, BookDoesntExistException, RenewLimitExceeded, InvalidHashException, NullParameterException,
            TooFewCharsException, TooManyCharsException, MissingUserIdException {
        User user = api.login("testUser2", "testUser2");
        inputStream = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(inputStream);

        api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", user.getId());
        Loan loan = null;
        for (Loan loan1 : user.getLoanList()) {
            if (loan1.getBookHash().equals("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364")) {
                loan = loan1;
                break;
            }
        }
        Assertions.assertNotNull(loan);

        inputStream = new ByteArrayInputStream("2\n0\n".getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outputStream));
        api.readBook(user.getId(), loan);
        Assertions.assertEquals("You are now reading 'The Art Of War'." +
                "You are reading page number 1 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page" +
                "0. Exit reader" +
                "You are already on the first page of the book" +
                "You are reading page number 1 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page" +
                "0. Exit reader", outputStream.toString()
                .replace("\r", "").replace("\n", ""));
    }

    @Test
    public void testReadBookPreviousPageInputOk() throws UserIsNotActiveException, EmptyUsernameException,
            UserDoesntExistException, EmptyPasswordException, IncorrectPasswordException,
            BookAlreadyLoanedException, BookDoesntExistException, RenewLimitExceeded, InvalidHashException, NullParameterException,
            TooFewCharsException, TooManyCharsException, MissingUserIdException {
        User user = api.login("testUser2", "testUser2");
        inputStream = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(inputStream);

        api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", user.getId());
        Loan loan = null;
        for (Loan loan1 : user.getLoanList()) {
            if (loan1.getBookHash().equals("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364")) {
                loan = loan1;
                break;
            }
        }
        Assertions.assertNotNull(loan);

        inputStream = new ByteArrayInputStream("1\n2\n0\n".getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outputStream));
        api.readBook(user.getId(), loan);
        Assertions.assertEquals("You are now reading 'The Art Of War'." +
                "You are reading page number 1 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page" +
                "0. Exit reader" +
                "You are reading page number 2 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page" +
                "0. Exit reader" +
                "You are reading page number 1 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page" +
                "0. Exit reader", outputStream.toString()
                .replace("\r", "").replace("\n", ""));
    }

    @Test
    public void testReadBookGotoPagePageInputErrorNegativePageNumber() throws UserIsNotActiveException, EmptyUsernameException,
            UserDoesntExistException, EmptyPasswordException, IncorrectPasswordException,
            BookAlreadyLoanedException, BookDoesntExistException, RenewLimitExceeded, InvalidHashException, NullParameterException,
            TooFewCharsException, TooManyCharsException, MissingUserIdException {
        User user = api.login("testUser2", "testUser2");
        inputStream = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(inputStream);

        api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", user.getId());
        Loan loan = null;
        for (Loan loan1 : user.getLoanList()) {
            if (loan1.getBookHash().equals("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364")) {
                loan = loan1;
                break;
            }
        }
        Assertions.assertNotNull(loan);

        inputStream = new ByteArrayInputStream("3\n-5\n0\n".getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outputStream));
        api.readBook(user.getId(), loan);
        Assertions.assertEquals("You are now reading 'The Art Of War'." +
                "You are reading page number 1 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page" +
                "0. Exit reader" +
                "Please enter the page number to go to" +
                "Please enter a page number between 1 and 110" +
                "You are reading page number 1 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page" +
                "0. Exit reader", outputStream.toString()
                .replace("\r", "").replace("\n", ""));
    }

    @Test
    public void testReadBookGotoPagePageInputErrorPage0() throws UserIsNotActiveException, EmptyUsernameException,
            UserDoesntExistException, EmptyPasswordException, IncorrectPasswordException,
            BookAlreadyLoanedException, BookDoesntExistException, RenewLimitExceeded, InvalidHashException, NullParameterException,
            TooFewCharsException, TooManyCharsException, MissingUserIdException {
        User user = api.login("testUser2", "testUser2");
        inputStream = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(inputStream);

        api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", user.getId());
        Loan loan = null;
        for (Loan loan1 : user.getLoanList()) {
            if (loan1.getBookHash().equals("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364")) {
                loan = loan1;
                break;
            }
        }
        Assertions.assertNotNull(loan);

        inputStream = new ByteArrayInputStream("3\n0\n0\n".getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outputStream));
        api.readBook(user.getId(), loan);
        Assertions.assertEquals("You are now reading 'The Art Of War'." +
                "You are reading page number 1 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page" +
                "0. Exit reader" +
                "Please enter the page number to go to" +
                "Please enter a page number between 1 and 110" +
                "You are reading page number 1 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page" +
                "0. Exit reader", outputStream.toString()
                .replace("\r", "").replace("\n", ""));
    }

    @Test
    public void testReadBookGotoPagePageInputErrorAboveMaxPage() throws UserIsNotActiveException, EmptyUsernameException,
            UserDoesntExistException, EmptyPasswordException, IncorrectPasswordException,
            BookAlreadyLoanedException, BookDoesntExistException, RenewLimitExceeded, InvalidHashException, NullParameterException,
            TooFewCharsException, TooManyCharsException, MissingUserIdException {
        User user = api.login("testUser2", "testUser2");
        inputStream = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(inputStream);

        api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", user.getId());
        Loan loan = null;
        for (Loan loan1 : user.getLoanList()) {
            if (loan1.getBookHash().equals("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364")) {
                loan = loan1;
                break;
            }
        }
        Assertions.assertNotNull(loan);

        inputStream = new ByteArrayInputStream("3\n111\n0\n".getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outputStream));
        api.readBook(user.getId(), loan);
        Assertions.assertEquals("You are now reading 'The Art Of War'." +
                "You are reading page number 1 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page" +
                "0. Exit reader" +
                "Please enter the page number to go to" +
                "Please enter a page number between 1 and 110" +
                "You are reading page number 1 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page" +
                "0. Exit reader", outputStream.toString()
                .replace("\r", "").replace("\n", ""));
    }

    @Test
    public void testReadBookGotoPagePageInputOk() throws UserIsNotActiveException, EmptyUsernameException,
            UserDoesntExistException, EmptyPasswordException, IncorrectPasswordException,
            BookAlreadyLoanedException, BookDoesntExistException, RenewLimitExceeded, InvalidHashException, NullParameterException,
            TooFewCharsException, TooManyCharsException, MissingUserIdException {
        User user = api.login("testUser2", "testUser2");
        inputStream = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(inputStream);

        api.requestBook("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", user.getId());
        Loan loan = null;
        for (Loan loan1 : user.getLoanList()) {
            if (loan1.getBookHash().equals("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364")) {
                loan = loan1;
                break;
            }
        }
        Assertions.assertNotNull(loan);

        inputStream = new ByteArrayInputStream("3\n50\n0\n".getBytes());
        System.setIn(inputStream);
        System.setOut(new PrintStream(outputStream));
        api.readBook(user.getId(), loan);
        Assertions.assertEquals("You are now reading 'The Art Of War'." +
                "You are reading page number 1 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page" +
                "0. Exit reader" +
                "Please enter the page number to go to" +
                "You are reading page number 50 of 110" +
                "1. Next page" +
                "2. Previous page" +
                "3. Go to certain page" +
                "0. Exit reader", outputStream.toString()
                .replace("\r", "").replace("\n", ""));
    }

    @AfterEach
    public void cleanUpEach() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }
}
