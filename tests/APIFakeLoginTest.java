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
    public void testLoginEmptyUsername() {
        Assertions.assertThrows(EmptyUsernameException.class, () -> api.login("", "testUser1"));
    }

    @Test
    public void testLoginEmptyPassword() {
        Assertions.assertThrows(EmptyPasswordException.class, () -> api.login("testUser1", ""));
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
