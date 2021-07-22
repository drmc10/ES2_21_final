import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class APIFakeLoginTest {
    private static API api;

    @BeforeAll
    public static void setUp() {
        api = APIFake.INSTANCE;
    }

    @Test
    public void testLoginUsernameAbove25() {
        Assertions.assertThrows(TooManyCharsException.class, () -> api.login("12345678901234567890123456"
                , "testUser1"));
    }

    @Test
    public void testLoginUsernameBelow5() {
        Assertions.assertThrows(TooFewCharsException.class, () -> api.login("1234", "testUser1"));
    }

    @Test
    public void testLoginPasswordAbove25() {
        Assertions.assertThrows(TooManyCharsException.class, () -> api.login("testUser1"
                , "12345678901234567890123456"));
    }

    @Test
    public void testLoginPasswordBelow5() {
        Assertions.assertThrows(TooFewCharsException.class, () -> api.login("testUser1", "1234"));
    }

    @Test
    public void testLoginNameEmpty() {
        Assertions.assertThrows(EmptyUsernameException.class, () -> api.login("", "testUser1"));
    }

    @Test
    public void testLoginPassEmpty() {
        Assertions.assertThrows(EmptyPasswordException.class, () -> api.login("testUser1", ""));
    }

    @Test
    public void testLoginLimiteInferiorUsername() {
        Assertions.assertThrows(UserDoesntExistException.class, () -> api.login("12345", "testUser1"));
    }

    @Test
    public void testLoginLimiteSuperiorUsername() {
        Assertions.assertThrows(UserDoesntExistException.class, () -> api.login("1234567890123456789012345",
                "testUser1"));
    }

    @Test
    public void testLoginLimiteInferiorPassword() {
        Assertions.assertThrows(IncorrectPasswordException.class, () -> api.login("testUser1", "12345"));
    }

    @Test
    public void testLoginLimiteSuperiorPassword() {
        Assertions.assertThrows(IncorrectPasswordException.class, () -> api.login("testUser1"
                , "1234567890123456789012345"));
    }

    @Test
    public void testLoginEmptyUsername() {
        Assertions.assertThrows(EmptyUsernameException.class, () -> api.login("", "testUser1"));
    }

    @Test
    public void testLoginEmptyPassword() {
        Assertions.assertThrows(EmptyPasswordException.class, () -> api.login("testUser1", ""));
    }

    @Test
    public void testLoginUserNull() {
        Assertions.assertThrows(NullParameterException.class, () -> api.login(null, "testUser1"));
    }

    @Test
    public void testLoginPassNull() {
        Assertions.assertThrows(NullParameterException.class, () -> api.login("testUser1", null));
    }

    @Test
    public void testLoginOk() {
        Assertions.assertDoesNotThrow(() -> {
            User user = api.login("testUser1", "testUser1");
            Assertions.assertEquals("testUser1", user.getUsername());
            Assertions.assertEquals("testUser1", user.getPassword());
        });
    }
}
