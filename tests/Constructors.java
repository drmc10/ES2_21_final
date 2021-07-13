import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

public class Constructors {
    //Epub Class
    @Test
    public void testEpubMissingTitle() {
        Assertions.assertThrows(MissingTitleException.class, () -> new Epub("", "Publisher", 50));
    }

    @Test
    public void testEpubMissingPublisher() {
        Assertions.assertThrows(MissingPublisherException.class, () ->  new Epub("Title", "", 50));
    }

    @Test
    public void testEpubNegativePages() {
        Assertions.assertThrows(IncorrectPageNumber.class, () ->new Epub("Title", "Publisher", -50));
    }

    @Test
    public void testEpubZeroPages() {
        Assertions.assertThrows(IncorrectPageNumber.class, () -> new Epub("Title", "Publisher", 0));
    }

    //Pdf Class
    @Test
    public void testPdfMissingTitle() {
        Assertions.assertThrows(MissingTitleException.class, () -> new Pdf("", "Publisher", 50));
    }

    @Test
    public void testPdfMissingPublisher() {
        Assertions.assertThrows(MissingPublisherException.class, () -> new Pdf("Title", "", 50));
    }

    @Test
    public void testPdfNegativePages() {
        Assertions.assertThrows(IncorrectPageNumber.class, () -> new Pdf("Title", "Publisher", -50));
    }

    @Test
    public void testPdfZeroPages() {
        Assertions.assertThrows(IncorrectPageNumber.class, () -> new Pdf("Title", "Publisher", 0));
    }

    //BookReader Class
    @Test
    public void testBookReaderBookNull() {
        Assertions.assertThrows(BookDoesntExistException.class, () -> new BookReader(null));
    }

    //Loan Class to finish
    @Test
    public void testLoanConstWrongHash() {
        Assertions.assertThrows(BookDoesntExistException.class, () -> new Loan(123, 14));
    }

    @Test
    public void testLoanConsCorrect() {
        Assertions.assertDoesNotThrow(() -> {
            Loan loan = new Loan("The Art Of War".hashCode(), 14);
            Assertions.assertEquals("The Art Of War".hashCode(), loan.getBookHash());
        });
    }

    @Test
    public void testLoanConstWrongHash2() {
        Assertions.assertThrows(BookDoesntExistException.class, () -> new Loan(123,new Date(), 14));
    }

