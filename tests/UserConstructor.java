import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserConstructor {
    @Test
    public void testUserCons1NameNull() {
        Assertions.assertThrows(MissingUsernameException.class, () ->
                new User(null, "NelsonCortes", "Asia"));
    }

    @Test
    public void testUserCons1NameTooFewChars() {
        Assertions.assertThrows(TooFewCharsUsernameException.class, () ->
                new User("12345", "NelsonCortes", "Asia"));
    }

    @Test
    public void testUserCons1NameTooManyChars() {
        Assertions.assertThrows(TooManyCharsUsernameException.class, () ->
                new User("1234567890123456789012345", "NelsonCortes", "Asia"));
    }

    @Test
    public void testUserCons1PassNull() {
        Assertions.assertThrows(MissingPasswordException.class, () ->
                new User("Nelson", null, "Asia"));
    }

    @Test
    public void testUserCons1PassTooFewChars() {
        Assertions.assertThrows(TooFewCharsUsernameException.class, () ->
                new User("Nelson", "12345", "Asia"));
    }

    @Test
    public void testUserCons1PassTooManyChars() {
        Assertions.assertThrows(TooManyCharsUsernameException.class, () ->
                new User("Nelson", "1234567890123456789012345", "Asia"));
    }

    @Test
    public void testUserCons1UserNamePasswordNull() {
        Assertions.assertThrows(MissingUsernameException.class, () ->
                new User(null, null, "Asia"));
    }

    @Test
    public void testUserCons1RegionEmpty() {
        Assertions.assertThrows(MissingRegionException.class, () ->
                new User("Nelson", "NelsonCortes", ""));
    }

    @Test
    public void testUserCons1NameEmpty() {
        Assertions.assertThrows(EmptyUsernameException.class, () ->
                new User("", "NelsonCortes", "Asia"));
    }

    @Test
    public void testUserCons1PassEmpty() {
        Assertions.assertThrows(EmptyPasswordException.class, () ->
                new User("Nelson", "", "Asia"));
    }

    @Test
    public void testUserCons1UsernamePasswordEmpty() {
        Assertions.assertThrows(EmptyUsernameException.class, () ->
                new User("", "", "Asia"));
    }

    @Test
    public void testUserCons1AllEmpty() {
        Assertions.assertThrows(EmptyUsernameException.class, () ->
                new User("", "", ""));
    }

    @Test
    public void testUserCons1Ok() throws MissingPasswordException, MissingUsernameException, MissingRegionException,
            TooManyCharsUsernameException, EmptyPasswordException, TooFewCharsUsernameException,
            EmptyUsernameException {
        User user = new User("Nelson", "NelsonCortes", "Asia");
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
                new User(null, "NelsonCortes", new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                        14)));
    }

    @Test
    public void testUserCons3NameTooFewChars() {
        Assertions.assertThrows(TooFewCharsUsernameException.class, () ->
                new User("12345", "NelsonCortes", new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                        14)));
    }

    @Test
    public void testUserCons3NameTooManyChars() {
        Assertions.assertThrows(TooManyCharsUsernameException.class, () ->
                new User("1234567890123456789012345", "NelsonCortes", new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                        14)));
    }

    @Test
    public void testUserCons3PassNull() {
        Assertions.assertThrows(MissingPasswordException.class, () ->
                new User("Nelson", null, new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                        14)));
    }

    @Test
    public void testUserCons3PassTooFewChars() {
        Assertions.assertThrows(TooFewCharsUsernameException.class, () ->
                new User("Nelson", "12345", new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                        14)));
    }

    @Test
    public void testUserCons3PassTooManyChars() {
        Assertions.assertThrows(TooManyCharsUsernameException.class, () ->
                new User("Nelson", "1234567890123456789012345", new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                        14)));
    }

    @Test
    public void testUserCons3LoanNull() {
        Assertions.assertThrows(MissingLoanException.class, () ->
                new User("Nelson", "NelsonCortes", (Loan) null));
    }

    @Test
    public void testUserCons3UserPassNull() {
        Assertions.assertThrows(MissingUsernameException.class, () ->
                new User(null, null, new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                        14)));
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
                new User("", "NelsonFreitas", new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                        14)));
    }

    @Test
    public void testUserCons3PassEmpty() {
        Assertions.assertThrows(EmptyPasswordException.class, () ->

                new User("Nelson", "", new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                        14)));
    }

    @Test
    public void testUserCons3BothEmpty() {
        Assertions.assertThrows(EmptyUsernameException.class, () ->
                new User("", "", new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                        14)));
    }

    @Test
    public void testUserCons3Ok() throws BookDoesntExistException, MissingPasswordException,
            EmptyPasswordException, MissingUsernameException, EmptyUsernameException,
            MissingLoanException, TooFewCharsUsernameException, TooManyCharsUsernameException, InvalidHashException, InvalidNumberOfDaysToLoanException {
        User user = new User("Nelson", "NelsonCortes", new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                14));
        Assertions.assertEquals("Nelson", user.getUsername());
        Assertions.assertEquals("NelsonCortes", user.getPassword());
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(1, user.getLoanList().size());
        Assertions.assertFalse(user.isInactive());
    }
}