    @Test
    public void testLoanConsCorrect2() {
        Assertions.assertDoesNotThrow(() -> {
            Date currentDate = new Date();
            int numberOfDays = 14;

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, -(numberOfDays * 2));
            Date issuedDate = calendar.getTime();

            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, -numberOfDays);
            Date dateToReturn = calendar.getTime();

            Loan loan = new Loan("The Art Of War".hashCode(), currentDate, numberOfDays);
            Assertions.assertEquals("The Art Of War".hashCode(), loan.getBookHash());
            Assertions.assertEquals(issuedDate.getTime(), loan.getIssuedDate().getTime());
            Assertions.assertEquals(dateToReturn, loan.getDateToReturn());
        });
    }

    //User
    @Test
    public void testUserCons1NameNull() {
        Assertions.assertThrows(MissingUsernameException.class, () ->
                new User(null, "NelsonCortes"));
    }

    @Test
    public void testUserCons1NameTooFewChars() {
        Assertions.assertThrows(TooFewCharsUsernameException.class, () ->
                new User("12345", "NelsonCortes"));
    }

    @Test
    public void testUserCons1NameTooManyChars() {
        Assertions.assertThrows(TooManyCharsUsernameException.class, () ->
                new User("1234567890123456789012345", "NelsonCortes"));
    }

    @Test
    public void testUserCons1PassNull() {
        Assertions.assertThrows(MissingPasswordException.class, () ->
                new User("Nelson", null));
    }

    @Test
    public void testUserCons1PassTooFewChars() {
        Assertions.assertThrows(TooFewCharsUsernameException.class, () ->
                new User("Nelson", "12345"));
    }

    @Test
    public void testUserCons1PassTooManyChars() {
        Assertions.assertThrows(TooManyCharsUsernameException.class, () ->
                new User("Nelson", "1234567890123456789012345"));
    }

    @Test
    public void testUserCons1BothNull() {
        Assertions.assertThrows(MissingUsernameException.class, () ->
                new User(null, null));
    }

    @Test
    public void testUserCons1NameEmpty() {
        Assertions.assertThrows(EmptyUsernameException.class, () ->
                new User("", "NelsonCortes"));
    }

    @Test
    public void testUserCons1PassEmpty() {
        Assertions.assertThrows(EmptyPasswordException.class, () ->
                new User("Nelson", ""));
    }

    @Test
    public void testUserCons1BothEmpty() {
        Assertions.assertThrows(EmptyUsernameException.class, () ->
                new User("", ""));
    }

    @Test
    public void testUserCons1Ok() throws MissingPasswordException, EmptyPasswordException,
            MissingUsernameException, EmptyUsernameException, TooFewCharsUsernameException,
            TooManyCharsUsernameException {
        User user = new User("Nelson", "NelsonCortes");
        Assertions.assertEquals("Nelson", user.getUsername());
        Assertions.assertEquals("NelsonCortes", user.getPassword());
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(0, user.getLoanList().size());
        Assertions.assertFalse(user.isInactive());
    }

    @Test
    public void testUserCons2NameNull() {
        Assertions.assertThrows(MissingUsernameException.class, () ->
                new User(null, "NelsonCortes", true));
    }

    @Test
    public void testUserCons2NameTooFewChars() {
        Assertions.assertThrows(TooFewCharsUsernameException.class, () ->
                new User("12345", "NelsonCortes", true));
    }

    @Test
    public void testUserCons2NameTooManyChars() {
        Assertions.assertThrows(TooManyCharsUsernameException.class, () ->
                new User("1234567890123456789012345", "NelsonCortes", true));
    }

    @Test
    public void testUserCons2PassNull() {
        Assertions.assertThrows(MissingPasswordException.class, () ->
                new User("Nelson", null, true));
    }

    @Test
    public void testUserCons2PassTooFewChars() {
        Assertions.assertThrows(TooFewCharsUsernameException.class, () ->
                new User("Nelson", "12345", true));
    }

    @Test
    public void testUserCons2PassTooManyChars() {
        Assertions.assertThrows(TooManyCharsUsernameException.class, () ->
                new User("Nelson", "1234567890123456789012345", true));
    }

    @Test
    public void testUserCons2BothNull() {
        Assertions.assertThrows(MissingUsernameException.class, () ->
                new User(null, null, true));
    }

    @Test
    public void testUserCons2NameEmpty() {
        Assertions.assertThrows(EmptyUsernameException.class, () ->
                new User("", "NelsonCortes", true));
    }

    @Test
    public void testUserCons2PassEmpty() {
        Assertions.assertThrows(EmptyPasswordException.class, () ->
                new User("Nelson", "", true));
    }

    @Test
    public void testUserCons2BothEmpty() {
        Assertions.assertThrows(EmptyUsernameException.class, () ->
                new User("", "", true));
    }

    @Test
    public void testUserCons2OkActiveTrue() throws MissingPasswordException, EmptyPasswordException,
            MissingUsernameException, EmptyUsernameException, TooFewCharsUsernameException,
            TooManyCharsUsernameException {
        User user = new User("Nelson", "NelsonCortes", true);
        Assertions.assertEquals("Nelson", user.getUsername());
        Assertions.assertEquals("NelsonCortes", user.getPassword());
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(0, user.getLoanList().size());
        Assertions.assertFalse(user.isInactive());
    }

    @Test
    public void testUserCons2OkActiveFalse() throws MissingPasswordException, EmptyPasswordException,
            MissingUsernameException, EmptyUsernameException, TooFewCharsUsernameException,
            TooManyCharsUsernameException {
        User user = new User("Nelson", "NelsonCortes", false);
        Assertions.assertEquals("Nelson", user.getUsername());
        Assertions.assertEquals("NelsonCortes", user.getPassword());
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(0, user.getLoanList().size());
        Assertions.assertTrue(user.isInactive());
    }

    @Test
    public void testUserCons3NameNull() {
        Assertions.assertThrows(MissingUsernameException.class, () ->
                new User(null, "NelsonCortes", new Loan("The Art Of War".hashCode(), 14)));
    }

    @Test
    public void testUserCons3NameTooFewChars() {
        Assertions.assertThrows(TooFewCharsUsernameException.class, () ->
                new User("12345", "NelsonCortes", new Loan("The Art Of War".hashCode(), 14)));
    }

    @Test
    public void testUserCons3NameTooManyChars() {
        Assertions.assertThrows(TooManyCharsUsernameException.class, () ->
                new User("1234567890123456789012345", "NelsonCortes", new Loan("The Art Of War".hashCode(), 14)));
    }

    @Test
    public void testUserCons3PassNull() {
        Assertions.assertThrows(MissingPasswordException.class, () ->
                new User("Nelson", null, new Loan("The Art Of War".hashCode(), 14)));
    }

    @Test
    public void testUserCons3PassTooFewChars() {
        Assertions.assertThrows(TooFewCharsUsernameException.class, () ->
                new User("Nelson", "12345", new Loan("The Art Of War".hashCode(), 14)));
    }

    @Test
    public void testUserCons3PassTooManyChars() {
        Assertions.assertThrows(TooManyCharsUsernameException.class, () ->
                new User("Nelson", "1234567890123456789012345", new Loan("The Art Of War".hashCode(), 14)));
    }

    @Test
    public void testUserCons3LoanNull() {
        Assertions.assertThrows(MissingLoanException.class, () ->
                new User("Nelson", "NelsonCortes", (Loan) null));
    }

    @Test
    public void testUserCons3UserPassNull() {
        Assertions.assertThrows(MissingUsernameException.class, () ->
                new User(null, null, new Loan("The Art Of War".hashCode(), 14)));
    }

    @Test
    public void testUserCons3UserLoanNull() {
        Assertions.assertThrows(MissingUsernameException.class, () ->
                new User(null, "NelsonCortes", (Loan) null));
    }

    @Test
    public void testUserCons3PassLoanNull() {
        Assertions.assertThrows(MissingPasswordException.class, () ->
                new User("Nelson", null, (Loan) null));
    }

    @Test
    public void testUserCons3AllNull() {
        Assertions.assertThrows(MissingUsernameException.class, () ->
                new User(null, null, (Loan) null));
    }

    @Test
    public void testUserCons3UserEmpty() {
        Assertions.assertThrows(EmptyUsernameException.class, () ->
                new User("", "NelsonFreitas", new Loan("The Art Of War".hashCode(), 14)));
    }

    @Test
    public void testUserCons3PassEmpty() {
        Assertions.assertThrows(EmptyPasswordException.class, () ->

                new User("Nelson", "", new Loan("The Art Of War".hashCode(), 14)));
    }

    @Test
    public void testUserCons3BothEmpty() {
        Assertions.assertThrows(EmptyUsernameException.class, () ->
                new User("", "", new Loan("The Art Of War".hashCode(), 14)));
    }

    @Test
    public void testUserCons3Ok() throws BookDoesntExistException, MissingPasswordException,
            EmptyPasswordException, MissingUsernameException, EmptyUsernameException,
            MissingLoanException, TooFewCharsUsernameException, TooManyCharsUsernameException {
        User user = new User("Nelson", "NelsonCortes", new Loan("The Art Of War".hashCode(), 14));
        Assertions.assertEquals("Nelson", user.getUsername());
        Assertions.assertEquals("NelsonCortes", user.getPassword());
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(1, user.getLoanList().size());
        Assertions.assertFalse(user.isInactive());
    }
}
